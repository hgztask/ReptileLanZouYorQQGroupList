package com.MultithreadCrawling.picturesAndText.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/***
 * 封装自定义组件对话框-列表样式
 * @param <T>
 */
public class JListView<T> {


    /**
     * 窗口
     */
    private JDialog jDialog = new JDialog();


    private CountDownLatch latch;


    private JList<T> jlist = new JList<>();

    private JButton jButton = new JButton("确定");

    /**
     * 数据模型
     */
    private DefaultComboBoxModel<T> jListModel = new DefaultComboBoxModel<>();


    /**
     * 组件确定之后的值
     */
    private T var;


    {
        //获取屏幕分辨率大小
        jDialog.setLayout(null);
        jDialog.setSize(300, 180);
        jDialog.setResizable(false);
        jDialog.setAlwaysOnTop(true);
        jDialog.add(jlist);
        jDialog.add(jButton);
        jButton.setBounds(jDialog.getWidth() - 100, jDialog.getHeight() - 35 * 2, 100, 35);
        jlist.setSize(jDialog.getWidth(), jButton.getY());
        jButton.setBackground(Color.ORANGE);

        jDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jDialog.dispose();
                latch.countDown();

            }
        });


        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = jlist.getSelectedIndex();
                var = jlist.getModel().getElementAt(index);
                jDialog.dispose();
                latch.countDown();
            }
        });
    }


    public JListView(String title) {
        jDialog.setTitle(title);
    }

    /**
     * 设置数据模型
     *
     * @param listModel
     */
    public void setModel(DefaultComboBoxModel<T> listModel) {
        if (listModel == null) {
            throw new NullPointerException("listModel=null");
        }
        this.jListModel = listModel;
    }


    public void add(T var) {
        jListModel.addElement(var);
    }

    public void addAll(Collection<? extends T> c) {
        jListModel.addAll(c);
    }


    /**
     * 返回确定之后的值
     *
     * @return
     */
    public T getVar() {
        T temp = var;
        var = null;

        return temp;
    }

    /**
     * 设置对话框标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        jDialog.setTitle(title);
    }


    public void show() {
        jlist.setModel(jListModel);
        jDialog.setVisible(true);
        latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
