package com.example.api.dto;


import com.example.api.vo.user.UserVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MeetingAddRequest {

    private String title;

    private List<UserVO> users;

    private Integer roomId;

    private Date beginTime;

    private Date endTime;

}
