package com.test.service.service.impl;

import com.capacity.platform.common.mapper.BaseMapper;
import com.capacity.platform.common.service.impl.AbstractBaseServiceImpl;
import com.test.entity.SysUser;
import com.test.mapper.SysUserMapper;
import com.test.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: icl
 * Date:2018/05/27 17:06:55
 * Description:
 * Created by 2018/05/27 17:06:55 on 2018/05/27 17:06:55.
 */
@Service("sysUserService")
public class SysUserServiceImpl extends AbstractBaseServiceImpl<SysUser,String> implements
SysUserService  {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public BaseMapper<SysUser, String> getBaseMapper() {
        return sysUserMapper;
    }
}
