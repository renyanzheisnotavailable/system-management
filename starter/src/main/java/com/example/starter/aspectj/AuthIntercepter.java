package com.example.starter.aspectj;

import com.example.common.annotation.AuthCheck;
import com.example.common.exception.ErrorCode;
import com.example.common.exception.ThrowUtils;
import com.example.web.utils.UserHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthIntercepter  {

    @Around("@annotation(authCheck)")
    public Object doIntercepter(ProceedingJoinPoint point, AuthCheck authCheck) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String value = authCheck.value();
        System.out.println(UserHolder.getUser());
        ThrowUtils.throwIfNot(UserHolder.getUser().getRole().equals(Integer.valueOf(value)), ErrorCode.NO_AUTH_ERROR);
        // 通过权限校验，放行
        return point.proceed();
    }

}
