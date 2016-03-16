package me.jcis.chaos.core.groovy;

import com.alibaba.fastjson.JSON;
import freemarker.template.TemplateException;
import junit.framework.Assert;
import me.jcis.chaos.core.constant.Charset;
import me.jcis.chaos.core.freemarker.LazyFreeMarker;
import me.jcis.chaos.core.reflect.BaseInvoker;
import me.jcis.chaos.core.test.BaseTestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/28
 */
public class LazyGroovyClassTest extends BaseTestCase {
    @Autowired
    private LazyFreeMarker lazyFreeMarker;

    @Autowired
    private LazyGroovyClass lazyGroovyClass;

    @Test
    public void testGetClazz(){
        File file = new File("src\\test\\resources\\groovy\\person1.json");
        Class clazz = null;
        Map person = null;
        try {
            person = JSON.parseObject(IOUtils.toString(file.toURI(), Charset.CHARSET_UTF_8), Map.class);
            clazz = lazyGroovyClass.getClazz((String) person.get("classType"),lazyFreeMarker,person);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        Class clazz1 = null;
        try {
            clazz1 = lazyGroovyClass.getClazz((String) person.get("classType"),lazyFreeMarker,person);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(clazz, clazz1);
    }

    @Test
    public void testGetClazz0(){
        File file = new File("src\\test\\resources\\groovy\\person1.json");
        Class clazz = null;
        try {
            Map person = JSON.parseObject(IOUtils.toString(file.toURI(), Charset.CHARSET_UTF_8), Map.class);
            clazz = lazyGroovyClass.getClazz((String) person.get("classType"),lazyFreeMarker,person);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        /**
         * 关于parseObject，如果set的返回值不是void，将无法通过JSON.parseObject赋值
         */
        Object instance = JSON.parseObject("{\"sex\":\"妖\",\"age\":16}", clazz);
        BaseInvoker baseInvoker = new BaseInvoker();
        try {
            Assert.assertEquals(baseInvoker.invoker(instance, "getSex"), "妖");
            Assert.assertEquals(baseInvoker.invoker(instance, "getAge"), 16);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetClazz1(){
        File file = new File("src\\test\\resources\\groovy\\person1.json");
        Class clazz = null;
        try {
            Map person = JSON.parseObject(IOUtils.toString(file.toURI(), Charset.CHARSET_UTF_8), Map.class);
            clazz = lazyGroovyClass.getClazz((String) person.get("classType"),lazyFreeMarker,person);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        BaseInvoker baseInvoker = new BaseInvoker();
        try {
            String sex = "女";
            Object instance = clazz.newInstance();
            Object obj = baseInvoker.invoker(instance, "getSex");
            Assert.assertEquals(obj, "男");
            baseInvoker.invoker(instance, "setSex", sex);
            obj = baseInvoker.invoker(instance, "getSex");
            Assert.assertEquals(obj, sex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
