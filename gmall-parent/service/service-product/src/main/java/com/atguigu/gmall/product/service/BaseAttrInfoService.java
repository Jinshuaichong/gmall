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
}