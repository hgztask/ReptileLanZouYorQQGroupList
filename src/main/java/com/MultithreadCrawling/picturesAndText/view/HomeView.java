package com.MultithreadCrawling.picturesAndText.view;

import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * HOme窗体层,属于显示层
 */
@Data
public class HomeView {
    /**
     * 窗口
     */
    private JFrame jFrame = new JFrame("主页");


    /**
     * 多面板面板
     */
    @Getter
    private JTabbedPane jTabbedPane = new JTabbedPane();

    /**
     * 菜单栏
     */
    @Getter
    private JMenuBar menuBar = new JMenuBar();

    @Getter
    private final JMenu menuWindow = new JMenu("窗口");


    private JMenuItem windowTopItem = new JMenuItem("窗口置顶");

    private JMenuItem windowNotTopItem = new JMenuItem("窗口取消置顶");

    private JMenuItem windowPrintSizeItem = new JMenuItem("打印窗口大小信息");

    private JMenuItem windowReSizeItem = new JMenuItem("允许调整窗口大小");

    private JMenuItem windowNotReSizeItem = new JMenuItem("不允许调整窗口大小");

    private JMenuItem windowDeSizeItem = new JMenuItem("默认窗口大小");

    private HomeView() {
    }

    /**
     * 单例
     */
    @Getter
    private static HomeView homeView = new HomeView();

    public static HomeView getHomeView() {
        return homeView;
    }
}
