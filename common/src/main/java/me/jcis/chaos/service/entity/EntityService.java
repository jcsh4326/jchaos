package me.jcis.chaos.service.entity;

import java.util.List;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/28
 */
public interface EntityService {
    /**
     * 获取指定的Entity Class
     * @param className
     * @return
     */
    Class getEntityClass(String className);

    /**
     * 获取所有的Entity名字
     * @return List<String>
     */
    List getEntityNames();

    /**
     * 获取所有Entity
     * @return List<Entity>
     */
    List getEntities();
}
