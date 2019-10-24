package com.roncoo.eshop.cache.kafka;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;
import com.roncoo.eshop.cache.service.CacheService;
import com.roncoo.eshop.cache.spring.SpringContext;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.utils.Json;
import org.springframework.boot.jackson.JsonObjectDeserializer;

/**
 * @ClassName KafkaMessageProcessor
 * @Description kafka消息处理线程
 * @Author wuwenxiang
 * @Date 2019-07-16 21:49
 * @Version 1.0
 **/
public class KafkaMessageProcessor implements Runnable {

    private CacheService cacheService;

    private KafkaStream kafkaStream;

    public KafkaMessageProcessor(KafkaStream kafkaStream) {
        this.kafkaStream = kafkaStream;
        this.cacheService = (CacheService) SpringContext.getApplicationContext().getBean("cacheService");
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            String message = new String(it.next().message());
            //首先将message转换为Json对象；
            JSONObject messageJsonObject = JSONObject.parseObject(message);
            //从这里提取出消息对应的服务的标识；
            String serviceId = messageJsonObject.getString("serviceId");
            //如果是商品信息服务，就去调用
            if("productInfoService".equals(serviceId)){
                //TODO
                processProductInfoChangeMessage(messageJsonObject);
            }else if("shopInfoService".equals(serviceId)){
                processShopInfoChangeMessage(messageJsonObject);
            }
        }
    }

    /**
     * 处理商品信息变更的消息
     * @param jsonObject
     */
    private void processShopInfoChangeMessage(JSONObject jsonObject){
        //提取出商品id
        Long productId = jsonObject.getLong("productId");
        //调用商品信息服务的接口；

        //getProductInfo?productId=1 传递过去

        //店铺信息服务，一般来说就会去查询数据库，去获取productId=1的商品信息，然后返回回来；
        String shopInfoJson = "{\"id\":1,\"name\":\"小王的手机店\",\"level\":\"5\",\"goodCommentRate\":\"0.8\"}";
        ShopInfo shopInfo = JSONObject.parseObject(shopInfoJson,ShopInfo.class);
        cacheService.saveShopInfoToLocalCache(shopInfo);
        System.out.println("获取到刚保存到本地缓存的店铺信息："+cacheService.getShopInfoFromLocalCahe(productId));

        cacheService.saveShopInfoToRedisCache(shopInfo);
    }

    private void processProductInfoChangeMessage(JSONObject jsonObject){
        //提取出商品id
        Long productId = jsonObject.getLong("productId");

        Long shopId = jsonObject.getLong("shopId");
        //调用商品信息服务的接口；

        //getProductInfo?productId=1 传递过去

        //商品信息服务，一般来说就会去查询数据库，去获取productId=1的商品信息，然后返回回来；

        String productInfoJson = "{\"id\":1,\"name\":\"iphone7\",\"price\":5554,\"pictureList\":\"a.jpg\",\"specification\":\"iphone7的规格\",\"service\":\"iphone7售后\",\"color\":\"黑色\",\"size\":\"5.5\",\"shopId\":\"21\"}";
        ProductInfo productInfo = JSONObject.parseObject(productInfoJson,ProductInfo.class);
        cacheService.saveProductInfoToLocalCache(productInfo);

        System.out.println("获取到刚保存到本地缓存的商品信息："+cacheService.getProductInfoFromLocalCahe(shopId));

        cacheService.saveProductInfoToRedisCache(productInfo);
    }

}
