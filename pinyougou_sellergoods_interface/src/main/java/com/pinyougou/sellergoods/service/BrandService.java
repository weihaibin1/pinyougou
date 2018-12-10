package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-03 21:00
 */
public interface BrandService {

    public List<TbBrand> findAll();

    PageResult findPage(Integer pageNum, Integer pageSize);

    void add(TbBrand brand);

    void update(TbBrand brand);

    TbBrand findOne(Long id);

    void delete(Long[] ids);

    public PageResult search(Integer pageNum, Integer pageSize, TbBrand brand);

    List<Map> selectBrandList();
}
