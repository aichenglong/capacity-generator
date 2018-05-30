package com.capacity.generator.bridge;

import com.capacity.generator.model.GeneratorConfig;
import com.google.common.base.CaseFormat;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: icl
 * Date:2018/05/27
 * Description: controller restful 生成
 * Created by icl on 2018/05/27.
 */
public class ControllerRestfulGeneratorBridge extends BaseFreeMarkerBridge {


    private GeneratorConfig generatorConfig;//自动生成配置信息

    public ControllerRestfulGeneratorBridge(GeneratorConfig generatorConfig){
        this.generatorConfig=generatorConfig;

    }

    public void generator(){
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String,Object> data =new HashMap<>();
            data.put("author",getOsUserName());//作者
            data.put("date",getCreateTime()); //创建时间
            data.put("modelNameUpperCamel", generatorConfig.getDomainObjectName());
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, generatorConfig.getDomainObjectName()));
            data.put("basePackage", generatorConfig.getServicePackage());
            data.put("modelPackage",generatorConfig.getModelPackage());
            data.put("mapperPackage",generatorConfig.getDaoPackage());
            data.put("servicePackage",generatorConfig.getServicePackage());
            data.put("id","String");
            File file = new File(generatorConfig.getProjectFolder()+"/"+generatorConfig.getControllerTargetFolder()+"/"
                    +generatorConfig.getControllerPackage().replace(".","/")+"/"+ generatorConfig.getDomainObjectName() + "RestfulController.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller-restful.ftl").process(data,
                    new FileWriter(file));
            System.out.println(generatorConfig.getDomainObjectName() + "RestfulController.java 生成成功");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

}
