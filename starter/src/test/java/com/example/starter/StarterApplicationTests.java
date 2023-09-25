package com.example.starter;

import com.example.db.domain.FileType;
import com.example.db.domain.User;
import com.example.db.mapper.FileTypeMapper;
import com.example.redis.RedisService;
import com.example.web.service.FileTypeService;
import com.example.web.service.UserService;
import org.hamcrest.core.SubstringMatcher;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.lang.model.element.ModuleElement;

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


}
