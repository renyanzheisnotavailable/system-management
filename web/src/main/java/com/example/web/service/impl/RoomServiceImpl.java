package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.domain.Room;
import com.example.db.mapper.RoomMapper;
import com.example.web.service.RoomService;
import org.springframework.stereotype.Service;

/**
* @author Chu
* @description 针对表【room】的数据库操作Service实现
* @createDate 2023-09-25 11:11:18
*/
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room>
    implements RoomService {

}




