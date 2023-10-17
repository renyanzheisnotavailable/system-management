package com.example.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.api.dto.LoginUser;
import com.example.api.dto.UserUpdateInformationRequest;
import com.example.common.annotation.AuthCheck;
import com.example.common.annotation.Log;
import com.example.common.constant.RoleConstant;
import com.example.common.exception.ErrorCode;
import com.example.common.exception.ThrowUtils;
import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.db.domain.User;


import com.example.api.vo.user.LoginVO;
import com.example.api.vo.user.UserVO;
import com.example.redis.RedisService;
import com.example.web.service.FileService;
import com.example.web.service.UserService;
import com.example.web.utils.JwtUtils;
import com.example.web.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.example.common.constant.RedisConstant.*;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private static final String SALT = "long";

    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;

    private static final String MYEMAIL = "vwugbd@163.com";

    // 引入Spring Mail依赖后，会自动装配到IOC容器。用来发送邮件
    @Resource
    private JavaMailSender sender;

    @Resource
    RedisService redisService;

    /**
     * 向指定邮箱发送验证码
     *
     * @param email
     * @return
     */
    @RequestMapping("/code")
    public Result code(@RequestParam String email) {
        //检查是否已存
        if(userService.query().eq("email",email).count() > 0) {
            return ResultUtil.fail(4001002, "已注册");
        }
        //生成token
        String s = RandomUtil.randomString(6);
        //保存redis
        redisService.setCacheObject(LOGIN_CODE_KEY + email, s, LOGIN_CODE_TTL);
        //发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【测试验证码】验证消息"); // 发送邮件的标题
        message.setText("登录操作，验证码："+ s + "，切勿将验证码泄露给他人，本条验证码有效期2分钟。"); // 发送邮件的内容
        message.setTo(email); // 指定要接收邮件的用户邮箱账号
        message.setFrom(MYEMAIL); // 发送邮件的邮箱账号，注意一定要和配置文件中的一致！
        sender.send(message); // 调用send方法发送邮件即可
        //显示email
        return ResultUtil.ok(s);
    }

    /**
     * 注册
     *
     * @param user
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody LoginUser user) {
        String email = user.getEmail();
        //验证码是否正确
        //.1获得redis中token
        String cacheToken = redisService.getCacheObject(LOGIN_CODE_KEY + email);
        if(!user.getCode().equals(cacheToken)) {
            return ResultUtil.fail(ErrorCode.CODE_ERROR);
        }
        //添加用户数据库
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + user.getPassword()).getBytes());
        System.out.println(encryptPassword);
        User userAfterEncrypt = BeanUtil.toBean(user, User.class);
        userAfterEncrypt.setPassword(encryptPassword);
        userService.save(userAfterEncrypt);
        return ResultUtil.ok();
    }

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestParam @Email String email, @RequestParam @NotBlank String password) {
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        return userService.login(email, encryptPassword);
    }


    /**
     * 停用用户
     *
     * @param id
     * @return
     */
    @PostMapping("/deactivateUser")
    @AuthCheck(RoleConstant.ROLE_ADMIN)
    public Result deactivateUser(Integer id) {
        return userService.deactivateUser(id);
    }


    /**
     * 退出登录
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest httpServletRequest) {
        System.out.println(UserHolder.getUser());
        //threallocal删除
        UserHolder.removeUser();
        //redis删除
        String token = httpServletRequest.getHeader("Authorization");
        redisService.deleteObject(LOGIN_USER_KEY + token);
        return ResultUtil.ok();
    }

    /**
     * 更改个人信息
     *
     * @param file
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Result updateInformation(MultipartFile file,  UserUpdateInformationRequest user) {
        System.out.println(user.toString());
        return userService.updateInformation(user, fileService.updateAvatar(file));
    }

    /**
     * 更改密码
     *
     * @param password
     * @return
     */
    @GetMapping("/updatePassword")
    public Result updateInformation(@NotBlank String password) {
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        User user = new User();
        user.setId(UserHolder.getUser().getId());
        user.setPassword(encryptPassword);
        ThrowUtils.checkUpdate(userService.updateById(user));
        String token = JwtUtils.createToken(user);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        redisService.setCacheUser(LOGIN_USER_KEY + token, userVO);
        return ResultUtil.ok(new LoginVO(new UserVO(user), token));
    }


    /**
     * 授权
     *
     * @param id
     * @param role
     * @return
     */
    @AuthCheck(RoleConstant.ROLE_ADMIN)
    @GetMapping("/empowerUser")
    public Result EmpowerUser(@NotNull Integer id, @NotNull Integer role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        ThrowUtils.checkUpdate(userService.updateById(user));
        return ResultUtil.ok();
    }


    @Log(title = "fr")
    @PostMapping("/getIp")
    public void getIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("IP : " + ip);
        System.out.println(requestURL);
    }

    @RequestMapping("/code1")
    public Result code1(@RequestParam String email) {

        return ResultUtil.ok();
    }


}
