package com.roncoo.eshop.cache.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;
import com.roncoo.eshop.cache.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @ClassName CacheServiceImpl
 * @Description 实现类
 * @Author wuwenxiang
 * @Date 2019-07-15 20:19
 * @Version 1.0
 **/
@Service("cacheService")
public class CacheServiceImpl implements CacheService {


    @Resource
    private JedisCluster jedisCluster;

    /**
     * ehcache.xml 中自定义缓存的名字
     */
    private static final String CACHE_NAME = "local";

    @CachePut(value = CACHE_NAME, key = "'product_info_'+#productInfo.getId()")
    public ProductInfo saveLocalCache(ProductInfo productInfo) {
        System.out.println(productInfo);
        return productInfo;
    }

    @Cacheable(value = CACHE_NAME, key = "'product_info_'+#id")
    public ProductInfo getLocalCache(Long id) {
        return null;
    }

    @Cacheable(value = CACHE_NAME, key = "'product_info_'+#productId")
    public ProductInfo getProductInfoFromLocalCahe(long productId) {
        return null;
    }

    @Cacheable(value = CACHE_NAME, key = "'shop_info_'+#shopId")
    public ProductInfo getShopInfoFromLocalCahe(long shopId) {
        return null;
    }

    @CachePut(value = CACHE_NAME, key = "'product_info_'+#productInfo.getId()")
    public ProductInfo saveProductInfoToLocalCache(ProductInfo productInfo) {

        return null;
    }

    @Override
    public void saveProductInfoToRedisCache(ProductInfo productInfo) {
        String key = "product_info"+productInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(productInfo));
    }

    @CachePut(value = CACHE_NAME, key = "'shop_info_'+#shopInfo.getId()")
    public ShopInfo saveShopInfoToLocalCache(ShopInfo shopInfo) {

        return null;
    }

    @Override
    public void saveShopInfoToRedisCache(ShopInfo shopInfo) {
        String key = "shop_info"+shopInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(shopInfo));
    }


}
