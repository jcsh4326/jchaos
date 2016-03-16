package me.jcis.chaos.core.freemarker;

import com.alibaba.fastjson.JSON;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import me.jcis.chaos.core.ConfigClass;
import me.jcis.chaos.core.constant.Charset;
import me.jcis.chaos.core.constant.FreeMarker;
import me.jcis.chaos.core.exception.ConfigNotFoundException;
import me.jcis.chaos.core.exception.ConfigValueNotFoundException;
import me.jcis.chaos.core.log.BaseLogger;
import me.jcis.chaos.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public class LazyFreeMarker extends BaseLogger implements ConfigClass {
    /**
     * LazyFreeMarker 单例
     */
    private static LazyFreeMarker instance = null;

    /**
     * freemarker配置信息
     */
    private Map config;

    public LazyFreeMarker(){
        // do nothing
    }

    //私有的构造子(构造器,构造函数,构造方法)
    private LazyFreeMarker(File file){
        setConfig(file);
        init();
    }

    /**
     * 公开，静态的工厂方法，需要使用时才去创建该单体
     * @param file 配置文件
     * @return LazyFreeMarker实例
     */
    @Deprecated
    public static LazyFreeMarker getInstance(File file) {
        if( isNullOrEmpty(instance) ) {
            println("首次通过单例初始化LazyFreeMarker\n");
            instance = new LazyFreeMarker(file);
        }
        return instance;
    }

    //私有的构造子(构造器,构造函数,构造方法)
    private LazyFreeMarker(Resource resource){
        setConfig(resource);
        init();
    }

    /**
     * 公开，静态的工厂方法，需要使用时才去创建该单体
     * @param resource 配置文件
     * @return LazyFreeMarker实例
     */
    public static LazyFreeMarker getInstance(Resource resource) {
        if( isNullOrEmpty(instance) ) {
            println("首次通过单例初始化LazyFreeMarker\n");
            instance = new LazyFreeMarker(resource);
        }
        return instance;
    }

    private Map<String, Configuration> cfgMap;

    /**
     * 初始化配置文件
     * @param config 配置文件路径
     */
    public void setConfig(Resource config) {
        try {
            this.config = JSON.parseObject(IOUtils.toString(config.getURI(), Charset.CHARSET_UTF_8), Map.class);
        } catch (IOException e) {
            logger.error("获取配置文件失败，失败原因：【{0}】", e.getLocalizedMessage());
        }
    }

    public void setConfig(File file) {
        try {
            config = JSON.parseObject(IOUtils.toString(file.toURI(), Charset.CHARSET_UTF_8), Map.class);
        } catch (IOException e) {
            logger.error("获取配置文件失败，失败原因：【{0}】", e.getLocalizedMessage());
        } catch (Exception e){
            logger.error("获取配置文件失败，失败原因：【{0}】", e.getLocalizedMessage());
        }
    }

    /**
     * 初始化freemarker配置
     */
    public void init(){
        if(isNotNull(config)){
            logger.info("开始初始化FreeMarker配置信息！");
            cfgMap = new HashMap<String, Configuration>();
            Iterator<Map.Entry<String, Map>> iterator = config.entrySet().iterator();
            while(iterator.hasNext()){
                 Map.Entry<String, Map> entry = iterator.next();
                String key = entry.getKey();
                try {
                    cfgMap.put(key, setConfiguration(entry.getValue()));
                    logger.debug("【{}】配置成功！",key);
                } catch (IOException e) {
                    logger.error("配置文件读取出错，出错原因【{}】",e.getLocalizedMessage());
                } catch (ConfigValueNotFoundException e){
                    logger.error("配置文件配置出错，出错原因【{}】", e.getLocalizedMessage());
                }
            }
            logger.info("已完成【{}】条FreeMarker的配置工作！", cfgMap.entrySet().size());
        }else{
            logger.error("缺少配置信息！");
        }
    }

    private Configuration setConfiguration(Map config) throws IOException {
        if(!config.containsKey(FreeMarker.ConfigTag.path.name())||isNull(config.get(FreeMarker.ConfigTag.path.name())))
            throw new ConfigValueNotFoundException("path字段没有配置！");

        Configuration cfg = new Configuration(config.containsKey(FreeMarker.ConfigTag.version.name()) ?
                new Version((String) config.get(FreeMarker.ConfigTag.version.name())) : FreeMarker.Version.getVersion(FreeMarker.Version.VERSION_2_3_23));
        cfg.setEncoding(Locale.getDefault(), config.containsKey(FreeMarker.ConfigTag.encoding.name()) ?
                (String) config.get(FreeMarker.ConfigTag.encoding.name()) : Charset.DEFAULT_CHARSET);
        String path = (String) config.get(FreeMarker.ConfigTag.path.name());
        cfg.setDirectoryForTemplateLoading(FileUtils.getFile(path));
        return cfg;
    }

    public Configuration getConfigration(String key) {
        return isNotNull(cfgMap)&&cfgMap.containsKey(key) ? cfgMap.get(key) : null;
    }

    /**
     * 根据freemarker配置文件的key值，找到对应的configuration，使用Template渲染指定tpl文件
     * @param configKey freemarker配置key值
     * @param tplName 指定tpl文件（tpl文件需要在对应configu的path下）
     * @param value 渲染tpl文件需要的参数
     * @return
     * @throws ConfigNotFoundException
     */
    public String getTpl(String configKey, String tplName, Object value) throws IOException, TemplateException {
        Configuration cfg = getConfigration(configKey);
        if(isNull(cfg))
            throw new ConfigNotFoundException("指定配置文件"+configKey+"没有找到！" );
        Template template = cfg.getTemplate(tplName);
        StringWriter writer = new StringWriter();
        try {
            template.process(value, writer);
            return writer.toString();
        } finally {
            writer.close();
        }
    }
}
