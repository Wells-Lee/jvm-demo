package com.wells.jvm.demo.c2.error;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Description MetaSpaceTest
 * jdk1.8以后永久代就已经被抛弃了，因此 -XX:MaxPermSize 这个参数是无效的；
 * 通过 -XX:MaxMetaspaceSize 设置元空间大小，64位机器默认为21M，如果到了上限，会进行一次GC，并且向机器内存申请扩大 MetaSpace
 *
 * jconsole表现：堆内存几乎不变，类加载数直线上升，metaSpace占用大小直线上升；
 *
 * 元空间描述: https://www.infoq.cn/article/Java-permgen-Removed
 * Created by wells on 2020-03-29 10:02:50
 */

public class MetaSpaceOOMTest {
    public static void main(String[] args) {
        for (; ; ) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MetaSpaceOOMTest.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invokeSuper(obj, args1));
            System.out.println("Hello World");

            enhancer.create();
        }
    }
}
