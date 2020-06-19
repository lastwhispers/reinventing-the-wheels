package cn.lastwhisper.spring.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author lastwhisper
 */
public class ClassScanner {

    /**
     * 扫描jar包下的所有class文件，Application.class目录下所有的class文件，并放入一个list里面
     *
     * @param packageName
     * @return java.util.List<java.lang.Class < ?>>
     */
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().contains("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassFromJar(jarFilePath, path));

            } else {

            }

        }
        return classList;

    }

    /**
     * 加载一个jar文件下的所有class文件
     *
     * @param jarFilePath
     * @param path
     * @return java.util.List<java.lang.Class <?>>
     */
    private static List<Class<?>> getClassFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName(); // cn/lastwhisper/spring/Test.class
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
