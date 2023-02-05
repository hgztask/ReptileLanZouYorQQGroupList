package com.MultithreadCrawling.picturesAndText.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.MultithreadCrawling.picturesAndText.data.DataLanZouY;
import com.MultithreadCrawling.picturesAndText.data.DataParent;
import com.MultithreadCrawling.picturesAndText.data.LanZouYInfo;
import com.MultithreadCrawling.picturesAndText.data.ruleFace.LanZouYRule;
import com.MultithreadCrawling.picturesAndText.impi.CrawlingRuleFace;
import com.MultithreadCrawling.picturesAndText.impi.InitializationFace;
import com.MultithreadCrawling.picturesAndText.view.JFileChooserDialog;
import com.MultithreadCrawling.picturesAndText.view.panel.LanZouYJPanel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 蓝奏云逻辑层
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 15:46
 */
public class LanZouYJPanelEss  extends ParentPanelEss implements CrawlingRuleFace<ArrayList<LanZouYInfo>>,InitializationFace {

    /**
     * 蓝奏云面板view
     */
    @Setter
    private LanZouYJPanel lanZouYPanel;
    /**
     * 蓝奏云属性层
     */
    @Setter
    private DataLanZouY dataLanZouY;


    /**
     * 构造器
     * @param dataParent 面板父级的属性成员
     * @param lanZouYPanel 蓝奏云面板 核心是输入蓝奏云特需要的组件控件
     * @param dataLanZouY 蓝奏云属性层
     */
    public LanZouYJPanelEss(DataParent dataParent, LanZouYJPanel lanZouYPanel, DataLanZouY dataLanZouY) {
        super(lanZouYPanel, dataParent);
        this.lanZouYPanel = lanZouYPanel;
        this.dataLanZouY = dataLanZouY;
    }







    @Override
    public void initialization(){
        super.initialization();
        //顶部面板与底部面板
        JPanel topJPanel = lanZouYPanel.getTopJPanel();
        topJPanel.setLayout(new GridLayout(6,1));
        JPanel bottJPanel = lanZouYPanel.getBottJPanel();
        bottJPanel.setLayout(new GridLayout(4, 3));


        //每一个对象一个面板
        JPanel modelJPanel = lanZouYPanel.getModelJPanel();

        JCheckBoxMenuItem whileCHicjMenuItem = lanZouYPanel.getWhileCHicjMenuItem();

        //设置外部数据按钮
        JButton fIleDataJButton = lanZouYPanel.getFIleDataJButton();
        //总个数
        JLabel countIndexJLabel = lanZouYPanel.getCountIndexJLabel();

        //外部个数
        JLabel externalCountIndexJLabel = lanZouYPanel.getExternalCountIndexJLabel();

        //显示url选择文本
        JLabel urlJLabel = lanZouYPanel.getUrlJLabel();

        //显示外部数据路径标签文本
        JLabel pathFileJLabel = lanZouYPanel.getPathFileJLabel();
        //新的个数标签文本
        JLabel newCountIndexJLabel = lanZouYPanel.getNewCountIndexJLabel();


        //模拟点击下一页
        JButton startPage = lanZouYPanel.getStartPageJButton();

        //添加进顶部面板
        topJPanel.add(countIndexJLabel);
        topJPanel.add(externalCountIndexJLabel);
        topJPanel.add(newCountIndexJLabel);

        //添加控件
        //添加父组件
        bottJPanel.add(lanZouYPanel.getOpenWebJbutton());
        bottJPanel.add(lanZouYPanel.getLoadUrlJbutton());
        bottJPanel.add(lanZouYPanel.getPrintWebJButton());
        bottJPanel.add(lanZouYPanel.getPrintListJButton());
        bottJPanel.add(lanZouYPanel.getWriteListJbutton());

        //添加蓝奏云特需要的组件
        bottJPanel.add(lanZouYPanel.getMonitorJRadioButton());
        bottJPanel.add(lanZouYPanel.getPrintNewListJButton());
        bottJPanel.add(lanZouYPanel.getAddListJButton());
        bottJPanel.add(lanZouYPanel.getStartPageJButton());
        bottJPanel.add(lanZouYPanel.getPrintOldListJButton());
        bottJPanel.add(lanZouYPanel.getPrintStraightUrlJButton());
        bottJPanel.add(lanZouYPanel.getFilesInFolderJButton());

        //添加进右击菜单
        lanZouYPanel.getStartPagejPopupMenu().add(whileCHicjMenuItem);





        JMenuItem editUrlItem = lanZouYPanel.getEditUrlItem();

        urlJLabel.setBounds(0, 0, 1000, 20);
        topJPanel.add(urlJLabel);
        topJPanel.add(fIleDataJButton);
        topJPanel.add(pathFileJLabel);

        pathFileJLabel.setBounds(fIleDataJButton.getWidth(), fIleDataJButton.getY(), 1000, 20);

        modelJPanel.add(topJPanel);
        modelJPanel.add(bottJPanel);

        JPopupMenu monitorJRadiojPopupMenu = lanZouYPanel.getMonitorJRadiojPopupMenu();
        monitorJRadiojPopupMenu.add(lanZouYPanel.getAppendWebItem());
        monitorJRadiojPopupMenu.add(editUrlItem);


        super.getDataParent().setUrl("https://www.lanzoui.com/b93256");


        JRadioButton monitorJRadioButton = lanZouYPanel.getMonitorJRadioButton();
        //右击监听网页列表变化
        monitorJRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {  //监听鼠标右键
                    monitorJRadiojPopupMenu.show(monitorJRadioButton, e.getX(), e.getY());
                    //注意，第一个参数必须是弹出菜单所加入的窗口或是窗口中的任意一个组件
                    //后面的坐标就是相对于这个窗口或是组件的原点（左上角）这个位置进行弹出
                    //我们这里写的就是相对于当前窗口的左上角，鼠标点击位置的x、y位置弹出窗口
                }
            }
        });

        /**
         * 鼠标监听器
         * 监听右击url文本事件
         */
        urlJLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {  //监听鼠标右键
                    lanZouYPanel.getUrlJLabeljPopupMenu().show(modelJPanel, e.getX(), e.getY());   //要展示弹出菜单，我们只需要调用show方法即可
                    //注意，第一个参数必须是弹出菜单所加入的窗口或是窗口中的任意一个组件
                    //后面的坐标就是相对于这个窗口或是组件的原点（左上角）这个位置进行弹出
                    //我们这里写的就是相对于当前窗口的左上角，鼠标点击位置的x、y位置弹出窗口
                }
            }
        });

        //编辑加载的url
        editUrlItem.addActionListener(e -> {
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(super.getDataParent().getEdgeDriver()))) {
                return;
            }
            DataParent dataParent = super.getDataParent();
            if (!dataParent.isBoolUrl()) {
                JOptionPane.showMessageDialog(modelJPanel, "请先加载url！");
                return;
            }
            @NonNull
            String editContentUrl = JOptionPane.showInputDialog("请输入要加载的url");
            if (editContentUrl == null) {
                return;
            } else if (!(editContentUrl.contains("http"))) {
                JOptionPane.showMessageDialog(modelJPanel, "格式不正确!");
                return;
            }
            dataParent.setUrl(editContentUrl);
            urlJLabel.setText(editContentUrl);
        });

        //打印文件直链
        lanZouYPanel.getPrintStraightUrlJButton().addActionListener(e -> {
            EdgeDriver edgeDriver = super.getDataParent().getEdgeDriver();
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(edgeDriver))) {
                return;
            }
            System.out.println("该功能暂时未完成!");
//            Document parse = Jsoup.parse(edgeDriver.getPageSource());
//            Element n_downlink = parse.getElementsByClass("n_downlink").get(0);
//            System.out.println(n_downlink);
        });


        //获取文件夹内文件
        lanZouYPanel.getFilesInFolderJButton().addActionListener(e -> {
            EdgeDriver edgeDriver = super.getDataParent().getEdgeDriver();
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(edgeDriver))) {
                return;
            }
            Document parse = Jsoup.parse(edgeDriver.getPageSource());
            //设置href的绝对路径
            parse.setBaseUri("https://www.lanzoui.com");
            //判断列表中有没有文件夹,有就返回出来,没有就返回长度为0的元素集
            Elements folder = LanZouYRule.isFolder(parse);
            if (folder.isEmpty()) {
                JOptionPane.showMessageDialog(bottJPanel, "并未有文件夹列表元素");
                return;
            }
            //遍历每个文件夹信息
            for (Element body : folder) {
                //文件夹名
                String filename = body.getElementsByClass("filename").get(0).ownText();
                //文件夹对应url
                String href = body.getElementsByTag("a").get(0).attr("abs:href");
                edgeDriver.get(href);
                while (analogClick()) {
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                ArrayList<LanZouYInfo> lanZouYInfoArrayList = detectUpdateWeb();
                if (appendList(lanZouYInfoArrayList)) {
                    System.out.println(filename + "文件夹内资源 追加完成");
                    System.out.println(lanZouYInfoArrayList);
                } else {
                    System.out.println(filename + "文件夹内资源 添加失败,可能是数据源已经存储过该数据!!!!");
                }
            }
            JOptionPane.showMessageDialog(bottJPanel, "获取文件夹下的文件执行完成!！");
        });


        /**
         * 按钮监听器
         * 模拟点击一次网页位置
         */
        startPage.addActionListener(e -> {
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(super.getDataParent().getEdgeDriver()))) {
                return;
            }
            analogClick();
        });

        /**
         * 鼠标监听器
         * 功能:右键弹出菜单
         */
        startPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {  //监听鼠标右键
                    lanZouYPanel.getStartPagejPopupMenu().show(startPage, e.getX(), e.getY());   //要展示弹出菜单，我们只需要调用show方法即可
                    //注意，第一个参数必须是弹出菜单所加入的窗口或是窗口中的任意一个组件
                    //后面的坐标就是相对于这个窗口或是组件的原点（左上角）这个位置进行弹出
                    //我们这里写的就是相对于当前窗口的左上角，鼠标点击位置的x、y位置弹出窗口
                }
            }
        });


        /**
         * 按钮监听器,单击
         * 触发循环点击模拟下一页和秩序滚动底部
         */
        whileCHicjMenuItem.addActionListener(e -> {
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(super.getDataParent().getEdgeDriver()))) {
                whileCHicjMenuItem.setState(false);
                return;
            }
            ExecutorService threadPool = Executors.newFixedThreadPool(2);
            //单独开一条线程执行模拟按键
            threadPool.execute(() -> {
                while (whileCHicjMenuItem.getState()) {
                    if (!(analogClick())) {
                        whileCHicjMenuItem.setState(false);
                        break;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        whileCHicjMenuItem.setState(false);
                        throw new RuntimeException(ex);
                    }
                }
            });
            //单独执行持续滚动底部
            threadPool.execute(() -> {
                while (whileCHicjMenuItem.getState()) {
                    super.getDataParent().getEdgeDriver().executeScript("window.scrollTo(0,document.body.scrollHeight)");
                }
                JOptionPane.showMessageDialog(whileCHicjMenuItem, "已经结束持续滚动底部");
            });
        });


        /**
         *设置外部数据
         */
        lanZouYPanel.getFIleDataJButton().addActionListener(e -> {
            JFileChooserDialog jFileChooserDialog = new JFileChooserDialog(bottJPanel);
            jFileChooserDialog.setPath("E:\\");
            jFileChooserDialog.show();
            File file = jFileChooserDialog.getFile();
            if (file == null) {
                return;
            }
            //设置file对象
            dataLanZouY.setOldFile(file);
            ;
            lanZouYPanel.getPathFileJLabel().setText("外部路径:" + file);
            //将外部数据设置为程序对应面板所需的json对象
            dataLanZouY.setDataList(new LinkedHashSet<>(getFIlePathList()));
            dataLanZouY.setJsonArray(JSONUtil.parseArray(getFIlePathList()));
            JOptionPane.showMessageDialog(bottJPanel, "已设置外部数据到程序对应面板!！");
        });


        //监听网页列表变化
        monitorJRadioButton.addActionListener(e -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                if (!(super.isPrintBool())) {
                    monitorJRadioButton.setSelected(false);
                    return;
                }

                LinkedHashSet<LanZouYInfo> dataList = dataLanZouY.getDataList();
                if (dataList == null) {
                    int value = JOptionPane.showConfirmDialog(modelJPanel, "你并未设置外部数据,是否建立一个空的数据？");
                    //取消是2 否是1 确定0 返回值就是用户的选择结果，也是预置好的
                    if (value == JOptionPane.OK_OPTION) {
                        dataList = new LinkedHashSet<>();
                    } else {
                        monitorJRadioButton.setSelected(false);
                        return;
                    }
                }
                while (monitorJRadioButton.isSelected()) {
                    System.out.println("开启功能!" + DateUtil.now());
                    ArrayList<LanZouYInfo> detectUpdateWeb = detectUpdateWeb();
                    if (!(detectUpdateWeb.isEmpty())) {
                        //计算集合的单差集，即只返回【集合1】中有，但是【集合2】中没有的元素
                        //例如：
                        //subtract([1,2,3,4],[2,3,4,5]) -》 [1]
                        ArrayList<LanZouYInfo> newList;
                        newList = new ArrayList<>(CollUtil.subtract(detectUpdateWeb, dataList));
                        super.getDataParent().setBoolPrintList(true);
                        super.printList("新数据列表", newList);
                        System.out.printf("长度=%s,更新时间=%s%n", newList.size(), DateUtil.now());
                        //如果勾选了追加进列表则执行追加列表
                        if (lanZouYPanel.getAppendWebItem().getState()) {
                            appendList(newList);
                        }


                    }
                    //刷新网页
                    super.getDataParent().getEdgeDriver().navigate().refresh();
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                System.out.println("结束功能!" + DateUtil.now());
            });
        });

        //打印网页中所有列表信息
        lanZouYPanel.getPrintListJButton().addActionListener(e -> {
            if (!(super.isPrintBool())) {
                return;
            }
            if (super.getDataParent().isBoolPrintList()) {
                JOptionPane.showMessageDialog(modelJPanel, "请等待数据加载到列表窗口！");
                return;
            }
            ExecutorService threadPool = Executors.newCachedThreadPool();
            threadPool.execute(() -> {
                //读取网页中的列表元素
                ArrayList<LanZouYInfo> webList = crawlingRule(super.getDataParent().getEdgeDriver().getPageSource());
                if (webList.isEmpty()) {
                    JOptionPane.showMessageDialog(bottJPanel, "网页并未有指定列表元素信息！");
                    return;
                }
                super.printList("网页中所有的列表信息", webList);
            });
        });


        /***
         * 打印网页中新的列表信息
         */
        lanZouYPanel.getPrintNewListJButton().addActionListener(e -> {
            if (!(super.isPrintBool())) {
                return;
            }
            LinkedHashSet<LanZouYInfo> dataList = dataLanZouY.getDataList();
            if (dataList == null) {
                int value = JOptionPane.showConfirmDialog(bottJPanel, "你并未设置外部数据,是否建立一个空的数据？");
                //取消是2 否是1 确定0 返回值就是用户的选择结果，也是预置好的
                if (value == JOptionPane.OK_OPTION) {
                    dataList = new LinkedHashSet<>();
                } else {
                    return;
                }
            }
            ExecutorService threadPool = Executors.newCachedThreadPool();
            threadPool.execute(() -> {
                //检测网页元素是否有更新
                ArrayList<LanZouYInfo> detectUpdateWeb = detectUpdateWeb();
                if (detectUpdateWeb.isEmpty()) {
                    JOptionPane.showMessageDialog(bottJPanel, "没找到新数据,页面上的数据已经拥有了!!!!");
                    return;
                }
                //计算集合的单差集，即只返回【集合1】中有，但是【集合2】中没有的元素
                //例如：
                //subtract([1,2,3,4],[2,3,4,5]) -》 [1]
                ArrayList<LanZouYInfo> newList = new ArrayList<>(CollUtil.subtract(detectUpdateWeb, dataLanZouY.getDataList()));
                dataLanZouY.setNewList(newList);
                super.getDataParent().setBoolPrintList(true);
                super.printList("新数据列表", newList);
            });
        });


        //追加新列表信息
        lanZouYPanel.getAddListJButton().addActionListener(e -> {
            if (!(super.isPrintBool())) {
                return;
            }
            LinkedHashSet<LanZouYInfo> dataList = dataLanZouY.getDataList();
            if (dataList == null) {
                int value = JOptionPane.showConfirmDialog(bottJPanel, "你并未设置外部数据,是否建立一个空的数据？");
                //取消是2 否是1 确定0 返回值就是用户的选择结果，也是预置好的
                if (value == JOptionPane.OK_OPTION) {
                    dataLanZouY.setDataList(new LinkedHashSet<>());
                } else {
                    return;
                }
            }
            ExecutorService threadPool = Executors.newCachedThreadPool();
            threadPool.execute(() -> {
                if (appendList(detectUpdateWeb())) {
                    JOptionPane.showMessageDialog(bottJPanel, "追加完成!!!!");
                } else {
                    JOptionPane.showMessageDialog(bottJPanel, "添加失败,可能是数据源已经存储过该数据!!!!");
                }
            });
        });


        //监听器-打印外部数据
        lanZouYPanel.getPrintOldListJButton().addActionListener(e -> {
            LinkedHashSet<LanZouYInfo> dataList = dataLanZouY.getDataList();
            if (dataList == null) {
                JOptionPane.showMessageDialog(bottJPanel, "外部数据为Nul,可能是没有添加!!!!");
                return;
            }
            super.printList("外部数据列表", dataList);
        });


        //保存集合对象到本地文件
        lanZouYPanel.getWriteListJbutton().addActionListener(e -> {
            JSONArray jsonArray = dataLanZouY.getJsonArray();
            if (jsonArray == null || jsonArray.isEmpty()) {
                JOptionPane.showMessageDialog(bottJPanel, "列表为Nul或者长度为0!!!!");
                return;
            }
            FileUtil.writeUtf8String(jsonArray.toString(), String.format("E:\\%s条蓝奏云资源.json", jsonArray.size()));
            JOptionPane.showMessageDialog(bottJPanel, "保存成功! ");
        });


    }


    /**
     * 模拟点击网页位置
     *
     * @return 布尔值, 成功执行true, 反之false
     */
    private boolean analogClick() {
        if (!(super.isWebEdgeOpenUrl())) {
            return false;
        }
        try {
            EdgeDriver edgeDriver = super.getDataParent().getEdgeDriver();
            WebElement element = edgeDriver.findElement(By.id("filemore"));
            element.click();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 检测网页元素是否有更新
     *
     * @return 网页更新内容包含原先网页内容
     */
    private @NonNull ArrayList<LanZouYInfo> detectUpdateWeb() {
        //读取网页中的列表元素
        ArrayList<LanZouYInfo> webList = crawlingRule(super.getDataParent().getEdgeDriver().getPageSource());
        //该方法描述,调用方是否包含指定对象集合中所有的元素
        //可以理解为右边是不是左边的子集合
        if (dataLanZouY.getDataList().containsAll(webList)) {
            return DataLanZouY.getARRAY_LIST_NULL();
        }
        return webList;
    }


    /**
     * 依次执行规则,当第一个规则失效时切换到第二个规则,依次类推
     *
     * @param html 网页html源码
     * @return 列表数据
     */
    @Override
    public ArrayList<LanZouYInfo> crawlingRule(String html) {
        ArrayList<LanZouYInfo> lanZouYInfos;
        while (true) {
            try {
                lanZouYInfos = dataLanZouY.getRuleFace().crawlingRule(html);
                break;
            } catch (Exception e) {
                if (dataLanZouY.getCrawlingIndex() == 1) {
                    dataLanZouY.setCrawlingIndex(2);
                    dataLanZouY.setRuleFace(LanZouYRule.CrawRule2.getCrawRule());
                    System.out.println("规则实现类1不符合当前网站规则,已切换成规则实现类2");
                } else if (dataLanZouY.getCrawlingIndex() == 2) {
                    dataLanZouY.setRuleFace(LanZouYRule.CrawRule3.getCrawRule());
                    dataLanZouY.setCrawlingIndex(3);
                    System.out.println("规则实现类2不符合当前网站规则,已切换成规则实现类3");
                } else {
                    dataLanZouY.setCrawlingIndex(1);
                    dataLanZouY.setRuleFace(LanZouYRule.CrawRule1.getCrawRule());
                    System.out.println("规则实现类不符合当前网站规则,已切换成规则实现类1");
                }
            }
        }
        return lanZouYInfos;
    }


    /**
     * 追加列表并显示给主窗口
     *
     * @return
     */
    private boolean appendList(@NonNull ArrayList<LanZouYInfo> list) {
        LinkedHashSet<LanZouYInfo> dataList = dataLanZouY.getDataList();
        if (dataList.addAll(list)) {
            dataLanZouY.setJsonArray(JSONUtil.parseArray(dataList));
            dataLanZouY.setNewCountIndex(dataLanZouY.getNewCountIndex() + list.size());
            lanZouYPanel.getNewCountIndexJLabel().setText("新增个数=" + dataLanZouY.getNewCountIndex());
            lanZouYPanel.getCountIndexJLabel().setText("总个数=" + dataList.size());
            return true;
        }
        return false;
    }


    /**
     * 获取old的旧数据集合
     *
     * @return
     */
    public ArrayList<LanZouYInfo> getFIlePathList() {
        File oldFile = dataLanZouY.getOldFile();
        if (oldFile == null) {
            return DataLanZouY.getARRAY_LIST_NULL();
        }
        JSONArray jsonArray = JSONUtil.readJSONArray(oldFile, StandardCharsets.UTF_8);
        lanZouYPanel.getExternalCountIndexJLabel().setText("外部个数:" + jsonArray.size());
        ArrayList<LanZouYInfo> tempList = new ArrayList<>();
        for (Object obj : jsonArray) {
            tempList.add(BeanUtil.toBean(obj, LanZouYInfo.class));
        }
        return tempList;
    }


}
