package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Autowired
    private BaseTradeMarkMapper baseTradeMarkMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;


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

    /**
     * 查询所有品牌列表
     *
     * @return List<BaseTrademark>
     */
    @Override
    public List<BaseTrademark> getBaseTrademark() {
        return baseTradeMarkMapper.selectList(null);
    }

    /**
     * 查询所有的基础销售属性
     *
     * @return List<BaseSaleAttr>
     */
    @Override
    public List<BaseSaleAttr> getBaseSaleAttr() {
        return baseSaleAttrMapper.selectList(null);
    }

    /**
     * @param spuInfo 销售属性信息保存
     */
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //参数校验
        if(spuInfo == null){
            throw new RuntimeException("参数错误");
        }
        //判断是修改还是新增
        if(spuInfo.getId()!=null){
            //修改: 修改
            int update = spuInfoMapper.updateById(spuInfo);
            if(update<0){
                throw new RuntimeException("修改spu失败");
            }
            //删除图片 销售属性名称 销售属性值
            int deleteImage = spuImageMapper.delete(
                    new LambdaQueryWrapper<SpuImage>()
                            .eq(SpuImage::getSpuId, spuInfo.getId()));
            int deleteSaleAttr = spuSaleAttrMapper.delete(
                    new LambdaQueryWrapper<SpuSaleAttr>()
                            .eq(SpuSaleAttr::getSpuId,spuInfo.getId()));
            int deleteSaleAttrValue = spuSaleAttrValueMapper.delete(
                    new LambdaQueryWrapper<SpuSaleAttrValue>()
                            .eq(SpuSaleAttrValue::getSpuId,spuInfo.getId()));
            //判断删除结果
            if(deleteImage<0||deleteSaleAttr<0||deleteSaleAttrValue<0){
                throw new RuntimeException("修改spu失败");
            }
        }else{
            //新增: 新增就可以了
            int insert = spuInfoMapper.insert(spuInfo);
            if(insert<=0){
                throw new RuntimeException("新增失败");
            }
        }

        //新增图片表
        Long spuId = spuInfo.getId();
        addSpuImage(spuId,spuInfo.getSpuImageList());
        //新增销售属性名称表
        addSpuSaleAttr(spuId,spuInfo.getSpuSaleAttrList());
    }

    /**
     * 分页查询spu信息
     *
     * @param page        页码
     * @param size        页大小
     * @param category3Id 目录id
     * @return IPage<SpuInfo>
     */
    @Override
    public IPage<SpuInfo> pageSpuInfo(Integer page, Integer size, Long category3Id) {
        return spuInfoMapper.selectPage(
                new Page<>(page,size),
                new LambdaQueryWrapper<SpuInfo>()
                        .eq(SpuInfo::getCategory3Id,category3Id));
    }

    /**
     * 查询spu销售属性列表
     *
     * @param spuId id
     * @return List
     */
    @Override
    public List<SpuSaleAttr> getSpuSaleAttr(Long spuId) {

        return spuSaleAttrMapper.selectSpuSaleAttrBySpuId(spuId);
    }

    /**
     * 保存spu的销售属性信息
     * @param spuId
     * @param spuSaleAttrList
     */
    private void addSpuSaleAttr(Long spuId, List<SpuSaleAttr> spuSaleAttrList) {
        spuSaleAttrList.stream().forEach(spuSaleAttr -> {
            spuSaleAttr.setSpuId(spuId);
            int insert = spuSaleAttrMapper.insert(spuSaleAttr);
            if(insert<=0){
                throw new RuntimeException("新增spu销售属性失败");
            }
            addSpuSaleAttrValue(spuId,spuSaleAttr.getSpuSaleAttrValueList(),spuSaleAttr.getSaleAttrName());
        });
    }

    /**
     *新增spu销售属性败
     * @param spuId
     * @param spuSaleAttrValueList
     * @param saleAttrName
     */
    private void addSpuSaleAttrValue(Long spuId,
                                     List<SpuSaleAttrValue> spuSaleAttrValueList,
                                     String saleAttrName) {
        spuSaleAttrValueList.stream().forEach(spuSaleAttrValue -> {
            spuSaleAttrValue.setSpuId(spuId);
            spuSaleAttrValue.setSaleAttrName(saleAttrName);
            int insert = spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            if(insert<=0){
                throw new RuntimeException("新增spu销售属性名称失败");
            }
        });

    }


    /**
     * 新增spu图片
     * @param spuId spuid
     * @param spuImageList 图片列表
     */
    private void addSpuImage(Long spuId,List<SpuImage> spuImageList){
        spuImageList.stream().forEach(spuImage -> {
            //补全spu的id
            spuImage.setSpuId(spuId);
            //保存spu的图片
            int insert = spuImageMapper.insert(spuImage);
            if(insert<=0){
                throw new RuntimeException("新增spu图片失败");
            }
        });
    }
}
