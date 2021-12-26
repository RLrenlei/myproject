package com.rl.myproject.mapper;

import com.rl.myproject.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author renlei
 * @date 2021/12/26 1:52
 * @description: 查询用户基本信息
 */
@Repository
public interface UserMapper {
    User getUser(@Param("username") String username);
}
