package me.jcis.chaos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/22
 */
@Controller
@RequestMapping(value = "statics")
public class StaticController {
    @RequestMapping(value = "module")
    @ResponseBody
    public String getModule(@RequestParam String module){
        return "{{message}}";
    }
}
