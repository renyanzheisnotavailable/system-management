package com.example.web;

import com.example.db.domain.FileType;
import com.example.db.domain.User;
import com.example.redis.RedisService;
import com.example.web.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class WebApplicationTests {
    @Autowired
    UserService userService;
    @Test
    void contextLoads1() {
       userService.save(new User());
    }

}
