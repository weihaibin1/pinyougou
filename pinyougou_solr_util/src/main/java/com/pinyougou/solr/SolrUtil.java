package com.pinyougou.solr;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-18 17:06
 */

@Component
public class SolrUtil {

    //1.从数据库中查询满足条件的商品列表数据
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    //2.将查询结果导入solr索引库

    public void dataImport(){
        //1从 数据库中查询满足条件的商品列表数据
       List<TbItem> itemList =  itemMapper.findAllGrounding();

        for (TbItem item : itemList) {
            String spec = item.getSpec();
            //组装规格动态域字段
            Map<String,String> specMap = JSON.parseObject(spec, Map.class);
            item.setSpecMap(specMap);
        }

       //2.将查询结果导入solr索引库
        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();

        System.out.println("dataImport finish ......");
    }
}
