package me.jcis.chaos.utils;

import me.jcis.chaos.core.env.AbstractEnvironment;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/30
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private final static String prefix = "${";
    private final static String suffix = "}";
    /** URL prefix for loading from the file system: "file:" */
    public static final String FILE_URL_PREFIX = "file:";

    /** URL protocol for a file in the file system: "file" */
    public static final String URL_PROTOCOL_FILE = "file";
    public static void createNewFile(File file) throws IOException {
        if(!file.exists()){
            File parent = file.getParentFile();
            if(parent.exists()){
                file.mkdir();
            }else{
                createNewFile(parent);
            }
        }
    }

    public static File getFile(String filePath) throws FileNotFoundException {
        int preIndex = filePath.indexOf(prefix);
        if( preIndex > -1){
            // 路径以${ 开头
            int sufIndex = filePath.indexOf(suffix);
            String reg = filePath.substring(preIndex+prefix.length(), sufIndex);
            List<Map<String, Object>> confProperties = new AbstractEnvironment().getConfEnvironment();
            for(Map<String, Object> map : confProperties){
                if(map.containsKey(reg)){
                    filePath = filePath.substring(0, preIndex)+(String)map.get(reg)+filePath.substring(sufIndex+suffix.length());
                    return getFile(filePath);
                }
            }
        }
        filePath = StringUtils.cleanPath(filePath);
        File file;
        try {
            URI uri = new URI(filePath);
            Assert.notNull(uri, "Resource URI must not be null");
            if (!URL_PROTOCOL_FILE.equals(uri.getScheme())) {
                throw new FileNotFoundException(
                        " cannot be resolved to absolute file path " +
                                "because it does not reside in the file system: " + uri);
            }
            file = new File(uri.getSchemeSpecificPart());
        } catch (URISyntaxException e) {
            // 不能转成uri，尝试以绝对路径的方式获得
            file = new File(filePath);
        }
        if(!file.exists())
            throw new FileNotFoundException(
                    " cannot be resolved to absolute file path " +
                            "because it does not reside in the file system: " + file.getPath());
        return file;
    }
}
