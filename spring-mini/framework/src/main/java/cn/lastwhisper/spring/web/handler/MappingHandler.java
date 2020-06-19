package cn.lastwhisper.spring.web.handler;

import cn.lastwhisper.spring.bean.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 封装一个Controller的 uri、method对象、class对象、入参args
 * @author lastwhisper
 */
public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> cls, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = cls;
        this.args = args;
    }

    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        // 获取uri
        String requestURI = ((HttpServletRequest) req).getRequestURI();
        if (!uri.equals(requestURI)) {
            return false;
        }

        Object[] parameters = new Object[args.length];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = req.getParameter(args[i]);
        }

        //Object ctl = controller.newInstance();
        Object ctl = BeanFactory.getBean(controller);
        Object response = method.invoke(ctl, parameters);

        res.getWriter().println(response.toString());

        return true;
    }


}
