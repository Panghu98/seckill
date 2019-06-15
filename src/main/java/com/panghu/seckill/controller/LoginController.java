package com.panghu.seckill.controller;

import com.panghu.seckill.result.Result;
import com.panghu.seckill.service.UserService;
import com.panghu.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;



/**
 * @author panghu
 */
@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {


    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {//加入JSR303参数校验
        log.info(loginVo.toString());
        String token = userService.login(response, loginVo);
        return Result.success(token);
    }

}
