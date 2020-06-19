import cn.lastwhisper.server.core.Server;

import java.io.IOException;

/**
 * @author lastwhisper
 * @desc mytomcat启动类
 */
public class App {

    public static void main(String[] args) throws IOException {
        new Server().start();

    }
}
