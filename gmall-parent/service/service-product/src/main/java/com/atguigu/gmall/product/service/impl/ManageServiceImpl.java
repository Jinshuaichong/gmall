package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.mapper.BaseCategory2Mapper;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Metty
 * 商品管理使用的接口
 */
@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;


    /**
     * 获取所有的一级分类
     *
     * @return BaseCategory1
     */
    @Override
    public List<BaseCategory1> getCategory1() {
        return baseCategory1Mapper.selectList(null);
    }

    /**
     * 根据一级分类查询二级分类
     *
     * @param c1Id 一级分类id
     * @return BaseCategory2
     */
    @Override
    public List<BaseCategory2> getCategory2(Long c1Id) {
        return baseCategory2Mapper.selectList(
                new LambdaQueryWrapper<BaseCategory2>()
                        .eq(BaseCategory2::getCategory1Id,c1Id));
    }

    /**
     * 根据二级分类查询三级分类
     *
     * @param c2Id
     * @return BaseCategory3
     */
    @Override
    public List<BaseCategory3> getCategory3(Long c2Id) {
        return baseCategory3Mapper.selectList(
                new LambdaQueryWrapper<BaseCategory3>()
                        .eq(BaseCategory3::getCategory2Id,c2Id));
    }
}
