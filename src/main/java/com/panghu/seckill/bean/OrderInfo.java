package com.panghu.seckill.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author panghu
 * @title: OrderInfo
 * @projectName seckill
 * @date 19-6-15 下午9:57
 */
@Data
public class OrderInfo {

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;

}
