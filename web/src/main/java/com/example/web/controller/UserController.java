package com.example.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.common.result.ErrorCode;
import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.db.domain.User;
import com.example.db.dto.LoginUser;
import com.example.db.vo.user.LoginVO;
import com.example.redis.RedisService;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import static com.example.common.constant.RedisConstant.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    private static final String MYEMAIL = "vwugbd@163.com";

    @Resource
    private JavaMailSender sender; // 引入Spring Mail依赖后，会自动装配到IOC容器。用来发送邮件

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
        //检查是否已存在
        if(userService.query().eq("email",email).count() > 0) {
            return new Result(4001002, (Object) null,"已注册");
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
        //todo 60s内无法再发送 再一次发送token上次token无效
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
        userService.save(BeanUtil.toBean(user,User.class));
        return ResultUtil.ok();
    }

    /**
     * 登录
     *
     * 登录生成token前端储存再header 后端将token储存在redis里key token value 用户对象 存储在threadlocal
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    public Result deactivateUser(String email) {
        return userService.deactivateUser(email);
    }


    /**
     * 退出登录
     *
     * 停用用户
     *
     * 更改个人信息
     *
     * 修改密码
     *
     * 用户授权
     */

//    public Result logout() {
//
//    }




//    @Log(title = "ip")
//    @RequestMapping("/getIp")
//    public void getIp(HttpServletRequest request) {
//        String ip = request.getRemoteAddr();
//        StringBuffer requestURL = request.getRequestURL();
//        System.out.println("IP : " + ip);
//        System.out.println(requestURL);
//    }





}
