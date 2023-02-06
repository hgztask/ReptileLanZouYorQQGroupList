package com.MultithreadCrawling.picturesAndText;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.MultithreadCrawling.picturesAndText.business.HomeEss;
import lombok.NonNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author byhgz
 * @version 1.0
 * @date 2023/2/4 23:38
 */
public class Main {
    public static void main(String[] args) {
        HomeEss.getInstance().show();

    }


}
