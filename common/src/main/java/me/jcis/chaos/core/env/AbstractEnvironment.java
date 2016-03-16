package me.jcis.chaos.core.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/8
 */
public class AbstractEnvironment implements ConfEnvironment {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Map<String, Object>> getConfEnvironment() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(getSystemProperties());
        list.add(getSystemEnvironment());
        return list;
    }

    public Map<String, Object> getSystemProperties() {
        Map systemProperties;
        try {
            systemProperties = System.getProperties();
        }
        catch (AccessControlException ex) {
            systemProperties = new ReadOnlySystemAttributesMap() {
                @Override
                protected String getSystemAttribute(String propertyName) {
                    try {
                        return System.getProperty(propertyName);
                    }
                    catch (AccessControlException ex) {
                        if (logger.isInfoEnabled()) {
                            logger.info(format("Caught AccessControlException when " +
                                            "accessing system property [%s]; its value will be " +
                                            "returned [null]. Reason: %s",
                                    propertyName, ex.getMessage()));
                        }
                        return null;
                    }
                }
            };
        }
        return systemProperties;
    }

    public Map<String, Object> getSystemEnvironment() {
        Map<String, ?> systemEnvironment;
        try {
            systemEnvironment = System.getenv();
        }
        catch (AccessControlException ex) {
            systemEnvironment = new ReadOnlySystemAttributesMap() {
                @Override
                protected String getSystemAttribute(String variableName) {
                    try {
                        return System.getenv(variableName);
                    }
                    catch (AccessControlException ex) {
                        if (logger.isInfoEnabled()) {
                            logger.info(format("Caught AccessControlException when " +
                                            "accessing system environment variable [%s]; its " +
                                            "value will be returned [null]. Reason: %s",
                                    variableName, ex.getMessage()));
                        }
                        return null;
                    }
                }
            };
        }
        return (Map<String, Object>) systemEnvironment;
    }

    public void merge(ConfEnvironment parent) {

    }
}
