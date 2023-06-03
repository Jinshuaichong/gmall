package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.product.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Metty
 * 管理控制台使用的控制层
 */

@RestController
@RequestMapping("/admin/product")
public class ManageController {

    @Autowired
    private ManageService manageService;


    /**
     * 查询所有一级分类
     * @return BaseCategory1 List
     */
    @GetMapping("/getCategory1")
    public Result<List<BaseCategory1>> getCategory1(){
        return Result.ok(manageService.getCategory1());
    }

    /**
     * 根据一级分类查询二级分类
     * @return BaseCategory1 List
     */
    @GetMapping("/getCategory2/{category1Id}")
    public Result<List<BaseCategory2>> getCategory2(@PathVariable("category1Id") Long category1Id){
        return Result.ok(manageService.getCategory2(category1Id));
    }

    /**
     * 根据二级分类查询三级分类
     * @return BaseCategory1 List
     */
    @GetMapping("/getCategory3/{category2Id}")
    public Result<List<BaseCategory3>> getCategory3(@PathVariable("category2Id") Long category2Id){
        return Result.ok(manageService.getCategory3(category2Id));
    }



}
