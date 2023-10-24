package com.example.web.controller;


import com.example.api.domain.Logininfor;
import com.example.api.domain.OperLog;
import com.example.common.annotation.AuthCheck;
import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.elasticsearch.LoginforRepository;
import com.example.elasticsearch.OperLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    OperLogRepository operLogRepository;

    @Resource
    LoginforRepository loginforRepository;

    //查询全部登录日志
    //查询全部操作日志
    @AuthCheck
    @GetMapping("/getOperLog")
    public Result<List<OperLog>> queryAllOperLog(PageRequest pageRequest) {
        Page<OperLog> all = operLogRepository.findAll(pageRequest);
        List<OperLog> content = all.getContent();
        return ResultUtil.ok(content);
    }

    @AuthCheck
    @GetMapping("/getLoginLog")
    public Result<List<Logininfor>> queryAllLoginLog(PageRequest pageRequest) {
        Page<Logininfor> all = loginforRepository.findAll(pageRequest);
        return ResultUtil.ok(all.getContent());
    }
}
