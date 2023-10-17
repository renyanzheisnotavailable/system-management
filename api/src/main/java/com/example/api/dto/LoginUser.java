package com.example.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Data

public class LoginUser {

    @NotNull(message = "昵称不能为空")
    private String name;
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "验证码不能为空")
    private String code;
}
