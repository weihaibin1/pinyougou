package com.pinyougou.cart.service;

import groupEntity.Cart;

import java.util.List;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-27 16:17
 */
public interface CartService {

    /**
     * 添加商品到购物车
     */
    public List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num);

    List<Cart> selectCartListFromRedis(String sessionId);

    void saveCartListToRedis(String sessionId, List<Cart> cartList);


    List<Cart> mergeCartList(List<Cart> cartList_sessionId, List<Cart> cartList_username);

    void deleteCartList(String sessionId);
}
