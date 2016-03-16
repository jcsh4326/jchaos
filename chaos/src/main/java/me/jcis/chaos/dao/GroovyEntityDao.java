package me.jcis.chaos.dao;

import me.jcis.chaos.core.entity.Result;
import me.jcis.chaos.entity.groovy.GroovyEntity;

import java.util.List;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
public interface GroovyEntityDao {

    /**
     * 在配置文件中插入一个entity的配置
     * @param entity entity完整配置的JSONString
     * @return
     */
    Result setGroovyEntityVars(String entity);

    /**
     * 根据类名获得使用freemarker渲染该类所需的参数
     * 包括该类的类名、字段、字段类型等
     * @param name 类名
     * @return GroovyEntity
     */
    GroovyEntity getGroovyEntityVars(String name);

    /**
     * 获取所有Entity的名称
     * @return List<String>
     */
    List getGroovyEntityNames();

    /**
     * 获取所有的Entity
     * @return List<GroovyEntity>
     */
    List getGroovyEntities();
}
