package com.pinyougou.search.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-23 16:58
 */
public class DeleItemSolrMessageListener implements MessageListener {


    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {

        //获取上架商品id
        TextMessage textMessage = (TextMessage) message;
        try {

            String goodsId = textMessage.getText();
            //商品下架时，同步删除索引库数据

            SolrDataQuery query = new SimpleQuery("item_goodsid:" + goodsId);
            solrTemplate.delete(query);
            solrTemplate.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
