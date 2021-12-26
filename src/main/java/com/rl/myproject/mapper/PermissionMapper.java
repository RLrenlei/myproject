package com.rl.myproject.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author renlei
 * @date 2021/12/26 1:13
 * @description: 权限mapper
 */
@Repository
public interface PermissionMapper {
    /**
     * @author renlei
     * @date 2021/12/26 1:18
     * @description: 查找权限标志符
     * @Param:
     * @Return:
     */
    String selectPermissionByUser(@Param("userId") String userId);
}
