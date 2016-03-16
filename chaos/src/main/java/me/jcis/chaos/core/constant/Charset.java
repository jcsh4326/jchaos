package me.jcis.chaos.core.constant;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/24
 */
public final class Charset {
    /**
     * 默认编码
     */
    public final static String DEFAULT_CHARSET = "UTF-8";

    public final static java.nio.charset.Charset DefaultCharset = java.nio.charset.Charset.forName(DEFAULT_CHARSET);

    /**
     * utf-8 charset
     */
    public final static java.nio.charset.Charset CHARSET_UTF_8 = java.nio.charset.Charset.forName(DEFAULT_CHARSET);
}
