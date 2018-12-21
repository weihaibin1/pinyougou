package com.pinyougou.search.service;

import java.util.Map;

/**
 * 商品搜索模块接口
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-18 20:26
 */

public interface SearchService {
    /**
     * 商品搜索  itemList
     * 请求参数:关键字、规格、品牌、分类、价格区间，当前页、每页记录数
     */
    public Map<String,Object> search(Map searchMap);
}
