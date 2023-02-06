package com.MultithreadCrawling.picturesAndText.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 编辑框窗口
 * 用于显示相关信息
 */
public class TextEdtorView {

    /**
     * 窗口
     */
    private JFrame jFrame = new JFrame("展示信息");

    /**
     * 文本域对象
     * 用于编辑器
     */
    private TextArea textArea = new TextArea();




    {
        //获取屏幕分辨率大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() / 2.);
        int height = (int) (screenSize.getHeight() / 2);
        jFrame.setSize(width, height);
        jFrame.add(textArea);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jFrame.dispose();
            }
        });
    }


    public TextEdtorView() {
    }

    /**
     * @param title   窗口标题
     * @param content 窗口编辑框内容
     */
    public TextEdtorView(String title, String content) {
        jFrame.setTitle(title);
        textArea.setText(content);
    }

    /**
     * @param title 窗口标题
     */
    public TextEdtorView(String title) {
        jFrame.setTitle(title);
    }


    /**
     * 设置编辑框内容
     *
     * @param content 内容
     */
    public void setText(String content) {
        textArea.setText(content);
    }


    /**
     * 设置窗口标题
     * @param windowTitle 标题
     */
    public void setWindowTitle(String windowTitle) {
        jFrame.setTitle(windowTitle);
    }


    /**
     * 显示窗口
     */
    public void show() {
        jFrame.setVisible(true);
    }


}
