package com.MultithreadCrawling.picturesAndText.data;

import cn.hutool.json.JSONArray;
import com.MultithreadCrawling.picturesAndText.data.ruleFace.LanZouYRule;
import com.MultithreadCrawling.picturesAndText.impi.CrawlingRuleFace;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * 蓝奏云属性层面
 *
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 15:43
 */
@Data
public class DataLanZouY {

    /**
     * 外部数据FIle对象
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private File oldFile;

    /**
     * 列表资源转成json之后的产物
     * 这个根据模块选择,不是所有模块都需要该功能
     */
    private JSONArray jsonArray;


    /**
     * 爬取规则的接口,传入对应实现类,执行对应规则
     */
    private CrawlingRuleFace<ArrayList<LanZouYInfo>> ruleFace = LanZouYRule.CrawRule1.getCrawRule();


    /**
     * 空列表
     */
    @Getter
    private static final ArrayList<LanZouYInfo> ARRAY_LIST_NULL = new ArrayList(0);


    /**
     * 空链式set表
     */
    @Getter
    private static final LinkedHashSet<String> LINKED_HASH_SET_NULL = new LinkedHashSet();


    /**
     * 信号灯量,用于判断当前使用是那个规则实现类
     */
    private int crawlingIndex = 1;

    /**
     * 读取外部的数据数组
     */
    private LinkedHashSet<LanZouYInfo> dataList;

    /**
     * 新数据集合
     */
    private ArrayList<LanZouYInfo> newList;


    //本轮新增个数资源
    private int newCountIndex = 0;


}
