package com.MultithreadCrawling.picturesAndText.impi;

/**
 *
 * @param <T> 数组类型
 * @param <V> 查找的关键对象
 */
public interface BsearchFace<T, V> {

    /**
     * 使用伪二分查找法查找指定数据
     *
     * @param array
     * @param value
     * @return
     */
    int bsearch(T array, V value);
}
