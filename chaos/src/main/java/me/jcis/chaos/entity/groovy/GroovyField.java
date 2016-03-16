package me.jcis.chaos.entity.groovy;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/8
 */
public class GroovyField {
    private String name;
    private String alias;
    private String modifier;
    private boolean getter;
    private boolean setter;
    private String type;
    private String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public boolean isGetter() {
        return getter;
    }

    public void setGetter(boolean getter) {
        this.getter = getter;
    }

    public boolean isSetter() {
        return setter;
    }

    public void setSetter(boolean setter) {
        this.setter = setter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
