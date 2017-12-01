package com.yx.test.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * 机器人点击屏幕
 */
public class RobotClick extends JFrame {
    public static void main(String[] args) {
        try {
            Robot r = new Robot();
            int x = 260;//横坐标
            int y = 210;//纵坐标
            r.mouseMove(x, y);
            //单击
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            //双击
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (Exception e) {
            System.out.println("error :" + e);
        }
    }
}