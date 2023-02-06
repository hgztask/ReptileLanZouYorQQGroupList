package com.MultithreadCrawling.picturesAndText.data.ruleFace;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.MultithreadCrawling.picturesAndText.data.DataLanZouY;
import com.MultithreadCrawling.picturesAndText.data.LanZouYInfo;
import com.MultithreadCrawling.picturesAndText.impi.CrawlingRuleFace;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 截取蓝奏云网站列表规则
 */
public class LanZouYRule {

    private static Document parse;

    /**
     * 空值
     */
    private static final Elements LISTNULL = new Elements(0);

    /**
     * 文件类型集合
     */
    private static final ArrayList<String> TYPE_LIST = ListUtil.toList("apk", "exe", "zip", "txt", "7z", "rar", "xlsx", "pdf", "docx", "doc", "xls", "dmg", "lua", "jar", "dll", "ipa", "crx", "db", "pkg", "deb", "age", "mp3", "appimage");


    /**
     * 使用网络api解析蓝奏云链接,并获取直链
     *
     * @param url 蓝奏云分享链接的地址
     * @return 返回直链 字符串
     */
    public static String getAPIDownloadLink(@NonNull String url) {
        //注意用的是自己的接口
        String document = HttpUtil.get("https://vip.mikuchase.ltd/?url=" + url);
        JSONObject jsonObject;
        try {
            jsonObject = JSONUtil.parseObj(document);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        Integer code = jsonObject.get("status", int.class);
        if (code == null || code == 0) {
            return null;
        }
        String downdLoadUrl = jsonObject.getByPath("info", String.class);
        if (downdLoadUrl == null) {
            return null;
        }
        return downdLoadUrl;
    }


    /**
     * 判断列表中有没有文件夹,有就返回出来,没有就返回长度为0的元素集
     *
     * @param parse
     * @return 元素集合
     */
    public static @NonNull Elements isFolder(@NonNull Document parse) {
        //获取列表中的文件夹
        Elements folder;
        try {
            folder = parse.getElementById("folder").select("a[class='mlink minPx-top']");
        } catch (Exception e) {
            return LISTNULL;
        }
        return folder;
    }

    /**
     * 判断列表中是否有文件,有就返回出来,没有就返回长度为0的元素集
     *
     * @param parse
     * @return
     */
    public static final @NonNull Elements isFile(@NonNull Document parse) {
        Elements select;
        try {
            select = parse.getElementById("infos").select("div[id='ready']");
        } catch (Exception e) {
            return LISTNULL;
        }
        return select;
    }

    /**
     * 处理蓝奏云链接里包含文件的,列表,返回对应文件列表,如果没有则返回一个空列表
     *
     * @param html
     * @return
     */
    private static @NonNull Elements crawFileList(@NonNull String html) {
        try {
            parse = Jsoup.parse(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //设置href的绝对路径
        parse.setBaseUri("https://www.lanzoui.com");
        Elements fileList = isFile(parse);
        if (fileList.isEmpty()) {
            return LISTNULL;
        }
        return fileList;
    }

    /**
     * 针对列表集合中的name值进行判断,符合什么类型文件就设置文件类型
     */
    private static String subName(@NonNull String name) {
        for (String type : TYPE_LIST) {
            if (name.endsWith("." + type)) {
                return type;
            }
        }
        if (name.endsWith("...")) {
            //截取字符串后面带...的字符串,并清除
            return subName(name.substring(0, name.length() - 3));
        }
        return "未知文件";
    }


    /**
     * 个别蓝奏云网站规则不一样,故分别实现对应接口
     * <p>
     * 通用规则,适用于官方主题的蓝奏云
     */
    public static class CrawRule1 implements CrawlingRuleFace<ArrayList<LanZouYInfo>> {
        private static final CrawRule1 crawRule = new CrawRule1();

        public static CrawRule1 getCrawRule() {
            return crawRule;
        }

        /**
         * 编写规则爬取页面内容
         *
         * @param html html页面源码
         * @return 数组结果
         */
        @Override
        public ArrayList<LanZouYInfo> crawlingRule(String html) {
            Elements fileList = crawFileList(html);
            if (fileList.isEmpty()) {
                return DataLanZouY.getARRAY_LIST_NULL();
            }
            /**
             * 列表资源
             * 存储数据,改数据是由页面上的
             */
            ArrayList<LanZouYInfo> newList = new ArrayList<>();
            for (Element body : fileList) {
                LanZouYInfo yInfo = new LanZouYInfo();
                //title
                String name = body.select("a[href]").get(0).text();
                yInfo.setName(name);
                //获取href的绝对路径
                yInfo.setUrl(body.select("a[href]").get(0).attr("abs:href"));
                //大小
                yInfo.setSize(body.select("#size").get(0).text());
                //时间
                yInfo.setTime(body.select("#time").get(0).text());

                yInfo.setSuffixType(subName(name));
                newList.add(yInfo);
            }
            return newList;
        }
    }

    /**
     * 特点:无时间参数,适用个别用户主题,列表元素没有时间参数的
     *
     * @author Administrator
     */
    public static class CrawRule3 implements CrawlingRuleFace<ArrayList<LanZouYInfo>> {
        private static final CrawRule3 crawRule = new CrawRule3();

        public static CrawRule3 getCrawRule() {
            return crawRule;
        }

        /**
         * 编写规则爬取页面内容
         *
         * @param html html页面源码
         * @return 数组结果
         */
        @Override
        public ArrayList<LanZouYInfo> crawlingRule(String html) {
            Elements fileList = crawFileList(html);
            if (fileList.isEmpty()) {
                return DataLanZouY.getARRAY_LIST_NULL();
            }
            /**
             * 列表资源
             * 存储数据,改数据是由页面上的
             */
            ArrayList<LanZouYInfo> newList = new ArrayList<>();
            for (Element body : fileList) {
                LanZouYInfo yInfo = new LanZouYInfo();
                //title
                //提示ownText方法,仅获取此元素拥有的（规范化）文本;不获取所有子项的合并文本
                String name = body.select("div[class='filename']").get(0).ownText();
                yInfo.setName(name);
                //获取href的绝对路径
                yInfo.setUrl(body.select("a[href]").get(0).attr("abs:href"));
                //大小
                yInfo.setSize(body.select("div[class='filesize']").get(0).text());
                subName(name);
                newList.add(yInfo);
            }
            return newList;
        }
    }

    /**
     * 个别蓝奏云网站规则不一样,故分别实现对应接口
     * ,适用个别用户主题,列表元素有时间参数的,且非官方主题
     *
     * @author Administrator
     */
    public static class CrawRule2 implements CrawlingRuleFace<ArrayList<LanZouYInfo>> {

        private static final CrawRule2 crawRule = new CrawRule2();

        public static CrawRule2 getCrawRule() {
            return crawRule;
        }

        /**
         * 编写规则爬取页面内容
         *
         * @param html html页面源码
         * @return 数组结果
         */
        @Override
        public @NonNull ArrayList<LanZouYInfo> crawlingRule(@NonNull String html) {
            Elements fileList = crawFileList(html);
            if (fileList.isEmpty()) {
                return DataLanZouY.getARRAY_LIST_NULL();
            }
            /**
             * 列表资源
             * 存储数据,改数据是由页面上的
             */
            ArrayList<LanZouYInfo> newList = new ArrayList<>();
            for (Element body : fileList) {
                LanZouYInfo yInfo = new LanZouYInfo();
                //title
                //提示ownText方法,仅获取此元素拥有的（规范化）文本;不获取所有子项的合并文本
                String name = body.select("div[class='filename']").get(0).ownText();
                yInfo.setName(name);
                yInfo.setTime(body.select("div[class='file_time']").get(0).text());
                yInfo.setSize(body.select("div[class='filesize']").get(0).getElementsByTag("div").get(2).text());
                yInfo.setUrl(body.select("a[href]").get(0).attr("abs:href"));
                subName(name);
                newList.add(yInfo);
            }
            return newList;
        }
    }


}
