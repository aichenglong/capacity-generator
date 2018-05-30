package com.test.service.service.impl;

import com.capacity.platform.common.mapper.BaseMapper;
import com.capacity.platform.common.service.impl.AbstractBaseServiceImpl;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: icl
 * Date:2018/05/27 17:02:47
 * Description:
 * Created by 2018/05/27 17:02:47 on 2018/05/27 17:02:47.
 */
@Service("Service")
public class UserServiceImpl extends AbstractBaseServiceImpl<User,> implements
UserService  {

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseMapper<User, > getBaseMapper() {
        return userMapper;
    }
}
