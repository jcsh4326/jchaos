package me.jcis.chaos.core.constant;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public final class FreeMarker {
    public static enum ConfigTag {
        version, encoding, path
    }

    public static enum ConfigType {
        config,groovy
    }

    public static enum Version {
        VERSION_2_3_23("2.3.23");
        private String value;
        Version(String value){
            this.value = value;
        }

        public static freemarker.template.Version getVersion(Version version){
            return new freemarker.template.Version(version.value);
        }
    }
}
