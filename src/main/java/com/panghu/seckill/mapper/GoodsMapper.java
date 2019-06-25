package com.panghu.seckill.mapper;

import com.panghu.seckill.bean.Goods;
import com.panghu.seckill.bean.SeckillGoods;
import com.panghu.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author panghu
 * @title: GoodsMapper
 * @projectName seckill
 * @date 19-6-15 下午10:04
 */
public interface GoodsMapper {

    public List<GoodsVo> listGoodsVo();

    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    public int reduceStockByVersion(SeckillGoods seckillGoods);

    public int getVersionByGoodsId(@Param("goodsId") long goodsId);

}
