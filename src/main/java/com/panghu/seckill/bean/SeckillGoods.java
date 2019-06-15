package com.panghu.seckill.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author panghu
 * @title: SeckillGoods
 * @projectName seckill
 * @date 19-6-15 下午9:55
 */
@Data
public class SeckillGoods {

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private int version;

}
