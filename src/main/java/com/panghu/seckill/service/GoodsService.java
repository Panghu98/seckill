package com.panghu.seckill.service;

import com.panghu.seckill.bean.SeckillGoods;
import com.panghu.seckill.mapper.GoodsMapper;
import com.panghu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author panghu
 * @title: GoodsService
 * @projectName seckill
 * @date 19-6-25 下午8:53
 */
public class GoodsService {

    //乐观锁冲突最大重试次数
    private static final int DEFAULT_MAX_RETRIES = 5;

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 查询商品列表
     *
     * @return
     */
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    /**
     * 根据id查询指定商品
     *
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * 减少库存，每次减一
     *
     * @return
     */
    public boolean reduceStock(GoodsVo goods) {
        int numAttempts = 0;
        int ret = 0;
        SeckillGoods sg = new SeckillGoods();
        sg.setGoodsId(goods.getId());
        sg.setVersion(goods.getVersion());
        do {
            numAttempts++;
            try {
                //获取秒杀商品的版本号
                sg.setVersion(goodsMapper.getVersionByGoodsId(goods.getId()));

                //悲观锁  只能进行一次 其他的就会抛出异常
                ret = goodsMapper.reduceStockByVersion(sg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ret != 0)
                break;
        } while (numAttempts < DEFAULT_MAX_RETRIES);

        return ret > 0;
    }

}
