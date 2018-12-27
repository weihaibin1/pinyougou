package com.pinyougou.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-10 16:20
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    /**
     * 基于安全框架获取用户信息
     */
    @RequestMapping("/getName")
    public Map<String,String> getName(){
        //基于安全框架获取用户信息
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String,String> map = new HashMap<>();
        map.put("loginName",loginName);

        return map;
    }
}
