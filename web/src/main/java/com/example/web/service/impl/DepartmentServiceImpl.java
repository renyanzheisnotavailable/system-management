package com.example.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.domain.Department;

import com.example.db.mapper.DepartmentMapper;
import com.example.web.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
* @author Chu
* @description 针对表【department】的数据库操作Service实现
* @createDate 2023-09-03 21:46:59
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService {

}




