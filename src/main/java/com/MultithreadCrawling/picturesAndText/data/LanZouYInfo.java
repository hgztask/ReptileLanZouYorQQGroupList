package com.MultithreadCrawling.picturesAndText.data;

import lombok.Data;

import java.util.Objects;

/**
 * 蓝奏云列表的一些信息
 */
@Data
public class LanZouYInfo {
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源大小
     */
    private String size;

    /**
     * 资源时间
     */
    private String time;

    /**
     * 资源对应的页面url
     */
    private String url;

    /**
     * 后缀
     */
    private String suffixType;

    /**
     * 重写该方法,用于判断两个对象name和url值是否相同,相同则为两个对象相等,反之不相同
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LanZouYInfo that = (LanZouYInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
