package com.MultithreadCrawling.picturesAndText.business;

import com.MultithreadCrawling.picturesAndText.data.DataLanZouY;
import com.MultithreadCrawling.picturesAndText.data.DataParent;
import com.MultithreadCrawling.picturesAndText.view.HomeView;
import com.MultithreadCrawling.picturesAndText.view.QQPanel;
import com.MultithreadCrawling.picturesAndText.view.panel.LanZouYJPanel;
import com.MultithreadCrawling.picturesAndText.view.panel.ParentPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗口的逻辑层
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 23:24
 */
public class HomeEss {

    /**
     * 单例窗口view对象
     */
    private static final HomeView HOME_VIEW = HomeView.getHomeView();

    /**
     * 窗体默认大小
     */
    private Dimension dimensionDef = new Dimension(560, 451);


    /**
     * 单例窗口控制层对象
     */
    private static final HomeEss homeEss = new HomeEss();

    private HomeEss() {
    }

    public static HomeEss getInstance() {
        return homeEss;
    }

    {
        JFrame jFrame = HOME_VIEW.getJFrame();

        JMenu menuWindow = HOME_VIEW.getMenuWindow();

        JMenuItem windowTopItem = HOME_VIEW.getWindowTopItem();

        JMenuItem windowNotTopItem = HOME_VIEW.getWindowNotTopItem();

        JMenuItem windowPrintSizeItem = HOME_VIEW.getWindowPrintSizeItem();

        JMenuItem windowReSizeItem = HOME_VIEW.getWindowReSizeItem();

        JMenuItem windowNotReSizeItem = HOME_VIEW.getWindowNotReSizeItem();

        JMenuItem windowDeSizeItem = HOME_VIEW.getWindowDeSizeItem();

        //菜单栏
        JMenuBar menuBar = HOME_VIEW.getMenuBar();

        menuWindow.add(windowTopItem);
        menuWindow.add(windowNotTopItem);
        menuWindow.add(windowPrintSizeItem);
        menuWindow.add(windowReSizeItem);
        menuWindow.add(windowNotReSizeItem);
        menuWindow.add(windowDeSizeItem);
        menuBar.add(menuWindow);
        jFrame.setJMenuBar(menuBar);

        JTabbedPane jTabbedPane = HOME_VIEW.getJTabbedPane();

        //添加多面板进去窗口
        jFrame.add(jTabbedPane);
        jFrame.setSize(dimensionDef);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setAlwaysOnTop(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //蓝奏云面板
        LanZouYJPanel lanZouYPanel = new LanZouYJPanel();
        //qq面板
        QQPanel qqPanel = new QQPanel();

        //添加蓝奏云面板
        LanZouYJPanelEss lanZouYJPanelEss = new LanZouYJPanelEss(lanZouYPanel, new DataParent(), lanZouYPanel, new DataLanZouY());
        //添加QQ面板
        QQPanelEss qqPanelEss = new QQPanelEss(qqPanel, new DataParent(), qqPanel);
        lanZouYJPanelEss.initialization();
        qqPanelEss.initialization();

        jTabbedPane.add("蓝奏云面板", lanZouYPanel.getModelJPanel());
        jTabbedPane.add("QQ面板", qqPanel.getModelJPanel());

        //jTabbedPane.add("蓝奏云面板", LanZhouYunJPanel.getLanZhouYunPanel().getModelJPanel());
        //添加QQ面板
        //jTabbedPane.add("QQ群面板", QQPanel.getQQ_PANEL().getModelJPanel());

        windowTopItem.addActionListener(e -> jFrame.setAlwaysOnTop(true));
        windowNotTopItem.addActionListener(e -> jFrame.setAlwaysOnTop(false));
        windowPrintSizeItem.addActionListener(e -> JOptionPane.showMessageDialog(jFrame, jFrame.getSize().toString()));
        windowReSizeItem.addActionListener(e -> jFrame.setResizable(true));
        windowNotReSizeItem.addActionListener(e -> jFrame.setResizable(false));
        windowDeSizeItem.addActionListener(e -> jFrame.setSize(dimensionDef));
    }


    /**
     * 显示窗口
     */
    public void show() {
        HOME_VIEW.getJFrame().setVisible(true);
    }


}
