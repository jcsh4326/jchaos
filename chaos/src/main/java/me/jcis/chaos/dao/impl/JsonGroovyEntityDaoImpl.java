package me.jcis.chaos.dao.impl;

import com.alibaba.fastjson.JSON;
import me.jcis.chaos.core.ConfigClass;
import me.jcis.chaos.core.constant.Charset;
import me.jcis.chaos.core.entity.Result;
import me.jcis.chaos.core.log.BaseLogger;
import me.jcis.chaos.dao.GroovyEntityDao;
import me.jcis.chaos.entity.groovy.GroovyEntity;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
public class JsonGroovyEntityDaoImpl extends BaseLogger implements GroovyEntityDao, ConfigClass {
    private List<GroovyEntity> config;
    private URI configURI;

    /**
     * 在配置文件中插入一个entity的配置
     *
     * @param entity entity完整配置的JSONString
     * @return
     */
    public Result setGroovyEntityVars(String entity) {
        String message = "";
        try {
            GroovyEntity groovyEntity = JSON.parseObject(entity, GroovyEntity.class);

            if(getGroovyEntityNames().contains(groovyEntity.getClassName())){
                return new Result(String.format("entity[%s]已经存在！", groovyEntity.getClassName()));
            }

            File configFile = new File(this.configURI);
            if(configFile.exists()){
                this.config.add(groovyEntity);
                FileOutputStream fileOutputStream = null;
                BufferedOutputStream bufferedOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(configFile, false);
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    bufferedOutputStream.write(JSON.toJSONString(this.config).getBytes(Charset.DefaultCharset));
                    bufferedOutputStream.flush();
                    return new Result(true,"");
                }catch (Exception e){
                    message = String.format("新的配置已缓存，但写入groovyClazz配置文件时发生错误【%s】",e.getLocalizedMessage());
                    logger.error(message);
                }finally {
                    fileOutputStream.close();
                    bufferedOutputStream.close();
                }
            }else{
                message = "groovyClazz配置文件不存在！";
                logger.error(message);
            }
        }catch (Exception e){
            message = String.format("错误的JSON字符串，无法转换成GroovyEntity。错误原因【%s】",e.getLocalizedMessage());
            logger.error(message);
        }
        return new Result(message);
    }

    /**
     * 根据类名获得使用freemarker渲染该类所需的参数
     * 包括该类的类名、字段、字段类型等
     * @param name 类名
     * @return Map
     */
    public GroovyEntity getGroovyEntityVars(String name) {
        GroovyEntity vars = null;
        for(GroovyEntity map : config){
            // 类名不区分大小写
            if(name.equals(map.getClassName())){
                // 存在类的配置
                vars = map;
                break;
            }
        }
        return vars;
    }

    /**
     * 获取所有Entity的名称
     *
     * @return
     */
    public List getGroovyEntityNames() {
        List<String> names = new ArrayList<String>();
        for(GroovyEntity map : config){
            if(!names.contains(map.getClassName()))
                names.add(map.getClassName());
        }
        return names;
    }

    /**
     * 获取所有的Entity
     *
     * @return List<GroovyEntity>
     */
    public List getGroovyEntities() {
        return this.config;
    }

    public void setConfig(Resource config) {
        try {
            this.configURI = config.getURI();
            this.config = JSON.parseArray(IOUtils.toString(config.getURI(), Charset.CHARSET_UTF_8), GroovyEntity.class);
        } catch (IOException e) {
            logger.error("获取配置文件失败，失败原因：【{}】", e.getLocalizedMessage());
        }
    }

//    public void setConfig(File file) {
//        try {
//            config = JSON.parseArray(IOUtils.toString(file.toURI(), Charset.CHARSET_UTF_8), Map.class);
//        } catch (IOException e) {
//            logger.error("获取配置文件失败，失败原因：【{}】", e.getLocalizedMessage());
//        }
//    }
}
