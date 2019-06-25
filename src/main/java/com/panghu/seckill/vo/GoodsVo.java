package com.panghu.seckill.vo;

import com.panghu.seckill.bean.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author panghu
 * @title: GoodsVo
 * @projectName seckill
 * @date 19-6-15 下午10:00
 */

@Data
public class GoodsVo extends Goods {

    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Integer version;

}
