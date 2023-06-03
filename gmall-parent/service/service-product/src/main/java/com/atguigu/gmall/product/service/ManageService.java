package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;

import java.util.List;

/**
 * @Author Metty
 * 商品管理使用的接口
 */

public interface ManageService {

    /**
     * 获取所有的一级分类
     * @return BaseCategory1
     */
    List<BaseCategory1> getCategory1();

    /**
     * 根据一级分类查询二级分类
     * @return BaseCategory2
     */
    List<BaseCategory2> getCategory2(Long c1Id);

    /**
     * 根据二级分类查询三级分类
     * @return BaseCategory3
     */
    List<BaseCategory3> getCategory3(Long c2Id);

    /**
     * 保存平台属性
     * @param baseAttrInfo 平台属性
     */
    public void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo);
}
