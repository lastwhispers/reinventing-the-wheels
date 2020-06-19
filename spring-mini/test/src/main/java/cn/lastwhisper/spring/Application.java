package cn.lastwhisper.spring;

import cn.lastwhisper.spring.starter.MiniApplication;

/**
 * @author lastwhisper
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World");
        MiniApplication.run(Application.class, args);
    }
}
