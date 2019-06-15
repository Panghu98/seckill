package com.panghu.seckill.vo;


import com.panghu.seckill.annotation.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * @author panghu
 */
@Data
public class LoginVo {

    @IsMobile
    @NotNull
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

}
