package me.jcis.chaos.utils;

import me.jcis.chaos.core.test.BaseTestCase;
import org.junit.Test;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/25
 */
public class UUIDGeneratorTest extends BaseTestCase {
    @Test
    public void testGenerate(){
        String uuid = UUIDGenerator.generate();
        print(uuid);
    }
}
