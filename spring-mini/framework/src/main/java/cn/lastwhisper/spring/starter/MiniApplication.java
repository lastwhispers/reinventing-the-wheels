package cn.lastwhisper.spring.starter;

import cn.lastwhisper.spring.bean.BeanFactory;
import cn.lastwhisper.spring.core.ClassScanner;
import cn.lastwhisper.spring.web.handler.HandlerManager;
import cn.lastwhisper.spring.web.server.TomcatServer;

import java.util.List;
import java.util.Map;

/**
 * @author lastwhisper
 */
public class MiniApplication {
    // cls用于定位到项目的root目录
    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello Mini-Spring");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            // 获取Application.class所在的包名
            List<Class<?>> classes = ClassScanner.scanClasses(cls.getPackage().getName());
            // 初始化Bean容器
            BeanFactory.initBean(classes);
            // 将带有Controller注解的类添加到mappingHandlerList
            HandlerManager.resolveMappingHandler(classes);
            //classes.forEach(it -> System.out.println(it.getName()));
            for (Class<?> clazz : classes) {
                System.out.println(clazz.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
