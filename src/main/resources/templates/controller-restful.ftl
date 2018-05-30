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
@RequestMapping("/${model}/${modelNameLowerCame}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCame}Service;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result list(){
       List< ${modelNameUpperCame}Service> list=  ${modelNameLowerCame}Service.findAll(null);
    return ResultGenerator.genSuccessResult(list);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result get(@PathVariable String id ){
        ${modelNameUpperCame} ${modelNameLowerCame}= ${modelNameLowerCame}Service.find(id);
    return ResultGenerator.genSuccessResult(${modelNameLowerCame});
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result post(${modelNameUpperCame} ${modelNameLowerCame}){
        ${modelNameLowerCame}Service.save(${modelNameLowerCame});
    return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,${modelNameUpperCame} ${modelNameLowerCame}){
        ${modelNameLowerCame}Service.update(${modelNameLowerCame});
    return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        ${modelNameLowerCame}Service.delete(id);
    return ResultGenerator.genSuccessResult();

    }
}
