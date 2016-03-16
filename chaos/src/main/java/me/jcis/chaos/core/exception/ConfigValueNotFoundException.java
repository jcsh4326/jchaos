package me.jcis.chaos.core.exception;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public class ConfigValueNotFoundException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    public ConfigValueNotFoundException(String message){
        this.message = message;
    }

    public ConfigValueNotFoundException(){
        this("配置文件的值没有找到！");
    }
}
