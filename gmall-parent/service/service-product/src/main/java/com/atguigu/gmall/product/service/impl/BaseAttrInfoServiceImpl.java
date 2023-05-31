package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Metty
 * 平台属性相关的接口类实现
 */

@Service
public class BaseAttrInfoServiceImpl implements BaseAttrInfoService {


    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;



    /**
     * 主键查询
     *
     * @param Id 主键
     * @return BaseAttrInfo
     */
    @Override
    public BaseAttrInfo getBaseAttrInfo(Long Id) {
        return baseAttrInfoMapper.selectById(Id);
    }
}
