package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zipkin2.Call;

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

    /**
     * 新增
     * @return com.atguigu.gmall.common.result.Result
     */
    @PostMapping
    public Result<?> add(@RequestBody BaseAttrInfo baseAttrInfo){
        baseAttrInfoService.add(baseAttrInfo);
        return Result.ok();
    }

    /**
     * 修改
     * @return com.atguigu.gmall.common.result.Result
     */
    @PutMapping
    public Result<?> update(@RequestBody BaseAttrInfo baseAttrInfo){
        baseAttrInfoService.update(baseAttrInfo);
        return Result.ok();
    }

    /**
     * 删除
     * @return com.atguigu.gmall.common.result.Result
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable(value = "id") Long id){
        baseAttrInfoService.delete(id);
        return Result.ok();
    }

    /**
     * 根据条件查询
     * @param baseAttrInfo 查询条件
     * @return com.atguigu.gmall.common.result.Result
     */
    @PostMapping("/search")
    public Result<?> search(@RequestBody BaseAttrInfo baseAttrInfo){
        return Result.ok(baseAttrInfoService.search(baseAttrInfo));

    }

    /**
     * 分页查询
     * @param page 页码
     * @param size 页大小
     * @return IPage
     */
    @GetMapping("/page/{page}/{size}")
    public Result<?> page(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        return Result.ok(baseAttrInfoService.page(page,size));
    }
}
