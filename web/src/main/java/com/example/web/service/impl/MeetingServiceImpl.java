package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.domain.Meeting;
import com.example.web.service.MeetingService;
import com.example.db.mapper.MeetingMapper;
import org.springframework.stereotype.Service;

/**
* @author Chu
* @description 针对表【meeting】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting>
    implements MeetingService{

}




