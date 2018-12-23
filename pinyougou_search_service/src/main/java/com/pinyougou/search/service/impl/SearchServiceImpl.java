package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map searchMap) {
        //创建高亮查询对象
        HighlightQuery query = new SimpleHighlightQuery();

        //1、关键字搜索
        String keywords = (String) searchMap.get("keywords");
        Criteria criteria = null;
        if(keywords!=null && !"".equals(keywords)){
            //输入了关键字条件
            criteria = new Criteria("item_keywords").is(keywords);
        }else {
            //没有输入关键字，查询所有  *:*
            criteria = new Criteria().expression("*:*");
        }


        //将关键字搜索条件对象赋值给查询对象
        query.addCriteria(criteria);

        //2、构建分类过滤条件查询
        String category = (String) searchMap.get("category");
        if (category!=null && !"".equals(category)) {
            //构建分类查询条件
            Criteria categoryCriteria= new Criteria("item_category").is(category);
            //构建过滤条件查询
            FilterQuery filterQuery = new SimpleFilterQuery(categoryCriteria);
            query.addFilterQuery(filterQuery);
        }

        //3、构建品牌过滤条件查询
        String brand = (String) searchMap.get("brand");
        if (brand!=null && !"".equals(brand)) {
            //构建查询条件
            Criteria brandCriteria= new Criteria("item_brand").is(brand);
            //构建过滤条件查询
            FilterQuery filterQuery = new SimpleFilterQuery(brandCriteria);
            query.addFilterQuery(filterQuery);
        }

        //4、构建规格过滤条件查询
        Map<String,String> specMap = (Map<String, String>) searchMap.get("spec");
        if (specMap!=null) {
            for(String key :specMap.keySet()){
                //构建查询条件
                Criteria brandCriteria= new Criteria("item_spec_"+key).is(specMap.get(key));
                //构建过滤条件查询
                FilterQuery filterQuery = new SimpleFilterQuery(brandCriteria);
                query.addFilterQuery(filterQuery);
            }

        }

        //5、构建价格区间过滤条件查询
        String price = (String) searchMap.get("price");
        if (price!=null && !"".equals(price)) {
            String[] prices = price.split("-");
            //0-500 500-1000 1000-*  最关键：通过价格临界值判断  0  *
            if (!"0".equals(prices[0])) {
                //构建查询条件
                Criteria priceCriteria= new Criteria("item_price").greaterThanEqual(prices[0]);
                //构建过滤条件查询
                FilterQuery filterQuery = new SimpleFilterQuery(priceCriteria);
                query.addFilterQuery(filterQuery);
            }

            if (!"*".equals(prices[1])) {
                //构建查询条件
                Criteria priceCriteria= new Criteria("item_price").lessThanEqual(prices[1]);
                //构建过滤条件查询
                FilterQuery filterQuery = new SimpleFilterQuery(priceCriteria);
                query.addFilterQuery(filterQuery);
            }

        }
        //6、构建排序查询
        String sortField = (String) searchMap.get("sortField");
        String sort = (String) searchMap.get("sort");
        if (sortField!=null && !"".equals(sortField)) {
            //构建排序操作
            if("ASC".equals(sort)){//升序
                query.addSort(new Sort(Sort.Direction.ASC,"item_"+sortField));
            }else {
                query.addSort(new Sort(Sort.Direction.DESC,"item_"+sortField));
            }
        }

        //7、构建分页查询
        Integer pageNo = (Integer) searchMap.get("pageNo");
        Integer pageSize = (Integer) searchMap.get("pageSize");
        query.setOffset((pageNo-1)*pageSize);//分页起始值  0  60
        query.setRows(pageSize);//每页记录数


        //设置高亮
        //高亮对象
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");//设置高亮字段
        highlightOptions.setSimplePrefix("<font color='red'>");//设置高亮前缀
        highlightOptions.setSimplePostfix("</font>");//设置高亮后缀
        query.setHighlightOptions(highlightOptions);

        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);

        System.out.println("getTotalElements:"+page.getTotalElements());
        System.out.println("total page:"+page.getTotalPages());

        //当前页数据列表
        List<TbItem> content = page.getContent();
        for (TbItem item : content) {
            //处理高亮结果
            List<HighlightEntry.Highlight> highlights = page.getHighlights(item);
            if(highlights!=null && highlights.size()>0){
                //获取高亮内容结果集
                List<String> snipplets = highlights.get(0).getSnipplets();
                if (snipplets!=null && snipplets.size()>0) {
                    item.setTitle(snipplets.get(0));
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows",content);
        resultMap.put("totalPages",page.getTotalPages());//总页数
        resultMap.put("pageNo",pageNo);//当前页

        return resultMap;
    }
}
