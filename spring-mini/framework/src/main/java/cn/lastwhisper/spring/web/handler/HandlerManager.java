package cn.lastwhisper.spring.web.handler;

import cn.lastwhisper.spring.web.mvc.Controller;
import cn.lastwhisper.spring.web.mvc.RequestMapping;
import cn.lastwhisper.spring.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lastwhisper
 */
public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();
    
    /** 
     * 
     *
     * @param classList
     * @return void
     */
    public static void resolveMappingHandler(List<Class<?>> classList) {

        for (Class<?> cls : classList) {
            if (cls.isAnnotationPresent(Controller.class)) {
                parseHandlerFromController(cls);
            }
        }

    }

    /**
     * 处理Controller
     *
     * @param cls
     * @return void
     */
    private static void parseHandlerFromController(Class<?> cls) {
        // 获取Controller下的所有方法
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            // 该方法是否RequestMapping注解
            if (method.isAnnotationPresent(RequestMapping.class)) {
                // 请求的url
                String uri = method.getDeclaredAnnotation(RequestMapping.class).value();

                // 请求的参数名
                List<String> paramNameList = new ArrayList<>();
                for (Parameter parameter : method.getParameters()) {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                    }
                }
                String[] params = paramNameList.toArray(new String[paramNameList.size()]);
                MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);

                HandlerManager.mappingHandlerList.add(mappingHandler);
            }
        }
    }
}
