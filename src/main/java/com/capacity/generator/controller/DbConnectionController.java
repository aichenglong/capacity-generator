package com.capacity.generator.controller;

import com.capacity.generator.model.DatabaseConfig;
import com.capacity.generator.util.ConfigHelper;
import com.capacity.generator.util.DbUtil;
import com.capacity.generator.view.AlertUtil;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author: icl
 * Date:2018/05/26
 * Description:
 * Created by icl on 2018/05/26.
 */
public class DbConnectionController  extends BaseFXController {

    private static final Logger _LOG = LoggerFactory.getLogger(DbConnectionController.class);

    private MainUIController mainUIController; //主界面

    @FXML
    private javafx.scene.control.TextField nameField; //链接名称
    @FXML
    private javafx.scene.control.TextField hostField;// 主机名
    @FXML
    private javafx.scene.control.TextField portField; //端口号
    @FXML
    private javafx.scene.control.TextField userNameField; //用户名
    @FXML
    private javafx.scene.control.TextField passwordField; //密码
    @FXML
    private TextField schemaField; //表名
    @FXML
    private ChoiceBox<String> encodingChoice; //数据库编码
    @FXML
    private ChoiceBox<String> dbTypeChoice;//数据库类型

    private boolean isUpdate = false;

    private Integer primaryKey;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbTypeChoice.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val)->{
           switch (Integer.valueOf(new_val.toString())){
               case 0:
                   if(StringUtils.isEmpty(hostField.getText())){
                       if(StringUtils.isEmpty(nameField.getText())){
                           hostField.setText("localhost");
                       }else {
                           hostField.setText(nameField.getText());
                       }

                   }
                   if(StringUtils.isEmpty(portField.getText())) portField.setText("3306");
                   if(StringUtils.isEmpty(userNameField.getText())) userNameField.setText("root");
                   break;
               case 2:
                   break;
           }
            System.out.println(new_val);
        });

    }

   public  void setMainUIController(MainUIController controller) {
        this.mainUIController = controller;
    }

    /**
     * 测试
     * @param actionEvent
     */
    public void testConnection(ActionEvent actionEvent) {
        DatabaseConfig config = extractConfigForUI();
        if (config == null) {
            return;
        }
        try {
            DbUtil.getConnection(config);
            AlertUtil.showInfoAlert("连接成功");
        } catch (Exception e) {
            _LOG.error(e.getMessage(), e);
            AlertUtil.showWarnAlert("连接失败");
        }
    }

    private DatabaseConfig extractConfigForUI() {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String encoding = encodingChoice.getValue();
        String dbType = dbTypeChoice.getValue();
        String schema = schemaField.getText();
        DatabaseConfig config = new DatabaseConfig();
        config.setName(name);
        config.setDbType(dbType);
        config.setHost(host);
        config.setPort(port);
        config.setUsername(userName);
        config.setPassword(password);
        config.setSchema(schema);
        config.setEncoding(encoding);
        if (StringUtils.isAnyEmpty(name, host, port, userName, encoding, dbType, schema)) {
            AlertUtil.showWarnAlert("密码以外其他字段必填");
            return null;
        }
        return config;

    }

    /**
     * 保存
     * @param
     */

    @FXML
    void saveConnection() {
        DatabaseConfig config = extractConfigForUI();
        if (config == null) {
            return;
        }
        try {
            ConfigHelper.saveDatabaseConfig(this.isUpdate, primaryKey, config);
            getDialogStage().close();
            mainUIController.loadLeftDBTree();
        } catch (Exception e) {
            _LOG.error(e.getMessage(), e);
            AlertUtil.showErrorAlert(e.getMessage());
        }
    }

    /**
     * 关闭窗体
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        getDialogStage().close();
    }

    public void setConfig(DatabaseConfig config) {
        isUpdate = true;
        primaryKey = config.getId(); // save id for update config
        nameField.setText(config.getName());
        hostField.setText(config.getHost());
        portField.setText(config.getPort());
        userNameField.setText(config.getUsername());
        passwordField.setText(config.getPassword());
        encodingChoice.setValue(config.getEncoding());
        dbTypeChoice.setValue(config.getDbType());
        schemaField.setText(config.getSchema());
    }
}
