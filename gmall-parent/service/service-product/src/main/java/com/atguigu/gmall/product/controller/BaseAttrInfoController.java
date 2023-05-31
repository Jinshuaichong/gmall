package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Metty
 * 平台属性相关的接口类
 */
@RestController
@RequestMapping(value = "/api/baseAttrInfo")
public class BaseAttrInfoController {
    @Autowired
    private BaseAttrInfoService baseAttrInfoService;

    /**
     * 主键查询
     * @param Id 主键
     * @return com.atguigu.gmall.common.result.Result
     */
    @GetMapping("/getBaseAttrInfo/{id}")
    public Result<?> getBaseAttrInfo(@PathVariable(value = "id") Long Id){
        return Result.ok(baseAttrInfoService.getBaseAttrInfo(Id));
    }

    /**
     * 查询全部
     * @return com.atguigu.gmall.common.result.Result
     */
    @GetMapping("/findAll")
    public Result<?> findAll(){
        return Result.ok(baseAttrInfoService.findAll());
    }
}
