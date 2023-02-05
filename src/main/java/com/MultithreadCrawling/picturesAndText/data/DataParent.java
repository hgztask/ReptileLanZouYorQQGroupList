package com.MultithreadCrawling.picturesAndText.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;

/**
 * 面板父级的属性成员
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 15:30
 */
@Data
public class DataParent {

    /**
     * 需要加载的网页
     */
    private String url;

    /**
     * 是否有加载需要加载的网页
     */
    private boolean boolUrl;


    /**
     * 是否加载完列表窗口布尔值
     */
    private boolean boolPrintList;


    /**
     * 浏览器代理
     */
    private BrowserMobProxy browserMobProxy = new BrowserMobProxyServer();


    /**
     * 默认浏览器窗口大小配置
     */
    private Dimension dimension = new Dimension(1067, 604);

    /**
     * 保存集合对象的路径
     */
    private File arrayFile;


    /**
     * 浏览器对象
     */
    private EdgeDriver edgeDriver;


    static {
        /**
         * 使用之前需要指定对应浏览器版本的驱动
         * chromedriver服务地址
         */
        System.setProperty("webdriver.edge.driver", "D:\\program\\edgedriver_win64\\msedgedriver.exe");
    }


}
