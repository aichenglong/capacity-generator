package ${basePackage}.service.impl;

import com.capacity.platform.common.mapper.BaseMapper;
import com.capacity.platform.common.service.impl.AbstractBaseServiceImpl;
import ${modelPackage}.${modelNameUpperCamel};
import ${mapperPackage}.${modelNameUpperCamel}Mapper;
import ${servicePackage}.${modelNameUpperCamel}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: ${author}
 * Date:${date}
 * Description:
 * Created by ${author} on ${date}.
 */
@Service("${modelNameLowerCamel}Service")
public class ${modelNameUpperCamel}ServiceImpl extends AbstractBaseServiceImpl<${modelNameUpperCamel},${id}> implements
${modelNameUpperCamel}Service  {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    @Override
    public BaseMapper<${modelNameUpperCamel}, ${id}> getBaseMapper() {
        return ${modelNameLowerCamel}Mapper;
    }
}
