package me.jcis.chaos.utils;

import junit.framework.Assert;
import me.jcis.chaos.core.constant.EnvConstant;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/8
 */
public class FileUtilsTest {
    @Test
    public void testGetFile(){
        String path = "${TEST_HOME}/active.conf";
        URL url = this.getClass().getResource(EnvConstant.cleanPath(EnvConstant.ActiveConf));
        try {
            File file = new File(url.toURI()).getParentFile();
            System.setProperty("TEST_HOME", file.toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            File file = FileUtils.getFile(path);
            Assert.assertEquals(System.getProperty("TEST_HOME")+"active.conf",file.toURI().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
