package com.roncoo.eshop.cache.service;

import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;

/**
 * @ClassName CacheService
 * @Description 缓存service接口
 * @Author wuwenxiang
 * @Date 2019-07-15 20:18
 * @Version 1.0
 **/
public interface CacheService {
    /**
     * 将商品信息保存到本地缓存中
     * @param productInfo
     * @return
     */
    ProductInfo saveLocalCache(ProductInfo productInfo);

    /**
     * 从本地缓存中获取商品信息
     * @param id
     * @return
     */
    ProductInfo getLocalCache(Long id);


    /**
     * 从本地ehcache缓冲中获取缓存信息
     * @param productId
     * @return
     */
    ProductInfo getProductInfoFromLocalCahe(long productId);

    /**
     * 从本地ehcache缓冲中获取店铺信息
     * @param shopId
     * @return
     */
    ProductInfo getShopInfoFromLocalCahe(long shopId);


    /**
     * 将商品信息保存到本地的ehcache缓冲中；
     * @param productInfo
     */
    ProductInfo saveProductInfoToLocalCache(ProductInfo productInfo);

    /**
     * 将商品写入redis
     * @param productInfo
     */
    void saveProductInfoToRedisCache(ProductInfo productInfo);


    /**
     * 将店铺信息保存到本地的ehcache缓冲中；
     * @param productInfo
     */
    ShopInfo saveShopInfoToLocalCache(ShopInfo productInfo);

    /**
     * 将店铺写入redis
     * @param productInfo
     */
    void saveShopInfoToRedisCache(ShopInfo productInfo);

}
