package me.jcis.chaos.core.entity;

import com.alibaba.fastjson.JSON;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/8
 */
public class Result {
    private boolean success;
    private String message;

    /**
     * 错误信息
     * @param message
     */
    public Result(String message){
        this.success = false;
        this.message = message;
    }

    public Result(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String toString(){
        return JSON.toJSONString(this);
    }
}
