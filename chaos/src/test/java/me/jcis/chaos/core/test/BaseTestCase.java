package me.jcis.chaos.core.test;

import me.jcis.chaos.core.config.ChaosConfig;
import me.jcis.chaos.core.constant.EnvConstant;
import me.jcis.chaos.core.freemarker.LazyFreeMarker;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/10/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class BaseTestCase {
    @BeforeClass
    public static void init(){
        URL url = BaseTestCase.class.getResource(EnvConstant.cleanPath(EnvConstant.ActiveConf));
        try {
            File file = new File(url.toURI()).getParentFile();
            ChaosConfig.load(file.getAbsolutePath().replaceFirst("class","test-classes"));
        }catch (Exception e){

        }

    }

    private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public DateFormat dateFormat = new SimpleDateFormat(this.TIME_FORMAT);

    protected LazyFreeMarker initLazyFreeMarker(){

        File file = new File("src\\test\\resources\\freemarker\\freemarkerConfig.conf");
        Resource resource = new FileSystemResource(file);
        LazyFreeMarker lazyFreeMarker = LazyFreeMarker.getInstance(resource);
//        lazyFreeMarker.setConfig(file);
//        lazyFreeMarker.init();
        return lazyFreeMarker;
    }

    protected void print(Object value) {
        System.out.print(dateFormat.format(new Date()) + "------\n");
        System.out.print(value);
        System.out.println("\n");
    }

    @Test
    public void testLogger(){
        print("success");
    }
}
