package com.panghu.seckill.bean;

import lombok.Data;

/**
 * @author panghu
 * @title: Goods
 * @projectName seckill
 * @date 19-6-15 下午9:56
 */
@Data
public class Goods {

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;

}
