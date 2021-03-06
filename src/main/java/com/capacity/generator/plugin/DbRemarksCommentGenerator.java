package com.capacity.generator.plugin;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * Author: icl
 * Date:2018/05/27
 * Description:
 * Created by icl on 2018/05/27.
 */
public class DbRemarksCommentGenerator implements CommentGenerator {


    private Properties properties;

    private boolean columnRemarks;

    private boolean isAnnotations;



    private boolean suppressDate;

    private boolean suppressAllComments;

    /** If suppressAllComments is true, this option is ignored. */
    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;

    private String osUserName;//操作系统当前的用户

    private String  createTime;//创建时间

    public DbRemarksCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
        osUserName=System.getProperty("user.name");
        createTime=DateFormatUtils.format(new Date(),"yyyy/MM/dd HH:mm:ss");
    }


    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
        if (isAnnotations) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType("javax.persistence.Table"));
            compilationUnit.addImportedType(new FullyQualifiedJavaType("javax.persistence.Id"));
            compilationUnit.addImportedType(new FullyQualifiedJavaType("javax.persistence.Column"));
            compilationUnit.addImportedType(new FullyQualifiedJavaType("javax.persistence.GeneratedValue"));
            compilationUnit.addImportedType(new FullyQualifiedJavaType("org.hibernate.validator.constraints.NotEmpty"));
        }
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and
     * when it was generated.
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }
        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - "); //$NON-NLS-1$
        sb.append(osUserName);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement
                .addElement(new TextElement(
                        "  This element is automatically generated by MyBatis Generator, do not modify.")); //$NON-NLS-1$
        if (StringUtils.isNotEmpty(createTime) ){
            sb.setLength(0);
            sb.append("  This element was generated on "); //$NON-NLS-1$
            sb.append(createTime);
            sb.append('.');
            xmlElement.addElement(new TextElement(sb.toString()));
        }

        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }


    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {


    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        columnRemarks = isTrue(properties
                .getProperty("columnRemarks"));
        isAnnotations = isTrue(properties
                .getProperty("annotations"));
    }

    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
    }

    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * Author "+osUserName);
        topLevelClass.addJavaDocLine(" * Date: "+createTime);
        topLevelClass.addJavaDocLine(" * Description:" + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName() +" "
                +introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(" * Created by "+osUserName+" on "+createTime);
        topLevelClass.addJavaDocLine(" */");
        if(isAnnotations) {
            topLevelClass.addAnnotation("@Table(name=\"" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + "\")");
        }
    }

    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable) {
    }

    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }

        if (isAnnotations) {
            boolean isId = false;
            for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
                if (introspectedColumn == column) {
                    isId = true;
                    field.addAnnotation("@Id");
                    field.addAnnotation("@GeneratedValue");
                    break;
                }
            }
            if (!introspectedColumn.isNullable() && !isId){
                field.addAnnotation("@NotEmpty");
            }
            if (introspectedColumn.isIdentity()) {
                if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                    field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
                } else {
                    field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
                }
            } else if (introspectedColumn.isSequenceColumn()) {
                field.addAnnotation("@SequenceGenerator(name=\"\",sequenceName=\"" + introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement() + "\")");
            }
        }
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
    }

    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * "+introspectedTable.getFullyQualifiedTable());
        sb.append(" "+introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" * author: "+osUserName); //$NON-NLS-1$
//        sb.setLength(0);
        addJavadocTag(method, false);
        sb = new StringBuilder();
        sb.append(" * @return the value of "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * "+introspectedTable.getFullyQualifiedTable());
        sb.append(" "+introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" * author: "+osUserName); //$NON-NLS-1$
        addJavadocTag(method, false);
        method.addJavaDocLine(" *"); //$NON-NLS-1$
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb=new StringBuilder();
        sb.append(" * @param "); //$NON-NLS-1$
        sb.append(parm.getName());
        sb.append(" the value for "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */"); //$NON-NLS-1$

    }

    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement
     *            the java element
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
                                 boolean markAsDoNotDelete) {
        StringBuilder sb = new StringBuilder();
        sb.append(" * Created by "); //$NON-NLS-1$
        sb.append(osUserName);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        if (createTime != null) {
            sb.append(" on ");
            sb.append(createTime);
        }
        javaElement.addJavaDocLine(sb.toString());
    }
}
