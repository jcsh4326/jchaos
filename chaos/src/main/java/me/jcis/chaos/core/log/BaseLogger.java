package me.jcis.chaos.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/10/29
 */
public class BaseLogger {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final static void println(Object log){
        System.out.print(log);
    }

    protected static String dateFormatStr = "yyyy-MM-dd";
    protected static String timeFormatStr = "yyyy-MM-dd HH:mm:ss";

    protected static DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
    protected static DateFormat timeFormat = new SimpleDateFormat(timeFormatStr);

    protected boolean isNull(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNull(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }


    protected boolean isNotNull(Object value) {
        return !isNull(value);
    }

    protected Map result(String msg){
        return result(msg, true);
    }

    protected Map noComment(){
        String msg = "服务器遭到异界生物的袭击，代码君正在奋力反抗！";
        return result(msg, false);
    }

    protected Map result(String msg, boolean suc){
        Map map = new HashMap();
        map.put("suc",suc);
        map.put("msg",msg);
        return map;
    }
}
