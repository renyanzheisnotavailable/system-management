package com.example.starter.aspectj;


import com.alibaba.fastjson.JSON;
import com.example.common.annotation.Log;
import com.example.common.domain.OperLog;
import com.example.common.enums.BusinessType;
import com.example.common.utils.UserHolder;
import com.example.db.domain.Department;
import com.example.db.domain.User;
import com.example.kafka.KafkaService;

import java.util.Date;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.example.web.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
@Slf4j
@Aspect
public class LogAspectj {

    @Autowired
    KafkaTemplate template;

    @Resource
    DepartmentService departmentService;

    @Resource
    KafkaService kafkaService;

    StopWatch stopWatch = new StopWatch();


    @Before("@annotation(controllerlog)")
    public void doBefore(Log controllerlog) {
        //计时
        stopWatch.start();
    }

    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult)
    {
        OperLog operLog = new OperLog();
        //id
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        operLog.setOperId(requestId);
        //操作模块
        String title = controllerLog.title();
        operLog.setTitle(title);
        //业务模块
        BusinessType businessType = controllerLog.businessType();
        operLog.setBusinessType(businessType);

//        operLog.setBusinessTypes();


        //请求头
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //请求方法
        String className = request.getMethod();
        operLog.setMethod(className);
        //请求方式
        String requestMethod = joinPoint.getTarget().getClass().getName();
        operLog.setRequestMethod(requestMethod);



        //todo
        //操作人员
//        User user = UserHolder.getUser();
//        int name = user.getId();
//        operLog.setOperUserId(name);
        operLog.setOperUserId(1);

        //部门名称
        //todo
//        Department department = departmentService.getById(user.getDepartmentId());
//        operLog.setDeptName(department.getName());
        operLog.setDeptName("11");

        //操作地址
        String ip = request.getRemoteAddr();
        operLog.setOperIp(ip);

        // 获取请求路径
        String remoteURI = request.getRequestURI();
        operLog.setOperUrl(remoteURI);

        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        String reqParam = "[" + StringUtils.join(args, ", ") + "]";
        operLog.setOperParam(reqParam);

        operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        operLog.setStatus(0);
        operLog.setErrorMsg("");

        operLog.setOperTime(new Date(System.currentTimeMillis()));
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        operLog.setCostTime(totalTimeMillis);

        log.info("{}",operLog);
//        kafkaService.send(requestId, operLog);
        template.send("chu",requestId,operLog);

    }




}
