package me.jcis.chaos.service.entity;

/**
 * 允许通过此service配置系统需要的entity
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/8
 */
public interface ConfigurableEntityService extends EntityService {
    /**
     * 新建一个entity的groovy配置
     */
    void configEntity(String entity);
}
