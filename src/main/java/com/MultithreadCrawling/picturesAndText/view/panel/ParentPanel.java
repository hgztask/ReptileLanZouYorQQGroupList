package com.MultithreadCrawling.picturesAndText.view.panel;

import com.MultithreadCrawling.picturesAndText.view.TextEdtorView;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * 该面板是所有面板共有的控件
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 14:51
 */
@Getter
@Setter
public class ParentPanel {


    /**
     * 列表信息
     */
    private TextEdtorView textEdtorView = new TextEdtorView("列表信息");


    /**
     * 每一个对象一个面板
     */
    @Getter
    private JPanel modelJPanel = new JPanel(new GridLayout(2, 1));


    /**
     * 顶部面板
     */
    private JPanel topJPanel = new JPanel();

    /**
     * 底部面板
     */
    private JPanel bottJPanel = new JPanel();


    private JButton openWebJbutton = new JButton("打开浏览器");

    /**
     * 加载网页
     */
    private JButton loadUrlJbutton = new JButton("加载网页");


    /**
     * 打印源码信息
     */
    private JButton printWebJButton = new JButton("打印源码信息");


    /**
     * 获取代理浏览器监听的网络请求信息
     */
    private JButton demoJButton = new JButton("获取代理浏览器网络信息");


    /**
     * 打印网页中所有列表信息
     */
    private JButton printListJButton = new JButton("打印网页中所有列表信息");


    /**
     * 写入列表文件
     */
    private JButton writeListJbutton = new JButton("写入列表文件");

    /**
     * 右击打开浏览器控件-弹出菜单
     */
    private JPopupMenu webWindowjPopupMenu = new JPopupMenu();


    /**
     * 最大化浏览器窗口
     */
    private JMenuItem webWindowsMax = new JMenuItem("最大化浏览器窗口");

    /**
     * 最小化浏览器窗口
     */
    private JMenuItem webWindowsMix = new JMenuItem("最小化浏览器窗口");

    /**
     * 全屏窗口
     */
    private JMenuItem webWindowsfullscreen = new JMenuItem("全屏浏览器窗口");

    /**
     * 默认窗口大小
     */
    private JMenuItem webWindowsdef = new JMenuItem("默认浏览器窗口大小");

    /**
     * 获取web窗口大小
     */
    private JMenuItem webWindowsSize = new JMenuItem("获取web窗口大小");

    /**
     * 打开新的浏览器窗口
     */
    private JMenuItem newWebWindowsItem = new JMenuItem("打开新的浏览器窗口");

    /**
     * 滚动页面底部
     */
    private JMenuItem scrollBottomItem = new JMenuItem("滚动页面底部");


    /**
     * 持续滚动页面底部
     */
    private final JCheckBoxMenuItem continuedScrollBottomItem = new JCheckBoxMenuItem("持续滚动页面底部");

    /**
     * 开启浏览器代理
     */
    private final JCheckBoxMenuItem webAgentItem = new JCheckBoxMenuItem("开启浏览器代理");

    /**
     * 关闭浏览器
     */
    private JMenuItem closeJItem = new JMenuItem("关闭浏览器");
}
