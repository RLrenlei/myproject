package com.rl.myproject.service;

import com.rl.myproject.entity.User;

public interface PermissionService {

    User getUser(String username, String password);
}
