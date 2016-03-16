package me.jcis.chaos.core.freemarker;

import freemarker.template.TemplateException;
import me.jcis.chaos.core.exception.ConfigNotFoundException;
import me.jcis.chaos.core.test.BaseTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public class LazyFreeMarkerTest extends BaseTestCase{

    @Autowired
    private LazyFreeMarker lazyFreeMarker;

    @Test
    public void testGetTpl(){
//        LazyFreeMarker lazyFreeMarker = initLazyFreeMarker();
        Map obj = new HashMap();
        obj.put("val","World");
        String res = "", expect = "Hello,World";
        try {
             res = lazyFreeMarker.getTpl("config", "test.ftl", obj);
        } catch (IOException e) {
            print(e.getLocalizedMessage());
        } catch (TemplateException e) {
            print(e.getLocalizedMessage());
        } catch (ConfigNotFoundException e){
            print(e.getLocalizedMessage());
        }
        Assert.assertEquals(expect, res);
    }
}
