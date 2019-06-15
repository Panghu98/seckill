package com.panghu.seckill.controller;

import com.panghu.seckill.bean.User;
import com.panghu.seckill.redis.RedisService;
import com.panghu.seckill.service.UserService;
import com.sun.deploy.net.HttpResponse;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    @RequestMapping("to_list")
    public String to_login(Model model, HttpServletResponse httpServletResponse,
                           @CookieValue(value = UserService.COOKIE_NAME_TOKEN,required = false)String cookieToken,
                           @RequestParam(value = UserService.COOKIE_NAME_TOKEN,required = false) String paramToken){
            model.addAttribute("user",new User());
            if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
                return "login";
            }
            String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
            User user = userService.getByToken(httpServletResponse,token);
            model.addAttribute("user",user);
            return "goods_list";
    }

}
