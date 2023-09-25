package com.example.db.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginUser {

    private Integer id;

    private String name;

    private String email;

    private String password;

    private Integer departmentId;

    private String code;
}
