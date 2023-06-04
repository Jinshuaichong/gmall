package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Metty
 * 平台属性表的mapper映射
 */
@Mapper
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {

    /**
     * 根据分类查询平台属性列表
     * @param categoryId 分类id
     * @return List<BaseAttrInfo>
     */
    List<BaseAttrInfo> selectBaseAttrInfoByCategoryId(@Param("categoryId") Long categoryId);
}
