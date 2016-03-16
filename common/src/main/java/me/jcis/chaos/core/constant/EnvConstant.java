package me.jcis.chaos.core.constant;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
public final class EnvConstant {
    public final static String ChaosConfLocation = "confLocation";
    public final static String PathSep = "path.separator";
    public final static String FileSep = "file.separator";
    public final static String lineSep = "line.separator";
    public final static String UserDir = "user.dir";
    public final static String UserHome = "user.home";
    public final static String DefaultHome = "chaos.home";
    public final static String CatalinaBase = "catalina.base";
    public final static String CatalinaHome = "catalina.home";
    /**
     * System.properties里的配置文件路径
     */
    public final static String ChaosHome = "CHAOS_HOME";
    public final static String ChaosConf = "CHAOS_CONF";
    /**
     * 当前环境使用的系统
     */
    public final static String ActiveConf = "active.conf";
    public final static String Conf_Prex = "conf:/";
    public final static String Default = "default";
    public final static String[] DefaultConfPath = new String[]{"chaos","sys"};

    public final static String getFileSep(){
        return System.getProperty(EnvConstant.FileSep).equals("\\") ? "\\\\" : System.getProperty(EnvConstant.FileSep);
    }

    public final static String joinFilePath(String... path) {
        return StringUtils.join(path, getFileSep());
    }

    public static String cleanPath(String... path) {
        String res = me.jcis.chaos.utils.StringUtils.cleanPath(joinFilePath(path));
        return res.startsWith("/") ? res : "/" + res;
    }
}
