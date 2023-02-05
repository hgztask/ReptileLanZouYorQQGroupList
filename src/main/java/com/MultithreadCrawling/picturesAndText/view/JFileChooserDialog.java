package com.MultithreadCrawling.picturesAndText.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 自定义文件对话框组件,拓展其功能实现
 */
public class JFileChooserDialog {
    private JFileChooser jFileChooser = new JFileChooser();

    private Component component;

    @Getter
    private File file;

    /**
     * 确定按钮显示文本和窗口的标题
     */
    private String approveButtonText;

    /**
     * 设置要依附的组件
     *
     * @param component 依附的组件对象
     */
    public JFileChooserDialog(Component component) {
        this.component = component;
    }

    /**
     * 设置要依附的组件和窗口title及确定显示的文本
     *
     * @param component         依附的组件对象
     * @param approveButtonText 窗口title及确定显示的文本
     */
    public JFileChooserDialog(Component component, String approveButtonText) {
        this.component = component;
        this.approveButtonText = approveButtonText;
    }

    /**
     * 文件选择器选择的状态
     */
    @Getter
    private int state;

    {
        //设置只能选择文件
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }


    /**
     * 显示文件选择器对话框
     *
     * @param approveButtonText 确定按钮显示内容
     */
    public void show(String title, String approveButtonText) {
        jFileChooser.setDialogTitle(title);
        this.approveButtonText = approveButtonText;
        is(jFileChooser.showDialog(component, approveButtonText));
    }

    /**
     * 显示文件选择器对话框,会阻塞主线程
     */
    public void show() {
        show("选择文件", "选择文件");
    }


    public void setPath(String path) {
        setPath(new File(path));
    }

    public void setPath(File file) {
        jFileChooser.setCurrentDirectory(file);
    }


    /**
     * 判断状态
     *
     * @param i
     */
    @SuppressWarnings("all")
    private void is(int i) {
        state = i;
        switch (i) {
            case JFileChooser.CANCEL_OPTION:
                break;
            case JFileChooser.APPROVE_OPTION:
                file = jFileChooser.getSelectedFile();
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("jFileChooserView对象出现错误了");
                break;
        }
    }


    /**
     * 标题
     *
     * @return 标题字符串
     */
    public String getDialogTitle() {
        return jFileChooser.getDialogTitle();
    }
}
