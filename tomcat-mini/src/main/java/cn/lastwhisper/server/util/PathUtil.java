package cn.lastwhisper.server.util;

import java.io.File;

/**
 * 适用于Linux和Windows系统,根目录为jar包上一层目录
 * <br>
 * @author lastwhisper
 */
public class PathUtil {
    //获取项目的上一层目录
    public final static String PROJECT_PATH;

    static {
        //打包为jar时的路径
        PROJECT_PATH =  System.getProperty("user.dir") + File.separator;
    }

    /**
     * 项目上一层目录
     */
    public static String getRootPath() {
        return rootPath("");
    }

    /**
     * 自定义追加路径
     */
    public static String getRootPath(String uPath) {
        return rootPath(uPath);
    }

    /**
     * 私有处理方法
     */
    private static String rootPath(String uPath) {
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            rootPath = PROJECT_PATH + uPath;
            rootPath = rootPath.replaceAll("/", "\\\\");
            if ("\\".equals(rootPath.substring(0, 1))) {
                rootPath = rootPath.substring(1);
            }
        }
        //linux下
        if ("/".equals(File.separator)) {
            rootPath = PROJECT_PATH + uPath;
            rootPath = rootPath.replaceAll("\\\\", "/");
        }
        return rootPath;
    }

}
