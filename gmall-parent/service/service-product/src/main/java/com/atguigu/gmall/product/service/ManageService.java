package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.*;

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
     void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 根据分类查询平台属性列表
     * @param category3Id 分类id
     * @return List<BaseAttrInfo>
     */
     List<BaseAttrInfo> getBaseAttrInfo(Long category3Id);

    /**
     * 删除平台属性
     * @param attrId 平台属性id
     *
     */
    void deleteBaseAttrInfo(Long attrId);

    /**
     * 查询所有品牌列表
     * @return List<BaseTrademark>
     */
    List<BaseTrademark> getBaseTrademark();

    /**
     * 查询所有的基础销售属性
     * @return List<BaseSaleAttr>
     */
    List<BaseSaleAttr> getBaseSaleAttr();
}
