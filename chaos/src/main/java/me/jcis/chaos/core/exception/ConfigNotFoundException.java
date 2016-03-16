package me.jcis.chaos.core.exception;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public class ConfigNotFoundException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    public ConfigNotFoundException(String message){
        this.message = message;
    }

    public ConfigNotFoundException(){
        this("配置文件没有找到！");
    }
}
