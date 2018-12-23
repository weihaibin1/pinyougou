package com.pinyougou.page.service;

import groupEntity.Goods;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-21 16:35
 */
public interface PageService {

    /**
     * 查询商品详情页数据
     */
    public Goods findOne(Long goodsId);
}
