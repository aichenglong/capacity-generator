package com.capacity.generator.bridge;

import com.capacity.generator.model.DatabaseConfig;
import com.capacity.generator.model.DbType;
import com.capacity.generator.model.GeneratorConfig;
import com.capacity.generator.plugin.DbRemarksCommentGenerator;
import com.capacity.generator.util.DbUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Author: icl
 * Date:2018/05/27
 * Description:
 * Created by icl on 2018/05/27.
 */
public class MybatisGeneratorBridge {

    /**
     * 配置信息
     */
    private GeneratorConfig generatorConfig;
    /**
     * 数据库信息
     */

    private DatabaseConfig selectedDatabaseConfig;


    private ProgressCallback progressCallback;

    private List<IgnoredColumn> ignoredColumns;

    private List<ColumnOverride> columnOverrides;

    private String osUserName;//操作系统当前的用户

    private String  createTime;//创建时间

    public MybatisGeneratorBridge(){
        osUserName=System.getProperty("user.name");
        createTime=DateFormatUtils.format(new Date(),"yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 代码生成
     */
    public void generator() throws ClassNotFoundException, InvalidConfigurationException, InterruptedException, SQLException, IOException {
        myBatisGenerator();
        new ServiceGeneratorBridge(generatorConfig).generator();
        new ControllerGeneratorBridge(generatorConfig).generator();
        return;


    }

    private void myBatisGenerator() throws ClassNotFoundException, InvalidConfigurationException, SQLException, IOException, InterruptedException {

        Configuration configuration = new Configuration();
        Context context = new Context(ModelType.CONDITIONAL);
        configuration.addContext(context);
        context.addProperty("javaFileEncoding", "UTF-8"); //获取上下文环境

        //TableConfiguration
        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(generatorConfig.getTableName());
        tableConfig.setDomainObjectName(generatorConfig.getDomainObjectName());
        if(!generatorConfig.isUseExampe()) {
            tableConfig.setUpdateByExampleStatementEnabled(false);
            tableConfig.setCountByExampleStatementEnabled(false);
            tableConfig.setDeleteByExampleStatementEnabled(false);
            tableConfig.setSelectByExampleStatementEnabled(false);
        }

        if (DbType.MySQL.name().equals(selectedDatabaseConfig.getDbType())) {
            tableConfig.setSchema(selectedDatabaseConfig.getSchema());
        } else {
            tableConfig.setCatalog(selectedDatabaseConfig.getSchema());
        }
        if (generatorConfig.isUseSchemaPrefix()) {
            if (DbType.MySQL.name().equals(selectedDatabaseConfig.getDbType())) {
                tableConfig.setSchema(selectedDatabaseConfig.getSchema());
            } else if (DbType.Oracle.name().equals(selectedDatabaseConfig.getDbType())) {
                //Oracle的schema为用户名，如果连接用户拥有dba等高级权限，若不设schema，会导致把其他用户下同名的表也生成一遍导致mapper中代码重复
                tableConfig.setSchema(selectedDatabaseConfig.getUsername());
            } else {
                tableConfig.setCatalog(selectedDatabaseConfig.getSchema());
            }
        }
        //添加GeneratedKey主键生成
        if (StringUtils.isNoneEmpty(generatorConfig.getGenerateKeys())) {
            tableConfig.setGeneratedKey(new GeneratedKey(generatorConfig.getGenerateKeys(), selectedDatabaseConfig.getDbType(), true, null));
        }
        if (ignoredColumns != null) {
            ignoredColumns.stream().forEach(ignoredColumn -> {
                tableConfig.addIgnoredColumn(ignoredColumn);
            });
        }
        if (columnOverrides != null) {
            columnOverrides.stream().forEach(columnOverride -> {
                tableConfig.addColumnOverride(columnOverride);
            });
        }
        if (generatorConfig.isUseActualColumnNames()) {
            tableConfig.addProperty("useActualColumnNames", "true");
        }

        if(generatorConfig.isUseTableNameAlias()){
            tableConfig.setAlias(generatorConfig.getTableName());
        }

        JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
        // http://www.mybatis.org/generator/usage/mysql.html
        if (DbType.MySQL.name().equals(selectedDatabaseConfig.getDbType())) {
            jdbcConfig.addProperty("nullCatalogMeansCurrent", "true");
        }
        jdbcConfig.setDriverClass(DbType.valueOf(selectedDatabaseConfig.getDbType()).getDriverClass());
        jdbcConfig.setConnectionURL(DbUtil.getConnectionUrlWithSchema(selectedDatabaseConfig));
        jdbcConfig.setUserId(selectedDatabaseConfig.getUsername());
        jdbcConfig.setPassword(selectedDatabaseConfig.getPassword());
        // java model
        JavaModelGeneratorConfiguration modelConfig = new JavaModelGeneratorConfiguration();
        modelConfig.setTargetPackage(generatorConfig.getModelPackage());
        modelConfig.setTargetProject(generatorConfig.getProjectFolder() + "/" + generatorConfig.getModelPackageTargetFolder());
        modelConfig.getProperties().setProperty("rootClass","com.capacity.platform.common.entity.BaseModel");
        // Mapper configuration
        SqlMapGeneratorConfiguration mapperConfig = new SqlMapGeneratorConfiguration();
        mapperConfig.setTargetPackage(generatorConfig.getMappingXMLPackage());
        mapperConfig.setTargetProject(generatorConfig.getProjectFolder() + "/" + generatorConfig.getMappingXMLTargetFolder());
        // DAO
        JavaClientGeneratorConfiguration daoConfig = new JavaClientGeneratorConfiguration();
        daoConfig.setConfigurationType("XMLMAPPER");
        daoConfig.setTargetPackage(generatorConfig.getDaoPackage());
        daoConfig.setTargetProject(generatorConfig.getProjectFolder() + "/" + generatorConfig.getDaoTargetFolder());
        daoConfig.getProperties().setProperty("rootInterface","com.capacity.platform.common.mapper.BaseMapper");


        context.setId("myid");
        context.addTableConfiguration(tableConfig);
        context.setJdbcConnectionConfiguration(jdbcConfig);
        context.setJdbcConnectionConfiguration(jdbcConfig);
        context.setJavaModelGeneratorConfiguration(modelConfig);
        context.setSqlMapGeneratorConfiguration(mapperConfig);
        context.setJavaClientGeneratorConfiguration(daoConfig);
        // Comment
        CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
        commentConfig.setConfigurationType(DbRemarksCommentGenerator.class.getName());

        if (generatorConfig.isComment()) {
            commentConfig.addProperty("columnRemarks", "true");
        }
        if (generatorConfig.isAnnotation()) {
            commentConfig.addProperty("annotations", "true");
        }
        context.setCommentGeneratorConfiguration(commentConfig);
        // set java file encoding
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, generatorConfig.getEncoding());

        //实体添加序列化
        PluginConfiguration serializablePluginConfiguration = new PluginConfiguration();
        serializablePluginConfiguration.addProperty("type", "org.mybatis.generator.plugins.SerializablePlugin");
        serializablePluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");

        context.addPluginConfiguration(serializablePluginConfiguration);
        // toString, hashCode, equals插件
        if (generatorConfig.isNeedToStringHashcodeEquals()) {
            PluginConfiguration pluginConfiguration1 = new PluginConfiguration();
            pluginConfiguration1.addProperty("type", "org.mybatis.generator.plugins.EqualsHashCodePlugin");
            pluginConfiguration1.setConfigurationType("org.mybatis.generator.plugins.EqualsHashCodePlugin");

            context.addPluginConfiguration(pluginConfiguration1);
            PluginConfiguration pluginConfiguration2 = new PluginConfiguration();
            pluginConfiguration2.addProperty("type", "org.mybatis.generator.plugins.ToStringPlugin");
            pluginConfiguration2.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
            context.addPluginConfiguration(pluginConfiguration2);
        }
        // limit/offset插件
        if (generatorConfig.isOffsetLimit()) {
            if (DbType.MySQL.name().equals(selectedDatabaseConfig.getDbType())
                    || DbType.PostgreSQL.name().equals(selectedDatabaseConfig.getDbType())) {
                PluginConfiguration pluginConfiguration = new PluginConfiguration();
                pluginConfiguration.addProperty("type", "com.capacity.generator.plugin.MapperPlugin");
                pluginConfiguration.setConfigurationType("com.capacity.generator.plugin.MapperPlugin");
                context.addPluginConfiguration(pluginConfiguration);
            }
        }
        context.setTargetRuntime("MyBatis3");

        List<String> warnings = new ArrayList<>();
        Set<String> fullyqualifiedTables = new HashSet<>();
        Set<String> contexts = new HashSet<>();
        ShellCallback shellCallback = new DefaultShellCallback(true); // override=true
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
        // if overrideXML selected, delete oldXML ang generate new one
        if (generatorConfig.isOverrideXML()) {
            String mappingXMLFilePath = getMappingXMLFilePath(generatorConfig);
            File mappingXMLFile = new File(mappingXMLFilePath);
            if (mappingXMLFile.exists()) {
                mappingXMLFile.delete();
            }
        }

        myBatisGenerator.generate(progressCallback, contexts, fullyqualifiedTables);
    }


    private String getMappingXMLFilePath(GeneratorConfig generatorConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatorConfig.getProjectFolder()).append("/");
        sb.append(generatorConfig.getMappingXMLTargetFolder()).append("/");
        String mappingXMLPackage = generatorConfig.getMappingXMLPackage();
        if (StringUtils.isNotEmpty(mappingXMLPackage)) {
            sb.append(mappingXMLPackage.replace(".", "/")).append("/");
        }
        sb.append(generatorConfig.getDomainObjectName()).append("Mapper.xml");
        return sb.toString();
    }


    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig=generatorConfig;
    }

    public void setDatabaseConfig(DatabaseConfig selectedDatabaseConfig) {
        this.selectedDatabaseConfig=selectedDatabaseConfig;
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns=ignoredColumns;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides=columnOverrides;
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }


}

