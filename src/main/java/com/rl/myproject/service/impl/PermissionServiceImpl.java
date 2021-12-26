package com.rl.myproject.service.impl;

import com.rl.myproject.entity.User;
import com.rl.myproject.mapper.PermissionMapper;
import com.rl.myproject.mapper.UserMapper;
import com.rl.myproject.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author renlei
 * @date 2021/12/26 1:32
 * @description: 查询用户权限
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @author renlei
     * @date 2021/12/26 1:58
     * @description: 查询用户基本信息
     * @Param:
     * @Return:
     */
    @Override
    public User getUser(String username, String password) {
        User user = userMapper.getUser(username);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
