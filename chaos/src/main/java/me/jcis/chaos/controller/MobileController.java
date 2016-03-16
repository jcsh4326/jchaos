package me.jcis.chaos.controller;

import com.alibaba.fastjson.JSON;
import me.jcis.chaos.core.log.BaseLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by shuyuanhao on 16/3/7.
 */
@Controller
@RequestMapping(value = "m")
public class MobileController extends BaseLogger {

    /**
     * 获取文章列表，从e时间到s时间降序的n条文章。
     * 当e时间不存在时，默认当前时间为e时间，
     * 当e时间到s时间不足n条文章时，s时间存在则不继续查找，s时间不存在则继续查找以补全（贪婪）
     * @param s 开始时间（不重要）
     * @param e 结束时间
     * @return json
     */
    @RequestMapping(value = "arts")
    @ResponseBody
    public Object getArticles(@RequestParam(required = false) String s,
                              @RequestParam(required = false) String e,
                              @RequestParam(required = false, defaultValue = "10") int n){
        Date start = null;
        Date end = null;
        try {
            end = isNull(e) ? new Date() : timeFormat.parse(e);
            start = isNull(s) ? start : timeFormat.parse(s);
        } catch (ParseException e1) {
            logger.error(String.format("无法获取准确的时间锚！s:%s, e:%s",s,e));
            return noComment();
        }
        //TODO: test result
        return result(JSON.toJSONString(new ArrayList(IntStream.rangeClosed(0, 10).mapToObj(f->{
            Map map = new HashMap();
            map.put("pid", f);
            map.put("title", "title-test-"+f);
            map.put("abs", "abstract-test-"+f);
            return map;
        }).collect(Collectors.toList()))));
    }

}
