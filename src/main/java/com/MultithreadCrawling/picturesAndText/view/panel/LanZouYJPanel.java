package com.MultithreadCrawling.picturesAndText.view.panel;

import lombok.Data;

import javax.swing.*;

/**
 * 蓝奏云面板
 * <p>
 * 核心是输入蓝奏云特需要的组件控件
 */
@Data
@SuppressWarnings("all")
public class LanZouYJPanel extends ParentPanel {
    /**
     * 监听网页列表变化
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JRadioButton monitorJRadioButton = new JRadioButton("监听网页列表变化");

    /**
     * 打印网页中新的列表信息
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JButton printNewListJButton = new JButton("打印网页中新的列表信息");


    /**
     * 打印网页中列表的直链
     */
    private JButton printUrlListJButton = new JButton("打印网页中的列表直链信息");

    /**
     * 打印外部列表信息
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JButton printOldListJButton = new JButton("打印外部数据列表信息");

    private JPopupMenu monitorJRadiojPopupMenu = new JPopupMenu();

    /**
     * 右击打印网页中的列表直链信息
     */
    private JPopupMenu printUrlListjPopupMenu = new JPopupMenu();


    private JMenuItem apiUrlListjMenuItem = new JMenuItem("使用api获取");
    /**
     * 使用代理浏览器获取
     */
    private JMenuItem proxyUrlListjMenuItem = new JMenuItem("使用代理浏览器获取");


    /**
     * 右击url标签弹出菜单
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JPopupMenu urlJLabeljPopupMenu = new JPopupMenu();


    /**
     * 新内容自动追加列表信息
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private final JCheckBoxMenuItem appendWebItem = new JCheckBoxMenuItem("新内容自动追加列表信息");

    /**
     * 编辑要加载的url
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private final JMenuItem editUrlItem = new JMenuItem("编辑要加载的url");


    /**
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JButton fIleDataJButton = new JButton("设置外部数据");

    /**
     * 显示外部数据路径
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JLabel pathFileJLabel = new JLabel("显示外部数据路径======================");

    /**
     * 显示url
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JLabel urlJLabel = new JLabel("显示url==========================");


    /**
     * 追加新列表信息
     */
    private JButton addListJButton = new JButton("追加新列表信息");

    /**
     * 模拟点击下一页
     */
    private JButton startPageJButton = new JButton("模拟点击下一页");

    /**
     * 打印文件的直链
     */
    private final JButton printFileUrlJButton = new JButton("打印文件的直链");
    /**
     * 执行获取文件夹内的文件元素
     */
    private final JButton filesInFolderJButton = new JButton("获取文件夹内的文件元素");

    private JLabel countIndexJLabel = new JLabel("总个数:");
    private JLabel externalCountIndexJLabel = new JLabel("外部个数:");

    private JLabel newCountIndexJLabel = new JLabel("新增个数:");

    /**
     * 点击下一页的右击菜单
     */
    private JPopupMenu startPagejPopupMenu = new JPopupMenu();


    /**
     * 循环点击下一页
     */
    JCheckBoxMenuItem whileCHicjMenuItem = new JCheckBoxMenuItem("循环点击下一页");


}

