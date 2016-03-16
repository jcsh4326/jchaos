package me.jcis.chaos.core;

import org.springframework.core.io.Resource;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public interface ConfigClass {
    void setConfig(Resource config);

//    void setConfig(File file);
}
