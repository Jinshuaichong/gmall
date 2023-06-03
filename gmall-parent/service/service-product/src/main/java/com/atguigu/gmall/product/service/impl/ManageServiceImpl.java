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
        //保存平台属性名称表的数据
        int baseAttrInfoInsertRes = baseAttrInfoMapper.insert(baseAttrInfo);
        if(baseAttrInfoInsertRes <= 0){
            throw new RuntimeException("新增平台属性名称表数据失败!");
        }
        //保存完成后平台属性对象有了id
        Long attrId = baseAttrInfo.getId();
        //获取用户给的值列表
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();


        //将id补充到值的数据中 方式一: 串行化编程
//        for (BaseAttrValue baseAttrValue : attrValueList) {
//            //值不能空
//            if(!StringUtils.isEmpty(baseAttrValue.getValueName())){
//                //补全平台属性id
//                baseAttrValue.setId(attrId);
//                //保存平台属性值
//                int baseAttrValueInsert = baseAttrValueMapper.insert(baseAttrValue);
//                if (baseAttrValueInsert <= 0){
//                    throw new RuntimeException("新增平台属性值表数据失败!");
//                }
//            }
//
//        }

        //方式二: 流式编程
        attrValueList.stream().forEach(baseAttrValue -> {
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
}
