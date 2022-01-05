package com.rl.myproject.controller;

import com.rl.myproject.entity.User;
import com.rl.myproject.service.PermissionService;
import com.rl.myproject.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author renlei
 * @date 2021/12/26 1:33
 * @description: 处理登录请求
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String getUser(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        ResponseResult<User> resp = new ResponseResult<>();
        User user = permissionService.getUser(username,password);
        resp.setState(HttpStatus.OK.value());
        resp.setMessage("登录成功");
        resp.setData(user);
        if (user==null) {
            resp.setMessage("没有找到该用户信息");
        }
        return "index";
    }
}
