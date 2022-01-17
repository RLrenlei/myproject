package com.rl.myproject.controller;

import com.rl.myproject.entity.User;
import com.rl.myproject.entity.login.VerifyCode;
import com.rl.myproject.service.PermissionService;
import com.rl.myproject.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

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

    private static final int IMG_WIDTH = 160;
    private static final int IMG_HEIGHT = 40;

    /**
     * @author RenLei
     * @description 获取验证码
     * @date 2022/1/11 23:47
     * @params [request, response]
     */
    @GetMapping("/getVerifyCode")
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成对应宽高的初始图片
        BufferedImage verifyImg = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_BGR);
        // 生成验证码字符并加上噪点，干扰线，返回值为验证码字符串
        String randomText = VerifyCode.drawRandomText(IMG_WIDTH, IMG_HEIGHT, verifyImg);
        request.getSession().setAttribute("verfifyCode", randomText);
        response.setContentType("image/png"); //设置为图片
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(verifyImg, "png", os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
