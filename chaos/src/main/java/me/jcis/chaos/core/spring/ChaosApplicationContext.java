package me.jcis.chaos.core.spring;

import me.jcis.chaos.core.exception.BeanNotFoundException;
import me.jcis.chaos.core.exception.ApplicationContextNotSetException;
import me.jcis.chaos.core.log.BaseLogger;
import me.jcis.chaos.core.reflect.BaseInvoker;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/25
 */
public final class ChaosApplicationContext extends BaseLogger implements ApplicationContextAware {

    private static ApplicationContext context;

    private static BaseInvoker baseInvoker = new BaseInvoker();

    private final static boolean isContextValid(){
        return null==context;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public final static ApplicationContext getContext(){
        return context;
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Allows for specifying explicit constructor arguments / factory method arguments,
     * overriding the specified default arguments (if any) in the bean definition.
     * @param beanName the name of the bean to retrieve
     * @param args arguments to use if creating a prototype using explicit arguments to a
     * static factory method. It is invalid to use a non-null args value in any other case.
     * @return an instance of the bean
     * @throws ApplicationContextNotSetException
     */
    public final static Object getBean(String beanName, Object... args){
        if(!isContextValid()){
            throw new ApplicationContextNotSetException();
        }
        return context.getBean(beanName, args);
    }

    /**
     * 手动创建Bean
     * @param clazz
     * @return
     * @throws ApplicationContextNotSetException
     */
    public final static Object createBean(Class<?> clazz) {
        if(!isContextValid()){
            throw new ApplicationContextNotSetException();
        }
        return context.getAutowireCapableBeanFactory().createBean(clazz);
    }

    /**
     * 手动注入已经存在的bean
     * @param bean
     * @throws ApplicationContextNotSetException
     */
    public final static void registerBean(Object bean) {
        if(!isContextValid()){
            throw new ApplicationContextNotSetException();
        }
        context.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    /**
     * 调用
     * @param beanName
     * @param methodName
     * @param args
     * @return
     * @throws ApplicationContextNotSetException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BeanNotFoundException
     */
    public final static Object invoker(String beanName, String methodName, Object... args) throws ApplicationContextNotSetException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object bean = getBean(beanName, args);
        if(null==bean){
            throw new BeanNotFoundException(beanName+"的bean没有找到！");
        }
        return baseInvoker.invoker(bean, methodName, args);
    }
}
