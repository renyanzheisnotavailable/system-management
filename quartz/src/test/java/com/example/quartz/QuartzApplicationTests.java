package com.example.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuartzApplicationTests {

    @Test
    void contextLoads() throws ClassNotFoundException {
        Class jobClass = Class.forName("com.example.quartz.JobOne");
        System.out.println(jobClass);
    }

}
