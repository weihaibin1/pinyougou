package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import entity.PageResult;
import groupEntity.Specification;

import java.util.List;
import java.util.Map;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-05 19:13
 */
public interface SpecificationService {
    PageResult search(TbSpecification specification, Integer pageNum, Integer pageSize);

    void add(Specification specification);

    Specification findOne(Long id);

    void update(Specification specification);

    void delete(Long[] ids);

    List<Map> selectSpecList();
}
