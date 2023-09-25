package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.domain.MeetingUser;
import com.example.web.service.MeetingUserService;
import com.example.db.mapper.MeetingUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Chu
* @description 针对表【meeting_user】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class MeetingUserServiceImpl extends ServiceImpl<MeetingUserMapper, MeetingUser>
    implements MeetingUserService{

}




