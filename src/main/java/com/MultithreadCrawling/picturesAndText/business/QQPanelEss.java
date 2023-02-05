package com.MultithreadCrawling.picturesAndText.business;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.MultithreadCrawling.picturesAndText.data.DataParent;
import com.MultithreadCrawling.picturesAndText.data.QqInformation;
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
import java.io.File;
import java.util.ArrayList;

/**
 * QQ面板逻辑层
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 23:07
 */
public class QQPanelEss extends ParentPanelEss {

    /**
     * QQ面板层
     */
    private QQPanel qqPanel;

    /**
     * 数据层
     */
    private JSONArray jsonArray;

    public QQPanelEss(ParentPanel parentPanel, DataParent dataParent, QQPanel qqPanel) {
        super(parentPanel, dataParent);
        this.qqPanel = qqPanel;
    }

    {

        JPanel topJPanel = qqPanel.getTopJPanel();
        JPanel bottJPanel = qqPanel.getBottJPanel();
        EdgeDriver edgeDriver = super.getDataParent().getEdgeDriver();

        topJPanel.add(qqPanel.getJLabelOnePath());
        topJPanel.add(qqPanel.getJLabelTwoPath());

        //设置蓝奏云面板
        bottJPanel.setLayout(new GridLayout(3, 1));
        //设置要加载的网页
        // super.getDataParent().setUrl("https://qun.qq.com/member.html#gid=Q群群号");
        super.getDataParent().setUrl("https://qun.qq.com/member.html#gid=760849278");


        JButton printListJButton = qqPanel.getPrintListJButton();
        JButton writeListJbutton = qqPanel.getWriteListJbutton();
        //添加控件
        bottJPanel.add(qqPanel.getOpenWebJbutton());
        bottJPanel.add(qqPanel.getLoadUrlJbutton());
        bottJPanel.add(qqPanel.getPrintWebJButton());
        bottJPanel.add(qqPanel.getEqualsArray());
        bottJPanel.add(printListJButton);


        /**
         * 该方法适用于https://qun.qq.com/member.html#gid=Q群群号
         * 获取群成员信息
         */
        printListJButton.addActionListener(e -> {
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(edgeDriver))) {
                return;
            }
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
            System.out.println("===================================");
            qqInformationArrayList.forEach(System.out::println);
            System.out.println("数量=" + qqInformationArrayList.size());
            jsonArray = new JSONArray();
            System.out.println("==================转成json===================");
            for (QqInformation qqInformation : qqInformationArrayList) {
                jsonArray.add(JSONUtil.parseObj(qqInformation.toString()));
            }
            printList("QQ列表信息", jsonArray);
        });

        /**
         * 写入QQ列表按钮监听事件
         */
        writeListJbutton.addActionListener(e -> {
            if (jsonArray == null || jsonArray.isEmpty()) {
                JOptionPane.showMessageDialog(bottJPanel, "列表为Nul或者长度为0!!!!");
                return;
            }
            if (!(super.isWebEdgeOpenUrl() || super.isWebEdgeClose(edgeDriver))) {
                return;
            }
            FileUtil.writeUtf8String(jsonArray.toString(), "E:\\二群qq群成员.json");
            System.out.println("已保存至本地");
        });


        //比较两个数组内相同的成员
        qqPanel.getEqualsArray().addActionListener(e -> {
            JFileChooserDialog jFileChooserDialog = new JFileChooserDialog(bottJPanel);
            jFileChooserDialog.show();
            File file = jFileChooserDialog.getFile();
            if (file == null) {
                JOptionPane.showMessageDialog(bottJPanel, "file对象为nul！");
                return;
            }
            System.out.println(file);
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
        for (int i = 0; i < jsonArray2.size(); i++) {
            String i2 = JSONUtil.parseObj(jsonArray2.get(i)).get("QQ号", String.class);
            if ("2978778354".equals(i2) || "3426359078".equals(i2) || "2723743041".equals(i2)) {
                continue;
            }
            for (Object o : jsonArray1) {
                JSONObject jsonObj = JSONUtil.parseObj(o);
                String i1 = jsonObj.get("QQ号", String.class);
                if (i1.equals(i2)) {
                    System.out.println(i1);
                    String s = jsonObj.get("QQ群昵称", String.class);
                    String s1 = jsonObj.get("QQ昵称", String.class);
                    if (s.isEmpty()) {
                        System.out.println(String.format("QQ号=%s 昵称=%s", i1, s1));
                        continue;
                    }
                    if (s1.isEmpty()) {
                        System.out.println(String.format("QQ号=%s", i1));
                        continue;
                    }
                    System.out.println(String.format("QQ号=%s 昵称=%s 群昵称%s", i1, s1, s));

                }
            }
        }
    }


}
