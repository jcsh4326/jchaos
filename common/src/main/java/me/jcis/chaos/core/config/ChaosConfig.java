package me.jcis.chaos.core.config;

import com.alibaba.fastjson.JSON;
import me.jcis.chaos.core.constant.EnvConstant;
import me.jcis.chaos.utils.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2014/02/25
 */
public class ChaosConfig extends PropertiesLoaderSupport {

    private static ChaosConfig instance;

    public static boolean load(final String... paths){
        instance = new ChaosConfig();
        Assert.notNull(paths, "配置文件路径可以是空字符串，但不能为空！");
        return instance.loadConfig(paths);
    }

    private boolean loadConfig(String... paths){
        // 配置文件根目录
        File root = lookForRoot(paths);
        if(null==root)
            root = lookForClassLoaderRoot();
        if(null==root){
            // 包括路径包括项目名称
            String path = lookForSecondaryRoot();
            if(""==path){
                return throwErr();
            }else{
                root = new File(path);
                if(!root.exists()){
                    root.mkdirs();
                    String err = "无法定位配置文件路径，请在系统新建的默认路径【"+path+"】下进行系统配置！";
                    return throwErr(err);
                }
            }


        }
        if(null!=root){
            // 设置配置文件根目录
            return loadChaosConf(root);
        }
        return false;
    }

    private boolean loadChaosConf(File root){
        if(root.exists()){
            String chaosConf = StringUtils.join(new String[]{root.getAbsolutePath(), EnvConstant.ActiveConf}, EnvConstant.getFileSep());
            File activeConf = new File(chaosConf);
            String activeHome = "";
            if(!activeConf.exists())
                activeHome = EnvConstant.Default;
            else{
                try {
                    activeHome = FileUtils.readFileToString(activeConf);
                } catch (IOException e) {
                    logger.error("读取配置文件出错：" + e.getLocalizedMessage());
                    activeHome = EnvConstant.Default;
                }
            }
            String chaosHome = EnvConstant.joinFilePath(root.getAbsolutePath(), activeHome);
            File chaosHomePath = new File(chaosHome);
            chaosHome = chaosHomePath.getAbsolutePath();
            logger.debug("配置文件根目录:"+ chaosHome);
            setSystemProperty(EnvConstant.ChaosHome, chaosHomePath);
            File proj = lookForRootInProperties(EnvConstant.UserDir);
            String projName = getProjName(proj);
            String projHome = EnvConstant.joinFilePath(chaosHome, projName, "conf");
            File projFilePath = new File(projHome);
            projHome = projFilePath.getAbsolutePath();
            logger.debug("项目["+projName+"]配置文件目录:"+projHome);
            setSystemProperty(projName.toUpperCase() + "_HOME", projFilePath);
            return true;
        }
        return false;
    }

    /**
     * 寻找配置文件根目录
     * @param paths 外部传入的路径，如果有多个路径，默认返回第一个存在的路径
     * @return
     */
    private File lookForRoot(String... paths){
        File root = null;
        // 由外部定义
        for(String path : paths){
            root = new File(path);
            if(root.exists())
                return root;
        }
        // 外部定义的路径都不存在，寻找环境变量里设置的路径
        root = lookForRootInProperties(EnvConstant.ChaosHome);
        if(null != root)
            return root;
        // 在系统环境变量中找
        Map<String, String> envs = System.getenv();
        Iterator<Map.Entry<String, String>> iterator = envs.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            if(entry.getKey().equalsIgnoreCase(EnvConstant.ChaosHome)){
                String path = entry.getValue();
                root = new File(path);
                if(root.exists())
                    return root;
                break;
            }
        }
        logger.info("未能在环境变量中找到配置文件根目录，尝试寻找默认的配置文件根目录");
        root = lookForRootInProperties(EnvConstant.CatalinaBase,EnvConstant.CatalinaHome);
        if(null != root)
            return root;
        logger.info("未能找到默认的配置文件根目录！");
        return root;
    }

    /**
     * 在寻找配置文件根目录失败后，在当前项目所在路径的根目录下寻找默认配置文件路径
     * @return
     */
    private String lookForSecondaryRoot(){
        File proj = lookForRootInProperties(EnvConstant.UserDir);
        if(proj.exists()&&proj.isDirectory()){
            // 当前项目所在路径
            String abPath = proj.getAbsolutePath();
            logger.info("当前项目位于【"+abPath+"】");
            String drive = abPath.split(EnvConstant.getFileSep())[0];
//            String confPath = StringUtils.join(EnvConstant.DefaultConfPath, System.getProperty(EnvConstant.FileSep));
            String confPath = StringUtils.join(EnvConstant.DefaultConfPath, EnvConstant.getFileSep());
            logger.info("使用默认路径【"+confPath+"】");
//            String projName = getProjName(proj);
//            confPath = StringUtils.join(new String[]{drive,confPath,projName}, System.getProperty(EnvConstant.FileSep));
            confPath = StringUtils.join(new String[]{drive,confPath}, EnvConstant.getFileSep());
            return confPath;
        }
        return "";
    }

    private File lookForClassLoaderRoot(){
        URL url = this.getClass().getResource(EnvConstant.cleanPath(EnvConstant.ActiveConf));
        if(null!=url){
            try {
                return new File(url.toURI()).getParentFile();
            } catch (URISyntaxException e) {
                logger.info(e.getLocalizedMessage());
            }
        }
        return null;
    }

    private File lookForRootInProperties(String... keys){
        File root = null;
        Properties properties = System.getProperties();
        Enumeration enumeration = properties.propertyNames();
        while(enumeration.hasMoreElements()){
            String name = (String) enumeration.nextElement();
            for(String key : keys){
                if(name.equalsIgnoreCase(key)){
                    String path = (String) properties.get(name);
                    root = new File(path);
                    if(root.exists())
                        return root;
                }
            }
        }
        return root;
    }

    private String setSystemProperty(String key, Object value) {
        String prop = "";
        if(value instanceof File){
            // Spring在获取Resource的时候需要URI
            prop = ((File) value).toURI().toString();
        }else if(value instanceof String){
            prop = (String) value;
        }else{
            try{
                prop = JSON.toJSONString(value);
            }catch (Exception e){
                prop = value.toString();
            }
        }
        logger.info("set system property : " + key + "=" + value);
        return System.setProperty(key, prop);
    }

    private String getProjName(){
        File proj = lookForRootInProperties(EnvConstant.UserDir);
        if(proj.exists()&&proj.isDirectory()){
            return getProjName(proj);
        }
        return "";
    }

    private String getProjName(File proj){
        return proj.getName();
    }

    private boolean throwErr(){
        String err = "无法定位配置文件路径，请检查系统是否正确配置！";
        return throwErr(err);
    }

    private boolean throwErr(String err){
        logger.error(err);
        return false;
    }
}
