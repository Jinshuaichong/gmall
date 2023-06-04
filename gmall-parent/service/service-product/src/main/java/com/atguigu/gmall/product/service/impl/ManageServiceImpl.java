package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author Metty
 * 商品管理使用的接口
 */
@Service
@Transactional(rollbackFor = Exception.class )
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;


    /**
     * 获取所有的一级分类
     *
     * @return BaseCategory1
     */
    @Override
    public List<BaseCategory1> getCategory1() {
        return baseCategory1Mapper.selectList(null);
    }

    /**
     * 根据一级分类查询二级分类
     *
     * @param c1Id 一级分类id
     * @return BaseCategory2
     */
    @Override
    public List<BaseCategory2> getCategory2(Long c1Id) {
        return baseCategory2Mapper.selectList(
                new LambdaQueryWrapper<BaseCategory2>()
                        .eq(BaseCategory2::getCategory1Id,c1Id));
    }

    /**
     * 根据二级分类查询三级分类
     *
     * @param c2Id
     * @return BaseCategory3
     */
    @Override
    public List<BaseCategory3> getCategory3(Long c2Id) {
        return baseCategory3Mapper.selectList(
                new LambdaQueryWrapper<BaseCategory3>()
                        .eq(BaseCategory3::getCategory2Id,c2Id));
    }

    /**
     * 保存平台属性
     *
     * @param baseAttrInfo 平台属性
     */
    @Override
    public void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo) {
        //参数校验
        if(baseAttrInfo==null || StringUtils.isEmpty(baseAttrInfo.getAttrName())){
            throw new RuntimeException("参数错误");
        }
        //判断用户是修改还是新增
        if(baseAttrInfo.getId()!=null){
            //修改
            int update = baseAttrInfoMapper.updateById(baseAttrInfo);
            if(update<0){
                throw new RuntimeException("修改失败");
            }
            int delete=baseAttrValueMapper.delete(
                    new LambdaQueryWrapper<BaseAttrValue>()
                    .eq(BaseAttrValue::getAttrId,baseAttrInfo.getId()));
            if(delete<0){
                throw new RuntimeException("删除失败");
            }
        }else{
            //新增
            int insert = baseAttrInfoMapper.insert(baseAttrInfo);
            if(insert<=0){
                throw new RuntimeException("新增失败");
            }
        }
        //保存完成后平台属性对象有了id
        Long attrId = baseAttrInfo.getId();
        //获取用户给的值列表
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

        //方式二: 流式编程
        attrValueList.forEach(baseAttrValue -> {
            if(!StringUtils.isEmpty(baseAttrValue.getValueName())){
                //补全平台属性id
                baseAttrValue.setId(attrId);
                //保存平台属性值
                int baseAttrValueInsert = baseAttrValueMapper.insert(baseAttrValue);
                if (baseAttrValueInsert <= 0){
                    throw new RuntimeException("新增平台属性值表数据失败!");
                }
            }

        });
        //结束

    }

    /**
     * 根据分类查询平台属性列表
     *
     * @param category3Id 分类id
     * @return List<BaseAttrInfo>
     */
    @Override
    public List<BaseAttrInfo> getBaseAttrInfo(Long category3Id) {
        return baseAttrInfoMapper.selectBaseAttrInfoByCategoryId(category3Id);
    }

    /**
     * 删除平台属性
     *
     * @param attrId 平台属性id
     */
    @Override
    public void deleteBaseAttrInfo(Long attrId) {
        if(attrId == null){
            return;
        }
        //删除
        int delete = baseAttrInfoMapper.deleteById(attrId);
        if(delete<0){
            throw new RuntimeException("删除平台属性失败");
        }
        //平台属性值删除
        int deleteAttrValue = baseAttrValueMapper.delete(
                new LambdaQueryWrapper<BaseAttrValue>()
                        .eq(BaseAttrValue::getAttrId, attrId)
        );
        if(deleteAttrValue<0){
            throw new RuntimeException("删除平台属性值失败");

        }
    }
}
