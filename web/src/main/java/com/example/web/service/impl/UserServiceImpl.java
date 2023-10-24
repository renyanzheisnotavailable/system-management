package com.example.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.api.domain.Logininfor;
import com.example.common.exception.ErrorCode;
import com.example.common.exception.ThrowUtils;
import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.api.dto.UserUpdateInformationRequest;
import com.example.api.vo.user.LoginVO;
import com.example.api.vo.user.UserVO;
import com.example.common.utils.ip.AddressUtils;
import com.example.common.utils.ip.IpUtils;
import com.example.common.utils.ip.ServletUtils;
import com.example.elasticsearch.LoginforRepository;
import com.example.web.service.DepartmentService;
import com.example.web.service.WorkplaceService;
import com.example.web.utils.JwtUtils;
import com.example.db.domain.User;
import com.example.db.mapper.UserMapper;
import com.example.redis.RedisService;
import com.example.web.service.UserService;
import com.example.web.utils.UserHolder;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.common.constant.RedisConstant.LOGIN_USER_KEY;

/**
* @author Chu
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private RedisService redisService;

    @Autowired
    LoginforRepository loginforRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    WorkplaceService workplaceService;


    @Override
    public Result<LoginVO> login(String email, String password) {
        //查数据库
        User user = query().eq("email", email).eq("password",password).one();
        ThrowUtils.throwIfNull(user, ErrorCode.LOGIN_ERROR);
        String token = JwtUtils.createToken(user);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        redisService.setCacheUser(LOGIN_USER_KEY + token, userVO);
        //保存threadlocl
        UserHolder.saveUser(userVO);
        //记录登录日志
        recordUserInfor(user, "");
        return ResultUtil.ok(new LoginVO(new UserVO(user), token));
    }

    @Async("taskExecutor")
    public void recordUserInfor(User user, String message) {
        Logininfor logininfor = BeanUtil.copyProperties(user, Logininfor.class);
//        logininfor.setLogId();
        //用户昵称
        logininfor.setUsername(user.getName());
        //用户部门
        logininfor.setDepartId(user.getDepartmentId());
        //用户单位
        logininfor.setCompanyId(user.getCompanyId());

        //todo
        //ip
        String ip = IpUtils.getIpAddr();
        logininfor.setIpaddr(ip);
        //地址
        String address = AddressUtils.getRealAddressByIP(ip);
        logininfor.setLoginLocation(address);

        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        logininfor.setBrowser(browser);

        logininfor.setOs(os);
        logininfor.setMsg(message);
        logininfor.setLoginTime(new Date());

        Logininfor save = loginforRepository.save(logininfor);
    }
    @Override
    public Result deactivateUser(int id) {
        User user = new User();
        user.setId(id);
        user.setStatus(0);
        updateById(user);
        return ResultUtil.ok(user);

    }

    @Override
    public Result updateInformation(UserUpdateInformationRequest userUpdate, String updateAvatar) {
        User user = BeanUtil.copyProperties(userUpdate, User.class);
        user.setAvatar(updateAvatar);
        ThrowUtils.checkUpdate(updateById(user));
        return ResultUtil.ok();
    }


}




