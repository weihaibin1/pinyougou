package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-03 21:04
 */

@RequestMapping("/brand")
@RestController
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }
    /**
     * 分页查询
     */
    @RequestMapping("/findPage")
    public PageResult findPage( Integer pageNum, Integer pageSize) {
        return brandService.findPage(pageNum, pageSize);
    }



    /**
     * 新增
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }


    //根据id查询
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id) {
        return brandService.findOne(id);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand) {
        try {
            brandService.update(brand);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            brandService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 品牌分页数据查询功能
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, Integer pageNum, Integer pageSize) {
        return brandService.search(pageNum, pageSize,brand);
    }

    /**
     * 查询模板关联的品牌数据
     */
    @RequestMapping("/selectBrandList")
    public List<Map> selectBrandList(){
        return brandService.selectBrandList();
    }
}
