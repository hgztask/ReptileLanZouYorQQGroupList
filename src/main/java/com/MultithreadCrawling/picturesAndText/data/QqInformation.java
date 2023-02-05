package com.MultithreadCrawling.picturesAndText.data;

import cn.hutool.json.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * qq群成员信息
 */
public class QqInformation {
    @Getter
    @Setter
    private String qqName;
    @Getter
    @Setter
    private String qqQunName;
    @Getter
    @Setter
    private String qqIndex;
    @Getter
    @Setter
    private String gender;
    @Getter
    @Setter
    private String qAge;
    @Getter
    @Setter
    private String groupingTime;
    @Getter
    @Setter
    private String grade;
    @Getter
    @Setter
    private String finalStatement;

    public QqInformation(String qqName, String qqQunName, String qqIndex, String gender, String qAge, String groupingTime, String grade, String finalStatement) {
        this.qqName = qqName;
        this.qqQunName = qqQunName;
        this.qqIndex = qqIndex;
        this.gender = gender;
        this.qAge = qAge;
        this.groupingTime = groupingTime;
        this.grade = grade;
        this.finalStatement = finalStatement;
    }


    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("QQ昵称", qqName);
        jsonObject.set("QQ群昵称", qqQunName);
        jsonObject.set("QQ号", qqIndex);
        jsonObject.set("性别", gender);
        jsonObject.set("qq年龄", qAge);
        jsonObject.set("进群时间", groupingTime);
        jsonObject.set("等级", grade);
        jsonObject.set("最后发言", finalStatement);
        return jsonObject.toString();
    }
}
