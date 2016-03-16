package me.jcis.chaos.core.groovyClazz;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/28
 */
@MappedSuperclass
public abstract class AbstractEntity implements Entity, Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = "me.jcis.chaos.utils.UUIDGenerator")
    @Column(length = 32)
    private String id;

    private Boolean enabled;

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
