package ${basePackage}.controller;

import com.capacity.platform.common.result.Result;
import com.capacity.platform.common.result.ResultGenerator;
import ${modelPackage}.${modelNameUpperCamel};
import ${servicePackage}.${modelNameUpperCamel}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: ${author}
 * Date:${date}
 * Description:
 * Created by ${author} on ${date}.
 */
@RestController
@RequestMapping("/${modelNameLowerCamel}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result list(){
       List< ${modelNameUpperCamel}Service> list=  ${modelNameLowerCamel}Service.findAll(null);
    return ResultGenerator.genSuccessResult(list);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result get(@PathVariable String id ){
       ${modelNameUpperCamel} ${modelNameLowerCamel}= ${modelNameLowerCamel}Service.find(id);
    return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result post(${modelNameUpperCamel} ${modelNameLowerCamel}){
       ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
    return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,${modelNameUpperCamel} ${modelNameLowerCamel}){
      ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
    return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
       ${modelNameLowerCamel}Service.delete(id);
    return ResultGenerator.genSuccessResult();

    }
}
