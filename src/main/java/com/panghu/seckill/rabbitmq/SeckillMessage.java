package com.panghu.seckill.rabbitmq;

import com.panghu.seckill.bean.User;

/**
 * 消息体
 * @author panghu
 */
public class SeckillMessage {

    private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
