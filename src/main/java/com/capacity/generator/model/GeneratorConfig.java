package com.capacity.generator.model;

/**
 * Author: icl
 * Date:2018/05/27
 * Description:
 * Created by icl on 2018/05/27.
 */
public class GeneratorConfig {

    /**
     * 本配置的名称
     */
    private String name;

    private String connectorJarPath;

    private String projectFolder;

    private String modelPackage;

    private String modelPackageTargetFolder;

    private String daoPackage;

    private String daoTargetFolder;

    private String mapperName;

    private String mappingXMLPackage;

    private String mappingXMLTargetFolder;

    private String servicePackage; //service 包名

    private String serviceTargetFolder;//service 所在的目录

    private String controllerPackage;//controller 包名

    private String controllerTargetFolder;// controller 所在的目录

    private String tableName; //表名

    private String domainObjectName; //类名

    private String domainLowerObjectName;//小写类名称(首字母小写)



    private boolean offsetLimit;

    private boolean comment;

    private boolean overrideXML;

    private boolean needToStringHashcodeEquals;

    private boolean annotation;

    private boolean useActualColumnNames;

    private boolean useExampe;

    private String generateKeys;

    private String encoding;

    private boolean useTableNameAlias;

    private boolean useSchemaPrefix;

    public boolean isUseSchemaPrefix() {
        return useSchemaPrefix;
    }

    public void setUseSchemaPrefix(boolean useSchemaPrefix) {
        this.useSchemaPrefix = useSchemaPrefix;
    }

    public boolean isUseExampe() {
        return useExampe;
    }

    public void setUseExampe(boolean useExampe) {
        this.useExampe = useExampe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    public String getConnectorJarPath() {
        return connectorJarPath;
    }

    public void setConnectorJarPath(String connectorJarPath) {
        this.connectorJarPath = connectorJarPath;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public void setProjectFolder(String projectFolder) {
        this.projectFolder = projectFolder;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getModelPackageTargetFolder() {
        return modelPackageTargetFolder;
    }

    public void setModelPackageTargetFolder(String modelPackageTargetFolder) {
        this.modelPackageTargetFolder = modelPackageTargetFolder;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getDaoTargetFolder() {
        return daoTargetFolder;
    }

    public void setDaoTargetFolder(String daoTargetFolder) {
        this.daoTargetFolder = daoTargetFolder;
    }

    public String getMappingXMLPackage() {
        return mappingXMLPackage;
    }

    public void setMappingXMLPackage(String mappingXMLPackage) {
        this.mappingXMLPackage = mappingXMLPackage;
    }

    public String getMappingXMLTargetFolder() {
        return mappingXMLTargetFolder;
    }

    public void setMappingXMLTargetFolder(String mappingXMLTargetFolder) {
        this.mappingXMLTargetFolder = mappingXMLTargetFolder;
    }

    public boolean isOffsetLimit() {
        return offsetLimit;
    }

    public void setOffsetLimit(boolean offsetLimit) {
        this.offsetLimit = offsetLimit;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isNeedToStringHashcodeEquals() {
        return needToStringHashcodeEquals;
    }

    public void setNeedToStringHashcodeEquals(boolean needToStringHashcodeEquals) {
        this.needToStringHashcodeEquals = needToStringHashcodeEquals;
    }

    public boolean isAnnotation() {
        return annotation;
    }

    public void setAnnotation(boolean annotation) {
        this.annotation = annotation;
    }

    public boolean isUseActualColumnNames() {
        return useActualColumnNames;
    }

    public void setUseActualColumnNames(boolean useActualColumnNames) {
        this.useActualColumnNames = useActualColumnNames;
    }

    public String getGenerateKeys() {
        return generateKeys;
    }

    public void setGenerateKeys(String generateKeys) {
        this.generateKeys = generateKeys;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean getUseTableNameAlias() {
        return useTableNameAlias;
    }

    public void setUseTableNameAlias(boolean useTableNameAlias) {
        this.useTableNameAlias = useTableNameAlias;
    }

    public boolean isUseTableNameAlias() {
        return useTableNameAlias;
    }

    public boolean isOverrideXML() {
        return overrideXML;
    }

    public void setOverrideXML(boolean overrideXML) {
        this.overrideXML = overrideXML;
    }

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceTargetFolder() {
        return serviceTargetFolder;
    }

    public void setServiceTargetFolder(String serviceTargetFolder) {
        this.serviceTargetFolder = serviceTargetFolder;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getControllerTargetFolder() {
        return controllerTargetFolder;
    }

    public void setControllerTargetFolder(String controllerTargetFolder) {
        this.controllerTargetFolder = controllerTargetFolder;
    }


}
