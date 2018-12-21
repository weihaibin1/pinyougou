package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.SearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-18 20:58
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Reference
    private SearchService searchService;

    /**
     * 商品搜索
     */
    @RequestMapping("/searchItem")
    public Map<String,Object> searchItem(@RequestBody Map searchMap){
        return searchService.search(searchMap);
    }
}
