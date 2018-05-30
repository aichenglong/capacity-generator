package com.capacity.generator.controller;


/**
 * Author: icl
 * Date:2018/05/26
 * Description:
 * Created by icl on 2018/05/26.
 */
public enum FXMLPage {

    NEW_CONNECTION("fxml/newConnection.fxml"),
    SELECT_TABLE_COLUMN("fxml/selectTableColumn.fxml"),
    GENERATOR_CONFIG("fxml/generatorConfigs.fxml"),;

    private String fxml;

    FXMLPage(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return this.fxml;
    }


}