package com.panghu.seckill.mapper;

import com.panghu.seckill.bean.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author panghu
 * @title: UserMapper
 * @projectName seckill
 * @date 19-6-10 下午5:25
 */
public interface UserMapper {

    public User getById(long id);

}
