package com.MultithreadCrawling.picturesAndText.business;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.MultithreadCrawling.picturesAndText.data.DataParent;
import com.MultithreadCrawling.picturesAndText.impi.InitializationFace;
import com.MultithreadCrawling.picturesAndText.view.TextEdtorView;
import com.MultithreadCrawling.picturesAndText.view.panel.ParentPanel;
import lombok.Getter;
import lombok.NonNull;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 父级面板逻辑层
 * 有显示需求调用父级面板功能即可
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 15:10
 */
public  class ParentPanelEss implements InitializationFace {

    /**
     * 父级面板组件
     */
    @Getter
    private final ParentPanel parentPanel;

    /**
     * 父级属性
     */
    @Getter
    private final DataParent dataParent;

    public ParentPanelEss(ParentPanel parentPanel, DataParent dataParent) {
        this.parentPanel = parentPanel;
        this.dataParent = dataParent;
    }

    /**
     * 初始化信息
     */
    @Override
    public  void initialization(){

        //设置打开浏览器右击菜单项目
        JPopupMenu webWindowjPopupMenu = parentPanel.getWebWindowjPopupMenu();
        JMenuItem webWindowsMax = parentPanel.getWebWindowsMax();
        JMenuItem webWindowsMix = parentPanel.getWebWindowsMix();
        JMenuItem webWindowsfullscreen = parentPanel.getWebWindowsfullscreen();
        JMenuItem webWindowsdef = parentPanel.getWebWindowsdef();
        JMenuItem webWindowsSize = parentPanel.getWebWindowsSize();
        JMenuItem newWebWindowsItem = parentPanel.getNewWebWindowsItem();
        JMenuItem scrollBottomItem = parentPanel.getScrollBottomItem();
        JCheckBoxMenuItem continuedScrollBottomItem = parentPanel.getContinuedScrollBottomItem();
        JCheckBoxMenuItem webAgentItem = parentPanel.getWebAgentItem();
        webWindowjPopupMenu.add(webWindowsMax);
        webWindowjPopupMenu.add(webWindowsMix);
        webWindowjPopupMenu.add(webWindowsfullscreen);
        webWindowjPopupMenu.add(webWindowsdef);
        webWindowjPopupMenu.add(webWindowsSize);
        webWindowjPopupMenu.add(newWebWindowsItem);
        webWindowjPopupMenu.add(scrollBottomItem);
        webWindowjPopupMenu.add(continuedScrollBottomItem);
        webWindowjPopupMenu.add(webAgentItem);

        //打开浏览器浏览器对象
        parentPanel.getOpenWebJbutton().addActionListener(new ActionListener() {
            private boolean temp;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!temp) {
                    temp = true;
                    Executors.newSingleThreadExecutor().execute(() -> {
                        //创建浏览器对象
                        dataParent.setEdgeDriver(new EdgeDriver());
                        dataParent.getEdgeDriver().manage().window().setSize(dataParent.getDimension());
                    });
                    return;
                }
                JOptionPane.showMessageDialog(parentPanel.getModelJPanel(), "一个面板只能打开一个浏览器对象！");
            }
        });

        //监听鼠标事件-监听器
        //右击控件弹出菜单
        parentPanel.getOpenWebJbutton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {  //监听鼠标右键
                    webWindowjPopupMenu.show(parentPanel.getOpenWebJbutton(), e.getX(), e.getY());   //要展示弹出菜单，我们只需要调用show方法即可
                    //注意，第一个参数必须是弹出菜单所加入的窗口或是窗口中的任意一个组件
                    //后面的坐标就是相对于这个窗口或是组件的原点（左上角）这个位置进行弹出
                    //我们这里写的就是相对于当前窗口的左上角，鼠标点击位置的x、y位置弹出窗口
                }
            }
        });

        //监听器
        //点击最大化web窗口
        webWindowsMax.addActionListener(e -> {
            EdgeDriver edgeDriver = dataParent.getEdgeDriver();
            if (!isWebEdgeClose(edgeDriver)) {
                return;
            }
            edgeDriver.manage().window().maximize();
        });

        //监听器
        //点击最小化web窗口
        webWindowsMix.addActionListener(e -> {
            EdgeDriver edgeDriver = dataParent.getEdgeDriver();
            if (!isWebEdgeClose(edgeDriver)) {
                return;
            }
            edgeDriver.manage().window().minimize();
        });

        //设置默认窗口大小
        webWindowsdef.addActionListener(e -> {
            EdgeDriver edgeDriver = dataParent.getEdgeDriver();
            if (!isWebEdgeClose(edgeDriver)) {
                return;
            }
            edgeDriver.manage().window().setSize(dataParent.getDimension());
        });

        //监听器
        //全屏web窗口
        webWindowsfullscreen.addActionListener(e -> {
            EdgeDriver edgeDriver = dataParent.getEdgeDriver();
            if (!isWebEdgeClose(edgeDriver)) {
                return;
            }
            edgeDriver.manage().window().fullscreen();
        });

        //获取浏览器窗口大小
        webWindowsSize.addActionListener(e -> {
            EdgeDriver edgeDriver = dataParent.getEdgeDriver();
            if (!(isWebEdgeClose(edgeDriver))) {
                return;
            }
            JOptionPane.showMessageDialog(parentPanel.getModelJPanel(), edgeDriver.manage().window().getSize().toString());
        });

        //打开新的浏览器窗口
        newWebWindowsItem.addActionListener(e -> Executors.newSingleThreadExecutor().execute(() -> {
            dataParent.setBoolUrl(false);
            dataParent.setEdgeDriver(new EdgeDriver());
            dataParent.getEdgeDriver().manage().window().setSize(dataParent.getDimension());
        }));

        //滚动页面底部
        scrollBottomItem.addActionListener(e -> dataParent.getEdgeDriver().executeScript("window.scrollTo(0,document.body.scrollHeight)"));

        /**
         *监听器
         * 持续滚动底部
         */
        continuedScrollBottomItem.addActionListener(e -> Executors.newSingleThreadExecutor().execute(() -> {
            while (continuedScrollBottomItem.getState()) {
                dataParent.getEdgeDriver().executeScript("window.scrollTo(0,document.body.scrollHeight)");
            }
            System.out.println("已经结束持续滚动底部");
        }));

        //浏览器代理开关
        webAgentItem.addActionListener(e -> {
            boolean state = webAgentItem.getState();
            if (state) {
                dataParent.setBrowserMobProxy(newProxyBrowser());
                System.out.println("开启浏览器代理");
            } else {
                dataParent.getBrowserMobProxy().stop();
                System.out.println("关闭浏览器代理");
                JOptionPane.showMessageDialog(parentPanel.getModelJPanel(), "请先加载url！");
            }
        });

        //加载网页按钮监听
        parentPanel.getLoadUrlJbutton().addActionListener(e -> {
            EdgeDriver edgeDriver1 = dataParent.getEdgeDriver();
            if (isWebEdgeClose(edgeDriver1)) {
                dataParent.setBoolUrl(true);
                Executors.newSingleThreadExecutor().execute(() -> edgeDriver1.get(dataParent.getUrl()));
            }

        });

        //打印浏览器目前访问的网站源码信息
        JButton printWebJButton = parentPanel.getPrintWebJButton();
        printWebJButton.addActionListener(new ActionListener() {
            private final TextEdtorView textEdtorView = new TextEdtorView("网页源码");
            private boolean temp;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (temp) {
                    JOptionPane.showMessageDialog(printWebJButton, "请稍等,正在加载上一步的网页源码操作！");
                    return;
                }
                EdgeDriver edgeDriver = dataParent.getEdgeDriver();
                if (!isWebEdgeClose(edgeDriver)) {
                    return;
                }
                temp = true;
                Executors.newSingleThreadExecutor().execute(() -> {
                    textEdtorView.setText(edgeDriver.getPageSource());
                    textEdtorView.show();
                    temp = false;
                });
            }
        });




    }


    /**
     * 创建代理浏览器角色
     */
    private BrowserMobProxy newProxyBrowser() {
        dataParent.setBrowserMobProxy(new BrowserMobProxyServer());
        BrowserMobProxy browserMobProxy = dataParent.getBrowserMobProxy();
        browserMobProxy.start();
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(dataParent.getBrowserMobProxy());
        //浏览器参数
        EdgeOptions options = new EdgeOptions();
        browserMobProxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        browserMobProxy.setHarCaptureTypes(CaptureType.RESPONSE_CONTENT);
        browserMobProxy.newHar();
        options.setProxy(seleniumProxy);
        options.setAcceptInsecureCerts(true);
        options.setExperimentalOption("useAutomationExtension", false);
        dataParent.setEdgeDriver(new EdgeDriver(options));
        dataParent.getEdgeDriver().manage().window().setSize(dataParent.getDimension());
        return browserMobProxy;
    }


    /**
     * 判断浏览器对象是否被关闭掉
     * 异常处理用于判断浏览器是否还在,非对象,针对于用户把浏览器x关掉的情况
     *
     * @return 浏览器是否被关闭
     */
    public boolean isWebEdgeClose(@NonNull EdgeDriver edgeDriver) {
        try {
            edgeDriver.getWindowHandle();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentPanel.getModelJPanel(), "浏览器疑似被关掉,请重新打开一个新的浏览器窗口！");
            return false;
        }
        return true;
    }


    /**
     * 判断是否有加载url
     *
     * @return 布尔值
     */
    public boolean isWebEdgeOpenUrl() {
        if (!dataParent.isBoolUrl()) {
            JOptionPane.showMessageDialog(parentPanel.getModelJPanel(), "未加载网页的url!!!");
            return false;
        }
        return true;
    }


    /**
     * 执行打印流程
     *
     * @param obj 对象
     */
    public void printList(String title, Object obj) {
        JSONArray jsonArray = JSONUtil.parseArray(obj);
        printList(title, jsonArray.size(), jsonArray.toStringPretty());
    }


    /**
     * 调用窗口打印信息
     *
     * @param title   窗口标题
     * @param size    长度
     * @param content 内容
     */
    public void printList(String title, int size, String content) {
        printList(title, String.format("长度：%s\n\n%s", size, content));
    }

    /**
     * 打印信息
     *
     * @param title   窗口标题
     * @param content 规则+结果
     */
    public void printList(String title, String content) {
        dataParent.setBoolUrl(true);
        dataParent.setBoolPrintList(true);
        TextEdtorView textEdtorView = parentPanel.getTextEdtorView();
        textEdtorView.setWindowTitle(title);
        textEdtorView.setText(content);
        textEdtorView.show();
        dataParent.setBoolPrintList(false);
    }

    /**
     * 数据校验证
     *
     * @return
     */
    public boolean isPrintBool() {
        if (!(isWebEdgeOpenUrl())) {
            return false;
        }

        if (dataParent.isBoolPrintList()) {
            JOptionPane.showMessageDialog(parentPanel.getModelJPanel(), "请等待数据加载到列表窗口！");
            return false;
        }
        return true;
    }


}
