package com.capacity.generator.bridge;

import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Author: icl
 * Date:2018/05/27
 * Description:
 * Created by icl on 2018/05/27.
 */
public class BaseFreeMarkerBridge {

    private String osUserName;//操作系统当前的用户

    private String  createTime;//创建时间

    public BaseFreeMarkerBridge(){
        osUserName=System.getProperty("user.name");
        createTime=DateFormatUtils.format(new Date(),"yyyy/MM/dd HH:mm:ss");
    }
    protected  freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(Thread.currentThread().getContextClassLoader().getResource("templates").getFile()));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    public void generator(){

    }

    public String getOsUserName() {
        return osUserName;
    }

    public String getCreateTime() {
        return createTime;
    }
}
