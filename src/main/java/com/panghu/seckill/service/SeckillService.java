package com.panghu.seckill.service;

import com.panghu.seckill.bean.OrderInfo;
import com.panghu.seckill.bean.SeckillOrder;
import com.panghu.seckill.bean.User;
import com.panghu.seckill.redis.RedisService;
import com.panghu.seckill.redis.SeckillKey;
import com.panghu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author panghu
 * @title: SeckillService
 * @projectName seckill
 * @date 19-6-25 下午9:35
 */
public class SeckillService {

    private GoodsService goodsService;

    private OrderService orderService;

    private RedisService redisService;

    @Autowired
    public SeckillService(GoodsService goodsService, OrderService orderService, RedisService redisService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.redisService = redisService;
    }

    //减库存　和　生成订单需要同步进行

    @Transactional
    public OrderInfo seckill(User user, GoodsVo goods){


        //减库存
        boolean success = goodsService.reduceStock(goods);
        if (success){
            //下订单　　写入秒杀订单
            return orderService.createOrder(user,goods);
        }else{
            setGoodsOver(goods.getId());
            return null;
        }
    }

    /**
     * 获取秒杀的结果
     * @return
     */
    public long getSeckillResult(long userId,long goodsId){
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(userId, goodsId);
        if (order != null){
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            }else {
                return 0;
            }
        }
    }

    //在缓存中进行库存预减 ，设置为true，则库存秒杀完毕

    private void setGoodsOver(Long goodsId){
        redisService.set(SeckillKey.isGoodsOver,""+goodsId,true);
    }

    private boolean getGoodsOver(Long goodsId){
        return redisService.exists(SeckillKey.isGoodsOver,""+goodsId);
    }


}
