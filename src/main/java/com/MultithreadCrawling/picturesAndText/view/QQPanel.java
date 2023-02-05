package com.MultithreadCrawling.picturesAndText.view;

import com.MultithreadCrawling.picturesAndText.view.panel.ParentPanel;
import lombok.Data;

import javax.swing.*;

/**
 * QQ面板层
 */
@Data
public class QQPanel extends ParentPanel {

    private JButton equalsArray = new JButton("比较两个数组相同的成员");

    private JLabel jLabelOnePath = new JLabel("路径1");

    private JLabel jLabelTwoPath = new JLabel("路径2");

    /**
     * 单例
     */
    private static final QQPanel QQ_PANEL = new QQPanel();


}
