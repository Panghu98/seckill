package com.panghu.seckill.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author panghu
 * @title: User
 * @projectName seckill
 * @date 19-6-10 下午4:53
 */
@Data
public class User {

    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

}
