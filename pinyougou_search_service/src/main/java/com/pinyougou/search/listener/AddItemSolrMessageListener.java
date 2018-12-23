package com.pinyougou.search.listener;

import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-23 16:51
 */
public class AddItemSolrMessageListener implements MessageListener{
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;


    @Override
    public void onMessage(Message message) {
        //获取上架商品id
        TextMessage textMessage = (TextMessage) message;
        try {
            String goodsId = textMessage.getText();
            //同步上架商品数据到索引库
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(Long.parseLong(goodsId));
            List<TbItem> itemList = itemMapper.selectByExample(example);

            solrTemplate.saveBeans(itemList);
            solrTemplate.commit();


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
