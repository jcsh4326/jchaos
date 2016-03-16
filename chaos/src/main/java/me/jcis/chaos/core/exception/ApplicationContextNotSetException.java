package me.jcis.chaos.core.exception;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/25
 */
public class ApplicationContextNotSetException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    public ApplicationContextNotSetException(String message){
        this.message = message;
    }

    public ApplicationContextNotSetException(){
        this("ApplicationContext未设定！");
    }
}
