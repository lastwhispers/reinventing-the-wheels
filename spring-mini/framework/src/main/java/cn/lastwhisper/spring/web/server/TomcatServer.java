package cn.lastwhisper.spring.web.server;

import cn.lastwhisper.spring.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * 将tomcat内嵌到项目中，并与Servlet绑定
 * @author lastwhisper
 */
public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();

        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet)
                .setAsyncSupported(true);
        context.addServletMappingDecoded("/", "dispatcherServlet");
        tomcat.getHost().addChild(context);


        Thread awaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };

        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
