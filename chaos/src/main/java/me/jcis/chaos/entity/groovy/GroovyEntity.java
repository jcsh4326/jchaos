package me.jcis.chaos.entity.groovy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/8
 */
public class GroovyEntity {
    private String className;
    private String dataSource;
    private String classAlias;
    private Set<GroovyField> fields = new HashSet<GroovyField>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getClassAlias() {
        return classAlias;
    }

    public void setClassAlias(String classAlias) {
        this.classAlias = classAlias;
    }

    public Set<GroovyField> getFields() {
        return fields;
    }

    public void setFields(Set<GroovyField> fields) {
        this.fields = fields;
    }
}
