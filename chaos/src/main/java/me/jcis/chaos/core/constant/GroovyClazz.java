package me.jcis.chaos.core.constant;

import me.jcis.chaos.core.groovyClazz.AbstractEntity;
import me.jcis.chaos.core.log.BaseLogger;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/28
 */
public class GroovyClazz extends BaseLogger {

    public static enum ConfigTag {
        independent, template
    }

    public static enum GroovyClazzType {
        entity
    }

    public static enum ClazzTag {
        className,dataSource,classAlias,fields,
        name,alias,modifier,getter,setter,type,defaultValue
    }

    public static enum GroovyClazzClassLoader {
        ENTITY("entity", AbstractEntity.class.getClassLoader());
        private String clazzType;
        private ClassLoader cl;
        private GroovyClazzClassLoader(String clazzType, ClassLoader cl){
            this.clazzType = clazzType;
            this.cl = cl;
        }

        public String getClazzType() {
            return clazzType;
        }

        public void setClazzType(String clazzType) {
            this.clazzType = clazzType;
        }

        public ClassLoader getCl() {
            return cl;
        }

        public void setCl(ClassLoader cl) {
            this.cl = cl;
        }

        /**
         * 不区分大小写
         * @param clazzType
         * @return
         */
        public static ClassLoader getClassLoader(String clazzType){
            GroovyClazzClassLoader gccl = getGroovyClazzClassLoader(clazzType);
            return isNullOrEmpty(gccl) ? null : gccl.getCl();
        }

        public static GroovyClazzClassLoader getGroovyClazzClassLoader(String clazzType) {
            for(GroovyClazzClassLoader gccl : GroovyClazzClassLoader.values()){
                if(gccl.getClazzType().equalsIgnoreCase(clazzType)){
                    return gccl;
                }
            }
            return null;
        }
    }

    public static enum From {
        db(0), json(1);
        private int value;
        private From(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }

        public static From getFrom(int value){
            for(From from : From.values()){
                if(from.value==value){
                    return from;
                }
            }
            return json;
        }
    }
}
