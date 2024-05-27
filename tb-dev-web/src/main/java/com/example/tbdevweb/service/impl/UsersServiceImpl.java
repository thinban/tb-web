package com.example.tbdevweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tbdevweb.domain.Users;
import com.example.tbdevweb.mapper.UsersMapper;
import com.example.tbdevweb.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2024-05-27 21:49:31
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {
}




