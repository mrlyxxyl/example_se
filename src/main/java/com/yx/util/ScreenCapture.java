package com.yx.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 截屏
 */
public class ScreenCapture {
    public static void main(String[] args) {
        try {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            BufferedImage bim = new Robot().createScreenCapture(new Rectangle(0, 0, dim.width, dim.height));
            ImageIO.write(bim, "jpg", new File("E:\\test.jpg")); //写出到目标文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}