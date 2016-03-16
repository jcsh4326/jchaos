package me.jcis.chaos.utils;

import freemarker.template.TemplateException;
import groovy.lang.GroovyClassLoader;
import me.jcis.chaos.core.freemarker.LazyFreeMarker;
import me.jcis.chaos.core.test.BaseTestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/25
 */
public class GroovyUtilsTest extends BaseTestCase {
    @Autowired
    private LazyFreeMarker lazyFreeMarker;

    @Test
    public void testGetGroovyClassLoader(){
        GroovyClassLoader res = GroovyUtils.getGroovyClassLoader(GroovyUtils.DEFAULT);
        GroovyClassLoader exp = GroovyUtils.getDefaultClassLoader();
        Assert.assertEquals(res, exp);
    }

    @Test
    public void testGetCurrentClassLoader(){
        GroovyClassLoader res = GroovyUtils.getCurrentGroovyClassLoader();
        Thread curThread = Thread.currentThread();
        String key = curThread.getName();
        GroovyClassLoader exp = GroovyUtils.getGroovyClassLoader(key);
        Assert.assertEquals(res, exp);
    }

    @Test
    public void testGetCurrentClassLoader1(){
        GroovyClassLoader res = GroovyUtils.getCurrentGroovyClassLoader();
        GroovyClassLoader exp = GroovyUtils.getCurrentGroovyClassLoader();
        Assert.assertEquals(res, exp);
    }

    @Test
    public void testGenerateClass(){
        File file = new File("src\\test\\resources\\groovy\\Test.groovy");
        try {
            Class clazz = GroovyUtils.generateClass(file);
            Class clazz1 = GroovyUtils.generateClass(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void testGenerateClass1(){
//        initLazyFreeMarker();
        Map args = new HashMap();
        String exp = "Test", res = "";
        args.put("className", exp);

        try {
            Class clazz = GroovyUtils.generateClass(new File(""), "groovy", "TestFtl.ftl", args);
            res = clazz.getName();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(res, "groovy."+exp);
    }

    @Test
    public void testGenerateClass2(){
//        initLazyFreeMarker();
        Map args = new HashMap();
        String exp = "Test", res = "";
        args.put("className", exp);

        try {
            Class clazz = GroovyUtils.generateClass(lazyFreeMarker, "groovy", "TestFtl.ftl", args);
            res = clazz.getName();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(res, "groovy."+exp);
    }
}
