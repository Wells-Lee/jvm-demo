package com.wells.jvm.demo.c2.error;

import java.util.concurrent.TimeUnit;

/**
 * Description 栈溢出
 * 通过 -Xss 设置栈固定大小，至少160k，否则jvm启动不起来；固定大小栈大小只能抛出 StackOverflowError；
 * 如果不固定栈大小，会抛出OOM；
 * Created by wells on 2020-03-28 23:07:15
 */

public class StackOverflowTest {
    public static int count = 0;

    public void test() {
        count++;

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test();
    }

    public static void main(String[] args) {
        StackOverflowTest stackOverflowTest = new StackOverflowTest();
        try {
            stackOverflowTest.test();
        } catch (Throwable t) {
            System.out.println("count:" + StackOverflowTest.count);
            t.printStackTrace();
        }
    }
}
