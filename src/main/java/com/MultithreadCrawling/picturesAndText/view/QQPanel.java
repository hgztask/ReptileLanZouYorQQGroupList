package com.MultithreadCrawling.picturesAndText.view;

import com.MultithreadCrawling.picturesAndText.view.panel.ParentPanel;
import lombok.Data;
import lombok.NonNull;

import javax.swing.*;
import java.io.File;

/**
 * QQ面板层
 */
@Data
public class QQPanel extends ParentPanel {

    private JButton equalsArrJButton = new JButton("比较两个数组相同的成员");

    private JLabel jLabelOnePath = new JLabel("路径1");

    private JLabel jLabelTwoPath = new JLabel("路径2");

    /**
     * 第一个file对象
     */
    private File oneFIle;

    /**
     * 第二个file对象
     */
    private File twoFIle;


    public void setOneFIle(File oneFIle) {
        this.oneFIle = oneFIle;
        this.jLabelOnePath.setText("路径1=" + oneFIle);
    }

    public void setTwoFIle(File twoFIle) {
        this.twoFIle = twoFIle;
        this.jLabelTwoPath.setText("路径2=" + twoFIle);
    }
}
