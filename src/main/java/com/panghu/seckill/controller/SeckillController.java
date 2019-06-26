package com.panghu.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.panghu.seckill.bean.User;
import com.panghu.seckill.rabbitmq.MQSender;
import com.panghu.seckill.redis.GoodsKey;
import com.panghu.seckill.redis.RedisService;
import com.panghu.seckill.result.CodeMsg;
import com.panghu.seckill.result.Result;
import com.panghu.seckill.service.GoodsService;
import com.panghu.seckill.service.OrderService;
import com.panghu.seckill.service.SeckillService;
import com.panghu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author panghu
 * @title: SeckillController
 * @projectName seckill
 * @date 19-6-25 下午9:23
 */
@Controller
@RequestMapping("/sekill")
public class SeckillController implements InitializingBean {

    private GoodsService goodsService;

    private OrderService orderService;

    private SeckillService seckillService;

    private RedisService redisService;

    private MQSender mqSender;

    @Autowired
    public SeckillController(GoodsService goodsService, OrderService orderService,
                             SeckillService seckillService, RedisService redisService,
                             MQSender mqSender) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.seckillService = seckillService;
        this.redisService = redisService;
        this.mqSender = mqSender;
    }

    /**
     *     基于令牌桶算法实现的限流实体类
     */
    private RateLimiter rateLimiter;

    /**
     * 做标记  判断该商品是否被处理过了
     */
    private HashMap<Long,Boolean> localOverMap = new HashMap<>();

    /**
     * GET POST
     * 1.GET具有幂等性，服务端获取数据，无论调用多少次结果都一样
     * 2.POST 向服务器提交数据，不是幂等性
     *
     * 解决用户同时想服务器发送两个请求
     * 利用数据库的唯一索引，使得数据无法插入，进行回滚操作  这样就可以保证只能秒杀到一件商品
     * 但是在实际的运用当中会通过添加验证码  这样是的用户不能同时转发两个请求
     *
     * <br>
     *     将同步下单通过MQ转化为异步下单
     */

    @PostMapping("/do_seckill")
    @ResponseBody
    public Result<Integer> list(Model model, User user, @RequestParam("goodsId") long goodsId) throws Exception {
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MICROSECONDS)){
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }

        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        model.addAttribute("user",user);

        //内存标记，减少对redis的访问
        boolean over = localOverMap.get(goodsId);
        if (over){
            //如果缓存中不存在则说明秒杀库存商品为空
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //预减库存
        long stack = redisService.decr(GoodsKey.getGoodsStock,""+goodsId);
        if (stack < 0){
            afterPropertiesSet();
        }


    }

    //通过继承 InitializingBean，重写afterPropertiesSet
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList == null){
            return;
        }

        for (GoodsVo goodsVo : goodsVoList){
            redisService.set(GoodsKey.getGoodsStock,""+goodsVo.getId(),goodsVo.getStockCount());
            //初始化商品都是没有处理过的

        }
    }
}
