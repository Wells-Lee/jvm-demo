package com.wells.jvm.demo.c2.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 堆溢出:
 * 可通过 -Xms5m -Xmx5m -XX:+HeapDumpOnOutOfMemoryError(打印堆溢出的日志，可通过 jvisualvm -> 文件 -> 装入 加载查看)
 * OOM很严重，会直接导致jvm挂掉，可查看 OutOfMemoryError 类解释
 *
 * Created by wells on 2020-03-28 21:52:33
 */

public class HeapOOMTest {
    public static void main(String[] args) {
        List<HeapOOMTest> list = new ArrayList<HeapOOMTest>();

        for (; ; ) {
            list.add(new HeapOOMTest());
        }
    }
}