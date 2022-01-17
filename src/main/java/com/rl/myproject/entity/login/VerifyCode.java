package com.rl.myproject.entity.login;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author RenLei
 * @description 处理图片
 * @date 2022/1/11 23:48
 */
public class VerifyCode {

    private static final String BASE_NUM_LETTER = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    public static String drawRandomText(int width, int height, BufferedImage verifyImg) {
        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 40));

        StringBuffer sb = new StringBuffer();
        // 旋转原点的x坐标
        int x = 10;
        String ch = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            graphics.setColor(getRandomColor());
            // 设置字体旋转角度,小于30度
            int degree = random.nextInt() % 30;
            int dot = random.nextInt(BASE_NUM_LETTER.length());
            ch = BASE_NUM_LETTER.charAt(dot) + "";
            sb.append(ch);

            // 正向旋转
            graphics.rotate(degree * Math.PI/180, x, 45);
            // 反向旋转
            graphics.rotate(-degree * Math.PI/180, x, 45);
            graphics.drawString(ch,(i+1)*width/6,height-height/4);
            x += 48;
        }

        // 画干扰线
        for (int i = 0; i < 6; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机划线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }

        // 添加噪点
        for (int i = 0; i < 30; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2, 2);
        }
        return sb.toString();
    }

    // 随机取色
    public static Color getRandomColor() {
        Random random = new Random();
        Color color = new Color(random.nextInt(256),
                random.nextInt(256),random.nextInt(256));
        return color;
    }
}
