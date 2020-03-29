package com.wells.jvm.demo.c1.classloader;

import java.util.UUID;

/**
 * Description
 * -XX:+TraceClassLoading，用于追踪类的信息并打印出来，放在JVM启动参数中
 * <p>
 * 1、对于静态字段来说，只有直接定义了该字段的类才会被初始化: 下面子类中的static不会被执行
 * 2、当一个类在初始化时，要求其所有的父类全部进行初始化
 * 3、已知常量在编译阶段会存入到调用这个常量的方法所在的类的常量池中，因此调用的时候不会触发定义常量类的初始化
 * - 被final修饰的常量打印，并不会被触发类初始化
 * 4、未知常量(比如: 随机数)所在类会被初始化: 在编译期间确定不了的常量，所在类会被初始化
 * 5、直接new对象，所在类会被初始化
 * Created by wells on 2020-03-18 06:34:09
 */

public class ClassInitTest {
    public static void main(String[] args) {
        System.out.println(StaticVarChild.str);
        System.out.println(FinalVarTest.str);
        System.out.println(UnknowFinalVarTest.str);
        NewObjectTest newObjectTest = new NewObjectTest();
        NewObjectArrTest[] newObjectArrTests = new NewObjectArrTest[1];

        Singleton singleton = Singleton.getInstance();
        System.out.println(Singleton.count1);
        System.out.println(Singleton.count2);
    }
}


/**
 * 只有直接使用了静态字段的类才会被初始化
 */
class StaticVarTest {
    public static String str = "Hello World";

    static {
        System.out.println("StaticVarTest static init");
    }
}

class StaticVarChild extends StaticVarTest {
    static {
        System.out.println("StaticVarChild static block");
    }
}

/**
 * 直接使用常量，static静态块不会被初始化执行
 */
class FinalVarTest {
    public static final String str = "final var";

    static {
        System.out.println("FinalVarTest static block");
    }
}

/**
 * 常量类在编译期间不确定，因此在初始化阶段，所在类会被初始化
 */
class UnknowFinalVarTest{
    public static final String str = UUID.randomUUID().toString();

    static {
        System.out.println("UnknowFinalVarTest static init");
    }
}

/**
 * new对象类会被初始化
 */
class NewObjectTest{
    static {
        System.out.println("NewObjectTest static init");
    }
}

/**
 * 对象数组创建，当前类不会被初始化: 是在JVM运行期间动态创建出来的
 */
class NewObjectArrTest{
    static {
        System.out.println("NewObjectArrTest static block");
    }
}

/**
 * 当把 public static int count2 = 0; 代码放到构造方法之前，count1输出为1，count2输出为1: 先对 count2 进行链接阶段的准备，赋默认值0 -> 初始化: count2=0 -> 执行构造方法: count2++
 * 当把 public static int count2 = 0; 代码放到构造方法之后，count1输出为1，count2输出为0: 先对 count2 进行链接阶段的准备，赋默认值0 -> count2++ -> 初始化: count2=0
 *
 * 造成上面原因的是：类执行的时候，按照编译后的字节码指令指令(按照顺序)
 */
class Singleton{
    public static int count1;

    public static int count2 = 0;

    public static Singleton singleton = new Singleton();

    private Singleton(){
        count1++;
        count2++;

        System.out.println(count1);
        System.out.println(count2);
    }

    public static Singleton getInstance(){
        return singleton;
    }
}
