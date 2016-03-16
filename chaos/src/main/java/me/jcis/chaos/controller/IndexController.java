package me.jcis.chaos.controller;

import me.jcis.chaos.core.log.BaseLogger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/15
 */
@Controller
public class IndexController extends BaseLogger {
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("name","hi");
        return "index";
    }
}
