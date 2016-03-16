package me.jcis.chaos.core.reflect;

import me.jcis.chaos.core.log.BaseLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/10/29
 */
public class BaseInvoker extends BaseLogger implements MethodInvoker {

    /**
     * <core>核心方法</core>
     * @param methods
     * @param methodName
     * @param strick
     * @param parameterTypes
     * @return Method
     */
    private Method matchMethod(Method[] methods, String methodName, boolean strick, Class<?>[] parameterTypes){
        Method targetMethod = null;
        for(Method method : methods){
            Class<?>[] paramTypes = method.getParameterTypes();
            if(!method.getName().equals(methodName))
                continue;
            if(paramTypes.length!=parameterTypes.length)
                continue;
            targetMethod = method;
            for(int i=0;i<paramTypes.length;i++){
                if(!parameterTypes[i].equals(parameterTypes[i])){
                    if(strick){
                        targetMethod = null;
                    }else{
                        if(!parameterTypes[i].getSuperclass().equals(paramTypes[i])&&
                                !parameterTypes[i].getSuperclass().equals(paramTypes[i].getSuperclass())){
                            targetMethod = null;
                        }
                    }
                }
            }
            if(targetMethod!=null)
                break;
        }
        return targetMethod;
    }

    /**
     *
     * @param className
     * @param methodName
     * @param strict
     * @param parameters
     * @return Method
     */
    protected Method getMethod0(String className, String methodName, boolean strict, Object... parameters){
        Class<?>[] clazzs = new Class<?>[parameters.length];
        for(int i=0;i<parameters.length;i++){
            clazzs[i] = parameters[i].getClass();
        }
        return getMethod0(className, methodName, strict, clazzs);
    }

    /**
     *
     * @param className
     * @param methodName
     * @param strict
     * @param parameterTypes
     * @return Method
     */
    protected Method getMethod0(String className, String methodName, boolean strict, Class<?>... parameterTypes){
        Class<?> clazz = null;
        try{
            clazz = Class.forName(className);
        }catch (Exception e){

        }
        if(clazz==null){
            return null;
        }
        Method[] methods = clazz.getMethods();
        return matchMethod(methods, methodName, strict, parameterTypes);
    }

    protected Method getMethod(String className, String methodName, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clazz = Class.forName(className);
        return getMethod(clazz, methodName, parameterTypes);
    }

    protected Method getMethod(String className, String methodName, Object... parameters) throws NoSuchMethodException, ClassNotFoundException {
        Class<?>[] clazzs = new Class<?>[parameters.length];
        for(int i=0;i<parameters.length;i++){
            clazzs[i] = parameters[i].getClass();
        }
        return getMethod(className, methodName, clazzs);
    }

    /**
     * 在能直接获得Class的情况下，直接使用Class，避免class.forName()方法报ClassNotFoundException
     * @param clazz
     * @param methodName
     * @param parameters
     * @return
     * @throws NoSuchMethodException
     */
    protected Method getMethod(Class clazz, String methodName, Object... parameters) throws NoSuchMethodException {
        Class<?>[] clazzs = new Class<?>[parameters.length];
        for(int i=0;i<parameters.length;i++){
            clazzs[i] = parameters[i].getClass();
        }
        return getMethod(clazz, methodName, clazzs);
    }

    protected Method getMethod(Class clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getMethod(methodName, parameterTypes);
    }

    protected Object runMethod(String className, String methodName, Object... parameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object object = Class.forName(className).newInstance();
        return runMethod(object, methodName, parameters);
    }

    protected Object runMethod(Object clazzInstance, String methodName, Object... parameters) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Method method = getMethod(clazzInstance.getClass(), methodName, parameters);
        return runMethod(clazzInstance, method, parameters);
    }

    /**
     * All depends on method.invoke(Object obj, Object... args)
     * @param clazzInstance the object the underlying method is invoked from
     * @param method the special method the object invokes
     * @param parameters the arguments used for the method call
     * @return
     * the result of dispatching the method represented by
     * this object on {@code obj} with parameters
     * {@code args}
     */
    protected Object runMethod(Object clazzInstance, Method method, Object... parameters) throws InvocationTargetException, IllegalAccessException {
        Object result = null;
        return method.invoke(clazzInstance, parameters);
    }

    /**
     * 根据类名和方法名，使用方法所需参数，获得方法返回的结果
     * @param className 类名
     * @param methodName 方法名
     * @param parameters 方法所需参数
     * @return 方法结果
     */
    public Object invoker(String className, String methodName, Object... parameters) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return runMethod(className, methodName, parameters);
    }

    /**
     * 根据方法名，调用类实例的该方法，使用法所需参数，获得方法返回结果
     * @param classInstance 类实例
     * @param methodName 方法名
     * @param parameters 方法所需参数
     * @return 方法结果
     */
    public Object invoker(Object classInstance, String methodName, Object... parameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return runMethod(classInstance, methodName, parameters);
    }
}
