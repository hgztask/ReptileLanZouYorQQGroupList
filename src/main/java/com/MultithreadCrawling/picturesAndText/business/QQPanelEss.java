package com.MultithreadCrawling.picturesAndText.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.MultithreadCrawling.picturesAndText.data.DataParent;
import com.MultithreadCrawling.picturesAndText.data.QqInformation;
import com.MultithreadCrawling.picturesAndText.impi.InitializationFace;
import com.MultithreadCrawling.picturesAndText.view.JFileChooserDialog;
import com.MultithreadCrawling.picturesAndText.view.QQPanel;
import com.MultithreadCrawling.picturesAndText.view.panel.ParentPanel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.edge.EdgeDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;

/**
 * QQ面板逻辑层
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 23:07
 */
public class QQPanelEss extends ParentPanelEss implements InitializationFace {

    /**
     * QQ面板层
     */
    private QQPanel qqPanel;

    /**
     * 数据层
     */
    private JSONArray jsonArray;

    /**
     * 构造器
     *
     * @param parentPanel 该面板是所有面板共有的控件 或者是其继承的子类
     * @param dataParent  面板父级的属性成员
     * @param qqPanel     QQ面板层
     */
    public QQPanelEss(ParentPanel parentPanel, DataParent dataParent, QQPanel qqPanel) {
        super(parentPanel, dataParent);
        this.qqPanel = qqPanel;
    }


    public void initialization() {
        super.initialization();
        JPanel topJPanel = qqPanel.getTopJPanel();
        JPanel bottJPanel = qqPanel.getBottJPanel();
        topJPanel.setLayout(new GridLayout(2, 1));
        bottJPanel.setLayout(new GridLayout(4, 2));


        topJPanel.add(qqPanel.getJLabelOnePath());
        topJPanel.add(qqPanel.getJLabelTwoPath());

        JButton printListJButton = qqPanel.getPrintListJButton();
        JButton writeListJbutton = qqPanel.getWriteListJbutton();
        //添加控件
        bottJPanel.add(qqPanel.getOpenWebJbutton());
        bottJPanel.add(qqPanel.getLoadUrlJbutton());
        bottJPanel.add(qqPanel.getPrintWebJButton());
        bottJPanel.add(printListJButton);
        bottJPanel.add(writeListJbutton);
        bottJPanel.add(qqPanel.getEqualsArrJButton());

        qqPanel.getModelJPanel().add(topJPanel);
        qqPanel.getModelJPanel().add(bottJPanel);


        //设置要加载的网页
        // super.getDataParent().setUrl("https://qun.qq.com/member.html#gid=Q群群号");
        super.getDataParent().setUrl("https://qun.qq.com/member.html#gid=760849278");

        /**
         * 该方法适用于https://qun.qq.com/member.html#gid=Q群群号
         * 获取群成员信息
         */
        printListJButton.addActionListener(e -> {
            EdgeDriver edgeDriver = super.getDataParent().getEdgeDriver();
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(edgeDriver))) {
                return;
            }
            Executors.newSingleThreadExecutor().execute(() -> {
                Document parse = Jsoup.parse(edgeDriver.getPageSource());
                Elements selectXpath = parse.selectXpath("//table/tbody[@class='list']/tr[@class]");
                //列表资源
                ArrayList<QqInformation> qqInformationArrayList = new ArrayList<>();
                for (Element element : selectXpath) {
                    //QQ号昵称
                    String qqName = element.getElementsByClass("td-user-nick").get(0).text();
                    //qq群昵称
                    String qqQunName = element.getElementsByClass("td-card").get(0).select("span[class]").get(0).text();
                    //QQ号
                    String qqIndex = element.getElementsByTag("td").get(4).text();
                    //性别
                    String gender = element.getElementsByTag("td").get(5).text();
                    //Q龄
                    String qAge = element.getElementsByTag("td").get(6).text();
                    //入群时间
                    String groupingTime = element.getElementsByTag("td").get(7).text();
                    //等级
                    String grade = element.getElementsByTag("td").get(8).text();
                    //最后发言
                    String finalStatement = element.getElementsByTag("td").get(9).text();
                    qqInformationArrayList.add(new QqInformation(qqName, qqQunName, qqIndex, gender, qAge, groupingTime, grade, finalStatement));
                }
                jsonArray = new JSONArray();
                for (QqInformation qqInformation : qqInformationArrayList) {
                    jsonArray.add(JSONUtil.parseObj(qqInformation.toString()));
                }
                printList("QQ列表信息", jsonArray);
            });
        });

        /**
         * 写入QQ列表按钮监听事件
         */
        writeListJbutton.addActionListener(e -> {
            if (jsonArray == null || jsonArray.isEmpty()) {
                JOptionPane.showMessageDialog(bottJPanel, "列表为Nul或者长度为0!!!!");
                return;
            }
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(super.getDataParent().getEdgeDriver()))) {
                return;
            }
            String inputStr = JOptionPane.showInputDialog(writeListJbutton, "请输入你要保存的文件名(不包含文件后缀格式)");
            if (inputStr == null) {
                return;
            }
            FileUtil.writeUtf8String(jsonArray.toString(), "E:\\" + inputStr + ".json");
            JOptionPane.showMessageDialog(writeListJbutton, "已保存文件=" + inputStr);
        });


        //比较两个数组内相同的成员
        JButton equalsArrJButton = qqPanel.getEqualsArrJButton();
        equalsArrJButton.addActionListener(e -> {
            File oneFIle = qqPanel.getOneFIle();
            File twoFIle = qqPanel.getTwoFIle();
            if (oneFIle == null || twoFIle == null) {
                JOptionPane.showMessageDialog(equalsArrJButton, "请先设置两个数组对应的file文件对象");
                return;
            }
            Charset utf8 = StandardCharsets.UTF_8;
            equalsQQ(JSONUtil.readJSONArray(oneFIle, utf8), JSONUtil.readJSONArray(twoFIle, utf8));
        });


        /**
         * 右击路径一设置文件路径
         */
        JLabel jLabelOnePath = qqPanel.getJLabelOnePath();
        jLabelOnePath.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {  //监听鼠标右键
                    int value = JOptionPane.showConfirmDialog(jLabelOnePath, "是要指定路径吗？");
                    if (!(value == JOptionPane.OK_OPTION)) {
                        return;
                    }
                    JFileChooserDialog jFileChooserDialog = new JFileChooserDialog(jLabelOnePath, "选择文件");
                    jFileChooserDialog.show();
                    File file = jFileChooserDialog.getFile();
                    if (file == null) {
                        return;
                    }
                    if (!(file.toString().endsWith(".json"))) {
                        JOptionPane.showMessageDialog(jLabelOnePath, "请选择json的相关文件");
                        return;
                    }
                    qqPanel.setOneFIle(file);
                    JOptionPane.showMessageDialog(jLabelOnePath, "您已设置路径1的file对象");
                }
            }
        });


        /**
         * 右击路径二设置文件路径
         */
        JLabel jLabelTwoPath = qqPanel.getJLabelTwoPath();
        jLabelTwoPath.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {  //监听鼠标右键
                    int value = JOptionPane.showConfirmDialog(jLabelOnePath, "是要指定路径吗？");
                    if (!(value == JOptionPane.OK_OPTION)) {
                        return;
                    }
                    JFileChooserDialog jFileChooserDialog = new JFileChooserDialog(jLabelOnePath, "选择文件");
                    jFileChooserDialog.show();
                    File file = jFileChooserDialog.getFile();
                    if (file == null) {
                        return;
                    }
                    if (!(file.toString().endsWith(".json"))) {
                        JOptionPane.showMessageDialog(jLabelOnePath, "请选择json的相关文件");
                        return;
                    }
                    qqPanel.setTwoFIle(file);
                    JOptionPane.showMessageDialog(jLabelOnePath, "您已设置路径2的file对象");
                }
            }
        });


    }


    /**
     * 需要提供2个群信息的json信息
     * 该方法会过滤两个群都同时存在的人员
     *
     * @param jsonArray1 人数最多的一组
     * @param jsonArray2 人数最少的一组
     */
    public void equalsQQ(JSONArray jsonArray1, JSONArray jsonArray2) {
        Collection<Object> intersection = CollUtil.intersection(jsonArray1, jsonArray2);
        super.printList("相同的人员", intersection);
    }


}
