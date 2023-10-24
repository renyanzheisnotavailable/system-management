package com.example.starter;

import com.example.api.domain.Logininfor;
import com.example.api.domain.OperLog;
import com.example.common.utils.DateUtil;
import com.example.db.domain.FileType;

import com.example.db.mapper.FileTypeMapper;
import com.example.api.vo.user.UserVO;
import com.example.elasticsearch.LoginforRepository;
import com.example.elasticsearch.OperLogRepository;
import com.example.redis.RedisService;
import com.example.web.service.FileTypeService;
import com.example.web.service.UserService;
import com.example.web.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.Date;
import java.util.List;

@SpringBootTest
class StarterApplicationTests {
    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;
    @Autowired
    FileTypeService fileTypeService;
    @Autowired
    FileTypeMapper fileTypeMapper;
    @Autowired
    OperLogRepository operLogRepository;
    @Autowired
    LoginforRepository loginforRepository;
    @Test
    void contextLoads() {
        redisService.setCacheObject("1","1");
    }

    @Test
    void contextLoads1() {
        fileTypeMapper.insert(new FileType());
    }

    @Test
    void test2() {
        String path = new java.io.File("").getAbsolutePath();
        StringBuffer stringBuffer = new StringBuffer(path);
        System.out.println(path);

    }

    @Test
    void test3() {
        fileTypeService.getById(1);
        System.out.println(fileTypeService.getById(1));

    }

    @Test
    void test4() {
        System.out.println(DateUtil.parseCron(new Date(System.currentTimeMillis())));

    }

    @Test
    void test5() {
        UserVO userVO = JwtUtils.verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX3JvbGUiOjAsInVzZXJfaWQiOjEsInVzZXJfbmFtZSI6ImNodSIsImlzcyI6ImNodSIsImV4cCI6MTY5Njk4OTcwMSwiaWF0IjoxNjk2OTAzMzAxfQ.zKUublpFANEakJ7veRQSdWGlhWuYKgWAHI7Ya0Fen0M");
        System.out.println(userVO);

    }

    @Test
    void test6() {
        Page<OperLog> all = operLogRepository.findAll(PageRequest.of(0, 20));
        List<OperLog> content = all.getContent();
        System.out.println(content);
    }

    @Test
    void test7() {
        Page<Logininfor> all = loginforRepository.findAll(PageRequest.of(0, 20));
        List<Logininfor> content = all.getContent();
        System.out.println(content);
    }

    @Test
    void test8() {
        System.out.println(new Date(System.currentTimeMillis()));
    }



}
