package com.example.common.saToken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.util.SaResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {

        return null;
    }


}
