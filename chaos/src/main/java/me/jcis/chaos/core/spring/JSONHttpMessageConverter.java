package me.jcis.chaos.core.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import me.jcis.chaos.core.constant.Charset;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/15
 */
public class JSONHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    /**
     * Construct an {@code AbstractHttpMessageConverter} with no supported media types.
     *
     * @see #setSupportedMediaTypes
     */
    public JSONHttpMessageConverter() {
        super(new MediaType("application", "json", Charset.DefaultCharset), new MediaType("application", "*+json", Charset.DefaultCharset));
    }

    /**
     * Indicates whether the given class is supported by this converter.
     *
     * @param clazz the class to test for support
     * @return {@code true} if supported; {@code false} otherwise
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * Abstract template method that reads the actual object. Invoked from {@link #read}.
     *
     * @param clazz        the type of object to return
     * @param inputMessage the HTTP input message to read from
     * @return the converted object
     * @throws java.io.IOException                                                in case of I/O errors
     * @throws org.springframework.http.converter.HttpMessageNotReadableException in case of conversion errors
     */
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(FileCopyUtils.copyToByteArray(inputMessage.getBody()), clazz);
    }

    /**
     * Abstract template method that writes the actual body. Invoked from {@link #write}.
     *
     * @param o             the object to write to the output message
     * @param outputMessage the message to write to
     * @throws java.io.IOException                                                in case of I/O errors
     * @throws org.springframework.http.converter.HttpMessageNotWritableException in case of conversion errors
     */
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        render(o, new OutputStreamWriter(outputMessage.getBody(), Charset.DefaultCharset));
    }

    /**
     * @param obj
     * @param writer
     * @throws java.io.IOException
     */
    private void render(Object obj, Writer writer) throws IOException {
        SerializeWriter out = new SerializeWriter();
        out.config(SerializerFeature.DisableCircularReferenceDetect, true);
        out.config(SerializerFeature.SkipTransientField, true);
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.write(obj);
        try {
            out.writeTo(writer);
            writer.flush();
        } finally {
            out.close();
        }
    }
}
