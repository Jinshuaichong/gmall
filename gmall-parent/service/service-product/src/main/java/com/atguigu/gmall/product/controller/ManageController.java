package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 保存平台属性
     * @param baseAttrInfo 平台属性
     * @return Result
     */
    @PostMapping("/saveAttrInfo")
    public Result<?> saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        manageService.saveBaseAttrInfo(baseAttrInfo);
        return Result.ok();
    }

    /**
     * 根据三级分类查询平台属性列表
     * @param category3Id 分类id
     * @return List<BaseAttrInfo>
     */
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result<?> attrInfoList(@PathVariable("category1Id") Long category1Id,
                                  @PathVariable("category2Id") Long category2Id,
                                  @PathVariable("category3Id") Long category3Id){

        return Result.ok(manageService.getBaseAttrInfo(category3Id));
    }


    /**
     * 删除平台属性
     * @param attrId 平台属性id
     * @return Result
     */
    @DeleteMapping("deleteBaseAttrInfo/{attrId}")
    public Result<?> deleteBaseAttrInfo(@PathVariable("attrId") Long attrId){
        manageService.deleteBaseAttrInfo(attrId);
        return Result.ok();
    }

    /**
     *获取品牌属性
     * @return Result<List<BaseTrademark>>
     */
    @GetMapping("/baseTrademark/getTrademarkList")
    public Result<List<BaseTrademark>> getTrademarkList(){
        return Result.ok(manageService.getBaseTrademark());
    }

    /**
     *获取销售属性
     * @return Result<List<BaseSaleAttr>>
     */
    @GetMapping("baseSaleAttrList")
    public Result<List<BaseSaleAttr>> baseSaleAttrList(){
        return Result.ok(manageService.getBaseSaleAttr());
    }

    /**
     * 保存spu信息
     * @param spuInfo spu信息
     * @return Result<?>
     */
    @PostMapping("saveSpuInfo")
    public Result<?> saveSpuInfo(@RequestBody SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);
        return Result.ok();
    }

}
