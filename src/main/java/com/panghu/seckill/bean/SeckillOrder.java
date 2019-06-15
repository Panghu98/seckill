package com.panghu.seckill.bean;

import lombok.Data;

/**
 * @author panghu
 * @title: SeckillOrder
 * @projectName seckill
 * @date 19-6-15 下午9:56
 */
@Data
public class SeckillOrder {

    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;

}
