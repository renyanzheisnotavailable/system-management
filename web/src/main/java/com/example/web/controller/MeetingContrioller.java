package com.example.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import com.example.db.domain.Meeting;
import com.example.db.domain.MeetingUser;
import com.example.api.dto.MeetingAddRequest;
import com.example.api.vo.user.UserVO;
import com.example.web.service.MeetingService;
import com.example.web.service.MeetingUserService;
import com.example.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meet")
public class MeetingContrioller {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingUserService meetingUserService;

    @PostMapping("/")
    public Result<Meeting> addMeeting(MeetingAddRequest meetingAddRequest, HttpServletRequest httpServletRequest) {
        Integer id = UserUtils.getUser(httpServletRequest).getId();
        //meeting表
        Meeting meeting = BeanUtil.copyProperties(meetingAddRequest, Meeting.class);
        meeting.setUserId(id);
        meetingService.save(meeting);
        //meeting-user
        List<UserVO> users = meetingAddRequest.getUsers();
        List<MeetingUser> meetingUsers = users.stream().map(userVO -> new MeetingUser(null, meeting.getId(), id)).collect(Collectors.toList());
        meetingUserService.saveBatch(meetingUsers);
        return ResultUtil.ok(meeting);
        //添加定时任务 创建一个任务
    }




}
