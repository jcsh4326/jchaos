package me.jcis.chaos.core.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * 基础接口
 * 用于通过反射调用某类的某个接口
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/10/29
 */
public interface MethodInvoker {
    /**
     * 根据类名和方法名，使用方法所需参数，获得方法返回的结果
     * @param className 类名
     * @param methodName 方法名
     * @param parameters 方法所需参数
     * @return 方法结果
     */
    Object invoker(String className, String methodName, Object... parameters) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * 根据方法名，调用类实例的该方法，使用法所需参数，获得方法返回结果
     * @param classInstance 类实例
     * @param methodName 方法名
     * @param parameters 方法所需参数
     * @return 方法结果
     */
    Object invoker(Object classInstance, String methodName, Object... parameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
