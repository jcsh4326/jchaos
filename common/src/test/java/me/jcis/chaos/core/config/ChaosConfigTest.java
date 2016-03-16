package me.jcis.chaos.core.config;

import junit.framework.Assert;
import me.jcis.chaos.core.constant.EnvConstant;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
public class ChaosConfigTest {
    @Test
    public void testLoad(){
        ChaosConfig.load();
        URL url = this.getClass().getResource(EnvConstant.cleanPath(EnvConstant.ActiveConf));
        try {
            File file = new File(url.toURI()).getParentFile();
            Assert.assertEquals("成功找到的配置文件目录", file.toURI().toString()+"common/conf", System.getProperty("COMMON_HOME"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
