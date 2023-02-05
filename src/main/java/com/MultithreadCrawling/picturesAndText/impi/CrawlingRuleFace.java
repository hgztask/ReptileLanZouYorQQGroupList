package com.MultithreadCrawling.picturesAndText.impi;

/**
 * 规则
 * @param <T>
 */
public interface CrawlingRuleFace<T> {
    /**
     * 编写规则爬取页面内容
     *
     * @param html html页面源码
     * @return 数组结果
     */
    public abstract T crawlingRule(String html);

}
