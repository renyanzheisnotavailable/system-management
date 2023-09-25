package com.example.db.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVO {
    private int id;
    private String path;
    private String method;
    private int status;
    private String name;
    private List<MenuVO> children;
}
