package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import entity.Result;
import groupEntity.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-05 19:02
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    /**
     * 条件分页查询
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbSpecification specification,Integer pageNum,Integer pageSize){
        return specificationService.search(specification,pageNum,pageSize);
    }

    /**
     * 新增
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Specification specification) {
        try {
            specificationService.add(specification);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }


    //根据id查询
    @RequestMapping("/findOne")
    public Specification findOne(Long id) {
        return specificationService.findOne(id);
    }


    /**
     *修改
     */

    @RequestMapping("/update")
    public Result update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     *删除
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            specificationService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询模板关联的规格列表
     */
    @RequestMapping("/selectSpecList")
    public List<Map> selectSpecList(){
        return specificationService.selectSpecList();
    }
}
