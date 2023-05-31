package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 查询全部
     *
     * @return List<BaseAttrInfo>
     */
    @Override
    public List<BaseAttrInfo> findAll() {
        return baseAttrInfoMapper.selectList(null);
    }

    /**
     * 新增
     *
     * @param baseAttrInfo 属性
     */
    @Override
    public void add(BaseAttrInfo baseAttrInfo) {
        //参数校验
        if(baseAttrInfo==null || baseAttrInfo.getAttrName()==null){
            throw new RuntimeException("参数错误");
        }
        int insert = baseAttrInfoMapper.insert(baseAttrInfo);
        if(insert<=0){
            throw new RuntimeException("新增失败,请重试");
        }
    }

    /**
     * 修改
     *
     * @param baseAttrInfo 属性
     */
    @Override
    public void update(BaseAttrInfo baseAttrInfo) {
        //参数校验
        if(baseAttrInfo==null || baseAttrInfo.getAttrName()==null){
            throw new RuntimeException("参数错误");
        }
        int update = baseAttrInfoMapper.updateById(baseAttrInfo);
        if(update<0){
            throw new RuntimeException("修改失败,请重试");
        }
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @Override
    public void delete(Long id) {
        //参数校验
        if(id==null){
            return;
        }
        int i = baseAttrInfoMapper.deleteById(id);
        if(i<= 0){
            throw new RuntimeException("删除失败,请重试");
        }
    }

    /**
     * 根据条件查询
     *
     * @param baseAttrInfo 查询条件
     * @return List<BaseAttrInfo>
     */
    @Override
    public List<BaseAttrInfo> search(BaseAttrInfo baseAttrInfo) {
        if(baseAttrInfo == null){
            //没有条件就查询所有数据
            return baseAttrInfoMapper.selectList(null);
        }
        //声明条件构造器
        LambdaQueryWrapper<BaseAttrInfo> wrapper = new LambdaQueryWrapper<>();
        //若 id 不空则设置为查询条件
        if(baseAttrInfo.getId()!=null){
            wrapper.eq(BaseAttrInfo::getId,baseAttrInfo.getId());
        }
        //属性名字不空
        if(baseAttrInfo.getAttrName() !=null){
            wrapper.like(BaseAttrInfo::getAttrName,baseAttrInfo.getAttrName());
        }
        //分类id
        if(baseAttrInfo.getCategoryId()!=null){
            wrapper.eq(BaseAttrInfo::getCategoryId,baseAttrInfo.getCategoryId());
        }
        //分类级别
        if(baseAttrInfo.getCategoryLevel()!=null){
            wrapper.like(BaseAttrInfo::getCategoryLevel,baseAttrInfo.getCategoryLevel());
        }
        //执行查询返回结果\
        return baseAttrInfoMapper.selectList(wrapper);
    }
}
