package me.jcis.chaos.core.groovy;

import com.alibaba.fastjson.JSON;
import freemarker.template.TemplateException;
import me.jcis.chaos.core.ConfigClass;
import me.jcis.chaos.core.constant.Charset;
import me.jcis.chaos.core.constant.FreeMarker;
import me.jcis.chaos.core.constant.GroovyClazz;
import me.jcis.chaos.core.freemarker.LazyFreeMarker;
import me.jcis.chaos.core.log.BaseLogger;
import me.jcis.chaos.utils.GroovyUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/28
 */
public class LazyGroovyClass extends BaseLogger implements ConfigClass {

    private static String FreeMarkerType = FreeMarker.ConfigType.groovy.name();

    /**
     * LazyGroovyClass 单例
     */
    private static LazyGroovyClass instance = null;

    /**
     * 配置信息
     */
    private Map config;

    private URI configURI;

    private Map<String, List<Class>> clazzCache = new HashMap<String, List<Class>>();

    public LazyGroovyClass() {
        // do nothing
    }

    private LazyGroovyClass(Resource resource) {
        setConfig(resource);
        init();
    }

    /**
     * 公开，静态的工厂方法，需要使用时才去创建该单体
     * @param resource 配置文件
     * @return LazyGroovyClass实例
     */
    public static LazyGroovyClass getInstance(Resource resource){
        if(isNullOrEmpty(instance)){
            instance = new LazyGroovyClass(resource);
        }
        return instance;
    }

    /**
     * LazyGroovyClass 配置文件
     * @param config 配置文件
     */
    public void setConfig(Resource config) {
        try {
            this.configURI = config.getURI();
            this.config = JSON.parseObject(IOUtils.toString(config.getURI(), Charset.CHARSET_UTF_8), Map.class);
        } catch (IOException e) {
            logger.error("获取配置文件失败，失败原因：【{0}】", e.getLocalizedMessage());
        }
    }

    public void init(){
        if(isNotNull(config)){
            logger.info("开始初始化Groovy配置信息！");
        }else{
            logger.error("缺少配置信息！");
        }
    }

    private ClassLoader getClassLoader(String clazzName){
        ClassLoader cl = GroovyClazz.GroovyClazzClassLoader.getClassLoader(clazzName);
        return isNull(cl) ? this.getClass().getClassLoader() : cl;
    }

    private File getTemplate(String clazzName){
        return isNotNull(config)&&config.containsKey(clazzName) ? new File (getTemplateName(clazzName)) :
                null;
    }

    private String getTemplateName(String clazzName){
        return isNotNull(config)&&config.containsKey(clazzName) ? (String) ((Map)config.get(clazzName)).get(GroovyClazz.ConfigTag.template.name()) :
                "";
    }

    private boolean isIndependent(String clazzName){
        return isNotNull(config)&&config.containsKey(clazzName) ? (Boolean) ((Map) config.get(clazzName)).get(GroovyClazz.ConfigTag.independent.name()) :
                false;
    }

    /**
     * warn: 依赖于lazyFreeMarker的配置
     * @param clazzType Class类别，如entity,ledger等,对应在groovyConfig配置文件中的key值
     * @param lazyFreeMarker LazyFreeMarker实例
     * @param value 渲染该类别的Class所需要的参数，包括类名，字段名，字段类型等
     * @return 具体的Class
     * @throws IOException
     * @throws TemplateException
     */
    public Class getClazz(String clazzType, LazyFreeMarker lazyFreeMarker, Object value) throws IOException, TemplateException {
        if(value instanceof Map&&((Map)value).containsKey(GroovyClazz.ClazzTag.className.name())&&
                clazzCache.containsKey(clazzType)){
            List<Class> clazzs = clazzCache.get(clazzType);
            for(Class clazz : clazzs){
                if(clazz.getSimpleName().equalsIgnoreCase((String) ((Map)value).get(GroovyClazz.ClazzTag.className.name()))){
                    return clazz;
                }
            }
        }
        
        if(isIndependent(clazzType)) {
            // 是不依赖于LazyFreeMarker实例配置的类型
            logger.warn("根据配置{0}类型的groovy类是独立于LazyFreeMarker实例的，调用此方法有可能出错！");
        }
        // LazyFreeMarker的配置中必须要有key为groovy的配置项
        // template文件的路径必须在LazyFreeMarker实例的groovy配置项下进行配置
        Class clazz = GroovyUtils.generateClass(clazzType, getClassLoader(clazzType), lazyFreeMarker, FreeMarkerType, getTemplateName(clazzType), value);
        if(clazzCache.containsKey(clazzType)){
            clazzCache.get(clazzType).add(clazz);
        }else {
            List<Class> classes = new ArrayList<Class>();
            classes.add(clazz);
            clazzCache.put(clazzType, classes);
        }
        return clazz;
    }
    
    public boolean removeClazz(String clazzType, Class clazz){
        if(clazzCache.containsKey(clazzType)){
            return clazzCache.get(clazzType).remove(clazz);
        }
        logger.warn("【{0}】类别的Class集合不存在！",clazzType);
        return true;
    }

}
