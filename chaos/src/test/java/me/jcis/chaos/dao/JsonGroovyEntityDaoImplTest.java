package me.jcis.chaos.dao;

import com.alibaba.fastjson.JSON;
import me.jcis.chaos.core.constant.Charset;
import me.jcis.chaos.core.entity.Result;
import me.jcis.chaos.core.test.BaseTestCase;
import me.jcis.chaos.entity.groovy.GroovyEntity;
import me.jcis.chaos.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
public class JsonGroovyEntityDaoImplTest extends BaseTestCase {
    @Autowired
    private GroovyEntityDao groovyEntityDao;

    @Test
    public void testSetGroovyEntityVars(){
        String doubleNameEntity = "{\n" +
                "    \"className\":\"Man\",\n" +
                "    \"dataSource\":\"0\",\n" +
                "    \"classAlias\":\"男性\",\n" +
                "    \"fields\":[\n" +
                "      {\n" +
                "        \"name\":\"age\",\n" +
                "        \"alias\":\"年龄\",\n" +
                "        \"modifier\":\"private\",\n" +
                "        \"getter\": true,\n" +
                "        \"setter\": true,\n" +
                "        \"type\": \"int\",\n" +
                "        \"defaultValue\": \"\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
        Result dbNameR = groovyEntityDao.setGroovyEntityVars(doubleNameEntity);
        Result expected = new Result("entity[Man]已经存在！");
        Assert.assertEquals(expected.toString(),dbNameR.toString());
        String entity = "{\n" +
                "    \"className\":\"Dogge\",\n" +
                "    \"dataSource\":\"0\",\n" +
                "    \"classAlias\":\"男性\",\n" +
                "    \"fields\":[\n" +
                "      {\n" +
                "        \"name\":\"age\",\n" +
                "        \"alias\":\"年龄\",\n" +
                "        \"modifier\":\"private\",\n" +
                "        \"getter\": true,\n" +
                "        \"setter\": true,\n" +
                "        \"type\": \"int\",\n" +
                "        \"defaultValue\": \"\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
        Result r = groovyEntityDao.setGroovyEntityVars(entity);
        Assert.assertEquals(r.toString(),new Result(true,"").toString());
    }

    @Test
    public void testGetGroovyEntityNames(){
        List<String> names = new ArrayList<String>();
        try {
            File file = FileUtils.getFile("${CHAOS_HOME}/groovy/clazzs/entities.json");
            List<GroovyEntity> list = JSON.parseArray(IOUtils.toString(file.toURI(), Charset.CHARSET_UTF_8), GroovyEntity.class);
            for(GroovyEntity groovyEntity : list){
                names.add(groovyEntity.getClassName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Assert.assertArrayEquals(names.toArray(), groovyEntityDao.getGroovyEntityNames().toArray());
    }
}
