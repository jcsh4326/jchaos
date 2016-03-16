package me.jcis.chaos.service.entity.impl;

import com.alibaba.fastjson.JSON;
import freemarker.template.TemplateException;
import me.jcis.chaos.core.freemarker.LazyFreeMarker;
import me.jcis.chaos.core.groovy.LazyGroovyClass;
import me.jcis.chaos.dao.GroovyEntityDao;
import me.jcis.chaos.entity.groovy.GroovyEntity;
import me.jcis.chaos.service.entity.ConfigurableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/28
 */
@Service
public class EntityServiceImpl implements ConfigurableEntityService {

    /**
     * 在groovyConfig配置文件中的key值
     */
    private final static String configKey = "entity";

    @Autowired
    private LazyGroovyClass lazyGroovyClass;

    @Autowired
    private LazyFreeMarker lazyFreeMarker;

    @Autowired
    private GroovyEntityDao groovyEntityDao;

    public Class getEntityClass(String className) {
        // 获得该类的类名、字段及字段类型
        GroovyEntity vars = groovyEntityDao.getGroovyEntityVars(className);
        try {
            // freemarker渲染时接受的参数需要是Map形式
            Map fmVars = JSON.parseObject(JSON.toJSONString(vars), Map.class);
            // 渲染成具体的EntityCLass
            return lazyGroovyClass.getClazz(configKey, lazyFreeMarker, vars);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有的Entity名字
     *
     * @return
     */
    public List getEntityNames() {
        return groovyEntityDao.getGroovyEntityNames();
    }

    /**
     * 获取所有Entity
     *
     * @return List<Entity>
     */
    public List getEntities() {
        return groovyEntityDao.getGroovyEntities();
    }

    /**
     * 新建一个entity的groovy配置
     */
    public void configEntity(String entity) {

    }
}
