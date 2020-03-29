package com.wells.jvm.demo.c2.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * Description 死锁示例
 * 为了看 jconsole 和 jvisualvm
 * Created by wells on 2020-03-29 09:34:12
 */

public class DeadLockTest {
    public static void main(String[] args) {
        new Thread(() -> A.method(), "Thread-A").start();
        new Thread(() -> B.method(), "Thread-B").start();
    }
}

class A {
    public static synchronized void method() {
        System.out.println("method from A");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        B.method();
    }
}

class B {
    public static synchronized void method() {
        System.out.println("method from B");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        A.method();
    }
}