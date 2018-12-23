package cn.itcast.demo;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.solr.SolrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-18 15:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class SolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private SolrUtil solrUtil;

    /**
     * 导入商品数据
     */
    @Test
    public void dataImportTest(){
        solrUtil.dataImport();
    }

    /**
     * 添加商品到索引库
     * 新增和修改都是调用saveBean  当id存在时，执行修改操作
     */
    @Test
    public void saveTest(){
        TbItem item = new TbItem();
        item.setId(2L);
        item.setTitle("华为 max 移动3G 64G");
        item.setSeller("华为旗舰店");
        item.setBrand("华为");
        solrTemplate.saveBean(item);
        //必须提交
        solrTemplate.commit();
    }

    /**
     * 根据id查询
     */
/*    @Test
    public void queryByIdTest(){
        TbItem item = solrTemplate.getById(1L, TbItem.class);
        System.out.println(item.getId()+"  "+item.getBrand()+" " +item.getTitle()+"  "+item.getSeller());
    }*/

    /**
     * 根据id删除
     */
    @Test
    public void deleteByIdTest(){
       solrTemplate.deleteById("1");
       solrTemplate.commit();
    }

    /**
     * 根据id删除
     */
    @Test
    public void deleteAll(){
        SolrDataQuery query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    /**
     * 批量添加商品数据到索引库
     */
    @Test
    public void saveBatchTest(){
        List<TbItem> itemList = new ArrayList<>();
        for (long i = 1; i <=100 ; i++) {
            TbItem item = new TbItem();
            item.setId(i);
            item.setTitle(i+"华为 max 移动3G 64G");
            item.setSeller("华为"+i+"号旗舰店");
            item.setBrand("华为");
            itemList.add(item);
        }
        solrTemplate.saveBeans(itemList);
        //必须提交
        solrTemplate.commit();
    }

    /**
     * 分页查询
     */
    @Test
    public void queryPageTest(){
        //设置查询对象
        Query query =  new SimpleQuery("*:*");

        //设置分页条件
        query.setOffset(2);//设置分页查询起始值，  默认值是0，从第一条开始查
        query.setRows(5);//每页查询记录数
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);

        System.out.println("总记录数:"+page.getTotalElements());
        System.out.println("总页数:"+page.getTotalPages());

        //当前页数据列表
        List<TbItem> content = page.getContent();
        for (TbItem item : content) {
             System.out.println(item.getId()+"  "+item.getBrand()+" " +item.getTitle()+"  "+item.getSeller());
        }
    }

    /**
     * 条件查询    例如：item_title中包含9的数据    ，并且item_seller包含5的数据
     */
    @Test
    public void queryMultiTest(){
        //设置查询对象
        Query query =  new SimpleQuery("*:*");
        //构建查询条件
        Criteria criteria = new Criteria("item_title").contains("9").and("item_seller").contains("5");
        //将构建好的查询条件赋值给查询对象
        query.addCriteria(criteria);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);

        System.out.println("总记录数:"+page.getTotalElements());
        System.out.println("总页数:"+page.getTotalPages());

        //当前页数据列表
        List<TbItem> content = page.getContent();
        for (TbItem item : content) {
            System.out.println(item.getId()+"  "+item.getBrand()+" " +item.getTitle()+"  "+item.getSeller());
        }
    }
}
