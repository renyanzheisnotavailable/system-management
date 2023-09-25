package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.domain.Workplace;
import com.example.web.service.WorkplaceService;
import com.example.db.mapper.WorkplaceMapper;
import org.springframework.stereotype.Service;

/**
* @author Chu
* @description 针对表【workplace】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class WorkplaceServiceImpl extends ServiceImpl<WorkplaceMapper, Workplace>
    implements WorkplaceService{

}




