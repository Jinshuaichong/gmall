package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseCategory1;

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
}
