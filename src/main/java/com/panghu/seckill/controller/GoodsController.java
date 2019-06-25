package com.panghu.seckill.controller;

import com.panghu.seckill.bean.User;
import com.panghu.seckill.redis.GoodsKey;
import com.panghu.seckill.redis.RedisService;
import com.panghu.seckill.service.GoodsService;
import com.panghu.seckill.service.UserService;
import com.panghu.seckill.vo.GoodsVo;
import javafx.application.Application;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author panghu
 * @title: GoodsController
 * @projectName seckill
 * @date 19-6-13 下午4:59
 */
@RequestMapping("/goods")
@Controller
public class GoodsController {

    private UserService userService;

    private RedisService redisService;

    private GoodsService goodsService;




    @Autowired
    public GoodsController(UserService userService, RedisService redisService,
                           GoodsService goodsService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    /**
     * 商品列表页面
     * QPS:433
     * 1000 * 10
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(Model model, User user) {

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!org.springframework.util.StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsList);


        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        //结果输出
        return html;
    }

}
