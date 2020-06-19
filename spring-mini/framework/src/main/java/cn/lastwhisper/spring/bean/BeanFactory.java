package cn.lastwhisper.spring.bean;

import cn.lastwhisper.spring.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象创建工厂
 * @author lastwhisper
 */
public class BeanFactory {
    public static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    /**
     * 根据class获取对应对象
     *
     * @param cls
     * @return java.lang.Object
     */
    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    /**
     * 初始化所有对象并解决循环依赖问题
     *
     * @param classList
     * @return void
     */
    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        while (toCreate.size() != 0) {
            int remainSize = toCreate.size();
            //for (int i = 0; i < toCreate.size(); i++) {
            //    if (finishCreate(toCreate.get(i))) {
            //        toCreate.remove(i);
            //    }
            //}
            for (int i = 0; i < toCreate.size(); ) {
                if (finishCreate(toCreate.get(i))) {
                    toCreate.remove(i);
                } else {
                    i++;
                }
            }
            if (toCreate.size() == remainSize) {
                throw new Exception("cycle dependency!");
            }
        }
    }

    /**
     * 初始化对象并依赖注入
     *
     * @param cls
     * @return boolean
     */
    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        // 不需要初始化
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
            return true;
        }
        // 创建对象
        Object bean = cls.newInstance();

        // 依赖注入
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoWired.class)) {
                Class<?> fieldType = field.getType();
                Object reliantBean = classToBean.get(fieldType);
                if (reliantBean == null) {
                    return false;
                }
                field.setAccessible(true);
                field.set(bean, reliantBean);
            }
        }
        classToBean.put(cls, bean);
        return true;
    }
}
