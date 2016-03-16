package me.jcis.chaos.core.constant;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/7
 */
public class EnvConstantTest {
    @Test
    public void testCleanPath(){
        Assert.assertEquals("/active.conf", EnvConstant.cleanPath(EnvConstant.ActiveConf));
    }
}
