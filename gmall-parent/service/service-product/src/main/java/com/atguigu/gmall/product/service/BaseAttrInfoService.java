package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseAttrInfo;

import java.util.List;

/**
 * @Author Metty
 * 平台属性相关的接口类
 */

public interface BaseAttrInfoService {

    /**
     * 主键查询
     * @param Id 主键
     * @return BaseAttrInfo
     */
    BaseAttrInfo getBaseAttrInfo(Long Id);

    /**
     * 查询全部
     * @return List<BaseAttrInfo>
     */
    List<BaseAttrInfo> findAll();

    /**
     * 新增
     * @param baseAttrInfo 属性
     */
    void add(BaseAttrInfo baseAttrInfo);

    /**
     * 修改
     * @param baseAttrInfo 属性
     */
    void update(BaseAttrInfo baseAttrInfo);

    /**
     * 删除
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 根据条件查询
     * @param baseAttrInfo 查询条件
     * @return List<BaseAttrInfo>
     */
    List<BaseAttrInfo> search(BaseAttrInfo baseAttrInfo);
}
