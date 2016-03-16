package me.jcis.chaos.service.entity.test;

import com.alibaba.fastjson.JSON;
import junit.framework.Assert;
import me.jcis.chaos.core.reflect.BaseInvoker;
import me.jcis.chaos.core.test.BaseTestCase;
import me.jcis.chaos.service.entity.EntityService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
public class EntityServiceImplTest extends BaseTestCase {
    @Autowired
    private EntityService entityService;

    @Test
    public void testGetEntityClass(){
        Class clazz = entityService.getEntityClass("Man");
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
}
