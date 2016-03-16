package me.jcis.chaos.utils;

import freemarker.template.TemplateException;
import groovy.lang.GroovyClassLoader;
import me.jcis.chaos.core.exception.ConfigNotFoundException;
import me.jcis.chaos.core.freemarker.LazyFreeMarker;
import me.jcis.chaos.core.log.BaseLogger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public class GroovyUtils extends BaseLogger {

    //TODO: 对所有的Groovy脚本采用一个全局的GroovyClassLoader，这种方式当应用用到了很多的Groovy Script时将出现问题，
    //TODO: PermGen中的class能被卸载的前提是ClassLoader中所有的class都没有地方引用，
    //TODO: 因此如果是共用同一个GroovyClassLoader的话就很容易导致脚本class无法被卸载，从而导致PermGen被用满。
    final static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public final static String DEFAULT = "default";

    static Map<String, GroovyClassLoader> groovyClassLoaderPool = new HashMap<String, GroovyClassLoader>(){
        {
            put("default", groovyClassLoader);
        }
    };

    public static GroovyClassLoader getDefaultClassLoader() {
        return groovyClassLoader;
    }

    private static GroovyClassLoader getGroovyClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = GroovyUtils.class.getClassLoader();
        }
        return new GroovyClassLoader(cl);
    }

    /**
     * GroovyUtils有一个GroovyClassLoader池，默认包含两个GroovyClassLoader，
     * main线程使用的GroovyClassLoader 实例和default GroovyClassLoader实例。
     * 当GroovyUtils在main线程以外的线程中使用时，GroovyClassLoader池会自动追加当前线程的GroovyClassLoader实例。
     * 当需要使用当前线程的GroovyClassLoader实例以外的GroovyClassLoader实例时，可以使用此方法。
     * 如果key值在实例池中没有相应的GroovyClassLoader实例，系统会根据ClassLoader实例创建相应的GroovyClassLoader实例，
     * 并放入实例池。
     * @param key GroovyClassLoader实例在实例池中的key值
     * @param cl 当实例池中没有相应的实例时，可以使用此ClassLoader实例进行创建
     * @return GroovyClassLoader实例
     */
    public static GroovyClassLoader getSpecGroovyClassLoader(String key, ClassLoader cl){
        GroovyClassLoader groovyClassLoader = getGroovyClassLoader(key);
        return isNullOrEmpty(groovyClassLoader) ? addGroovyClassLoaderPool(key, new GroovyClassLoader(cl)) :
                groovyClassLoader;
    }

    /**
     * 返回当前线程使用的GroovyClassLoader，
     */
    public static GroovyClassLoader getCurrentGroovyClassLoader() {
        Thread curThread = Thread.currentThread();
        String key = curThread.getName();
        GroovyClassLoader groovyClassLoader = getGroovyClassLoader(key);
        if(null == groovyClassLoader){
            // 如果当前线程没有创建GroovyClassLoader，创建并插入GroovyClassLoader池
            println(key+"首次创建GroovyClassLoader");
            groovyClassLoader = getGroovyClassLoader();
            groovyClassLoaderPool.put(key, groovyClassLoader);
        }
        return getGroovyClassLoader(key);
    }

    /**
     * 返回指定线程使用的线程池
     * @param threadName 线程名
     * @return GroovyClassLoader实例,当指定线程实例不存在时，返回null
     */
    public static GroovyClassLoader getGroovyClassLoader(String threadName) {
        if(groovyClassLoaderPool.containsKey(threadName)){
            return groovyClassLoaderPool.get(threadName);
        }else{
            return null;
        }
    }

    public static GroovyClassLoader addGroovyClassLoaderPool(String key, GroovyClassLoader groovyClassLoader){
        groovyClassLoaderPool.put(key, groovyClassLoader);
        return groovyClassLoaderPool.get(key);
    }

    public static GroovyClassLoader removeGroovyClassLoaderPool(String key) {
        return groovyClassLoaderPool.remove(key);
    }

    /**
     * Override the thread context ClassLoader with the environment's bean ClassLoader
     * if necessary, i.e. if the bean ClassLoader is not equivalent to the thread
     * context ClassLoader already.
     * @param classLoaderToUse the actual ClassLoader to use for the thread context
     * @return the original thread context ClassLoader, or {@code null} if not overridden
     */
    public static ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse) {
        Thread currentThread = Thread.currentThread();
        ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
        if (classLoaderToUse != null && !classLoaderToUse.equals(threadContextClassLoader)) {
            currentThread.setContextClassLoader(classLoaderToUse);
            return threadContextClassLoader;
        }
        else {
            return null;
        }
    }


    /**
     * 通过当前线程的GroovyClassLoader实例动态加载ftl文件
     * warn: 该方法严重依赖于LazyFreeMarker
     * @param tpConfig LazyFreeMarker需要的配置文件，如果确信已经存在LazyFreeMarker的实例，可以为NULL
     * @param classType freemarker配置key值
     * @param tplName 指定tpl文件（tpl文件需要在对应configu的path下）
     * @param value 渲染tpl文件需要的参数
     * @return tpl文件对应的Class
     * @throws IOException
     * @throws TemplateException
     * @throws ConfigNotFoundException
     */
    @Deprecated
    public static Class generateClass(File tpConfig, String classType, String tplName, Object value) throws IOException, TemplateException, ConfigNotFoundException {
        LazyFreeMarker lazyFreeMarker = LazyFreeMarker.getInstance(tpConfig);
        String clazzTpl = lazyFreeMarker.getTpl(classType, tplName, value);
        return generateClass(clazzTpl);
    }

    /**
     * 通过给定的GroovyClassLoader实例动态加载ftl文件
     * warn: 该方法严重依赖于LazyFreeMarker
     * @param groovyClassLoader 给定的GroovyClassLoader实例
     * @param tpConfig LazyFreeMarker需要的配置文件，如果确信已经存在LazyFreeMarker的实例，可以为NULL
     * @param classType freemarker配置key值
     * @param tplName 指定tpl文件（tpl文件需要在对应configu的path下）
     * @param value 渲染tpl文件需要的参数
     * @return tpl文件对应的Class
     * @throws IOException
     * @throws TemplateException
     * @throws ConfigNotFoundException
     */
    public static Class generateClass(GroovyClassLoader groovyClassLoader, File tpConfig, String classType, String tplName, Object value) throws IOException, TemplateException, ConfigNotFoundException {
        LazyFreeMarker lazyFreeMarker = LazyFreeMarker.getInstance(tpConfig);
        String clazzTpl = lazyFreeMarker.getTpl(classType, tplName, value);
        return generateClass(groovyClassLoader,clazzTpl);
    }

    /**
     * 通过GroovyClassLoader实例在实例池中的key值动态加载ftl文件
     * warn: 该方法严重依赖于LazyFreeMarker
     * @param groovyClassLoaderKey GroovyClassLoader实例在实例池中的key值
     * @param cl 当key值对应的实例不存在时，可以使用ClassLoader的实例创建一个新的GroovyClassLoader实例
     * @param tpConfig LazyFreeMarker需要的配置文件，如果确信已经存在LazyFreeMarker的实例，可以为NULL
     * @param classType freemarker配置key值
     * @param tplName 指定tpl文件（tpl文件需要在对应configu的path下）
     * @param value 渲染tpl文件需要的参数
     * @return tpl文件对应的Class
     * @throws IOException
     * @throws TemplateException
     * @throws ConfigNotFoundException
     */
    public static Class generateClass(String groovyClassLoaderKey, ClassLoader cl, File tpConfig, String classType, String tplName, Object value) throws IOException, TemplateException, ConfigNotFoundException {
        LazyFreeMarker lazyFreeMarker = LazyFreeMarker.getInstance(tpConfig);
        String clazzTpl = lazyFreeMarker.getTpl(classType, tplName, value);
        return generateClass(getSpecGroovyClassLoader(groovyClassLoaderKey, cl),clazzTpl);
    }

    /**
     * 通过当前线程的GroovyClassLoader实例动态加载ftl文件
     * @param lazyFreeMarker LazyFreeMarker实例
     * @param classType freemarker配置key值
     * @param tplName 指定tpl文件（tpl文件需要在对应configu的path下）
     * @param value 渲染tpl文件需要的参数
     * @return tpl文件对应的Class
     * @throws IOException
     * @throws TemplateException
     */
    public static Class generateClass(LazyFreeMarker lazyFreeMarker, String classType, String tplName, Object value) throws IOException, TemplateException {
        String clazzTpl = lazyFreeMarker.getTpl(classType, tplName, value);
        return generateClass(clazzTpl);
    }

    /**
     * 通过给定的GroovyClassLoader实例动态加载ftl文件
     * @param groovyClassLoader 给定的GroovyClassLoader实例
     * @param lazyFreeMarker LazyFreeMarker实例
     * @param classType freemarker配置key值
     * @param tplName 指定tpl文件（tpl文件需要在对应configu的path下）
     * @param value 渲染tpl文件需要的参数
     * @return tpl文件对应的Class
     * @throws IOException
     * @throws TemplateException
     */
    public static Class generateClass(GroovyClassLoader groovyClassLoader, LazyFreeMarker lazyFreeMarker, String classType, String tplName, Object value) throws IOException, TemplateException {
        String clazzTpl = lazyFreeMarker.getTpl(classType, tplName, value);
        return generateClass(groovyClassLoader, clazzTpl);
    }

    /**
     * 通过给定的GroovyClassLoader实例动态加载ftl文件
     * @param groovyClassLoaderKey GroovyClassLoader实例在实例池中的key值
     * @param cl 当key值对应的实例不存在时，可以使用ClassLoader的实例创建一个新的GroovyClassLoader实例
     * @param lazyFreeMarker LazyFreeMarker实例
     * @param classType freemarker配置key值
     * @param tplName 指定tpl文件（tpl文件需要在对应configu的path下）
     * @param value 渲染tpl文件需要的参数
     * @return tpl文件对应的Class
     * @throws IOException
     * @throws TemplateException
     */
    public static Class generateClass(String groovyClassLoaderKey, ClassLoader cl, LazyFreeMarker lazyFreeMarker, String classType, String tplName, Object value) throws IOException, TemplateException {
        String clazzTpl = lazyFreeMarker.getTpl(classType, tplName, value);
        return generateClass(getSpecGroovyClassLoader(groovyClassLoaderKey, cl),clazzTpl);
    }

    /**
     * 通过当前线程的GroovyClassLoader实例动态加载groovy脚本内容
     * @param groovyStr groovy脚本内容
     * @return groovy脚本内容对应的Class
     */
    public static Class generateClass(String groovyStr) {
        return generateClass(getCurrentGroovyClassLoader(), groovyStr);
    }

    /**
     * 通过当前线程的GroovyClassLoader实例动态加载groovy 脚本文件
     * @param scriptFile groovy 脚本文件
     * @return 脚本文件对应的Class
     * @throws IOException
     */
    public static Class generateClass(File scriptFile) throws IOException {
        return generateClass(getCurrentGroovyClassLoader(), scriptFile);
    }

    /**
     * 通过给定的GroovyClassLoader实例动态加载groovy 脚本内容
     * @param groovyClassLoader 给定的GroovyClassLoader实例
     * @param groovyStr groovy脚本内容
     * @return groovy脚本内容对应的Class
     */
    public static Class generateClass(GroovyClassLoader groovyClassLoader, String groovyStr){
        return groovyClassLoader.parseClass(groovyStr);
    }

    /**
     * 通过给定的GroovyClassLoader实例动态加载groovy 脚本文件
     * @param groovyClassLoader 给定的GroovyClassLoader实例
     * @param scriptFile groovy脚本文件
     * @return groovy脚本文件对应的Class
     * @throws IOException
     */
    public static Class generateClass(GroovyClassLoader groovyClassLoader, File scriptFile) throws IOException {
        return groovyClassLoader.parseClass(scriptFile);
    }
}
