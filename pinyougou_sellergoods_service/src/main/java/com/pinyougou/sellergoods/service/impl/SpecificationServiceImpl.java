package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import groupEntity.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;

    @Override
    public PageResult search(TbSpecification specification, Integer pageNum, Integer pageSize) {
        //设置分页查询条件
        PageHelper.startPage(pageNum,pageSize);

        //设置查询条件
        TbSpecificationExample example = new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();

        if(specification!=null){
            String specName = specification.getSpecName();//获取规格名称查询条件
            if(specName!=null && !"".equals(specName)){
                criteria.andSpecNameLike("%"+specName+"%");
            }
        }
        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;


    @Override
    public void add(Specification specification) {
        //保存规格
        TbSpecification tbSpecification = specification.getSpecification();
        specificationMapper.insert(tbSpecification);

        //保存规格选项
        List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptions();
        for (TbSpecificationOption specificationOption : specificationOptions) {
            //关联规格
            specificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    @Override
    public Specification findOne(Long id) {
        Specification specification = new Specification();
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setSpecification(tbSpecification);

        //查询规格选项列表
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);
        specification.setSpecificationOptions(tbSpecificationOptions);
        return specification;
    }

    @Override
    public void update(Specification specification) {
        //修改规格数据
        TbSpecification tbSpecification = specification.getSpecification();
        specificationMapper.updateByPrimaryKey(tbSpecification);

        //更新规格选项列表  思想思路：先删除原有规格选项列表，再新增页面重新提交的规格选项数据
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        specificationOptionMapper.deleteByExample(example);

        List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptions();
        for (TbSpecificationOption specificationOption : specificationOptions) {
            //关联规格
            specificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteByPrimaryKey(id);//删除规格数据

            //删除规格选项
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }

    @Override
    public List<Map> selectSpecList() {
        return specificationMapper.selectSpecList();
    }
}
