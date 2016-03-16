package me.jcis.chaos.core.env;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/8
 */
public interface ConfEnvironment {
    List<Map<String, Object>> getConfEnvironment();
    Map<String, Object> getSystemProperties();
    Map<String, Object> getSystemEnvironment();
    void merge(ConfEnvironment parent);
}
