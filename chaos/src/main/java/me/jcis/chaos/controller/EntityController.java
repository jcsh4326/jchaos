package me.jcis.chaos.controller;

import com.alibaba.fastjson.JSON;
import me.jcis.chaos.core.log.BaseLogger;
import me.jcis.chaos.service.entity.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
@Controller
@RequestMapping(value = "/entity")
public class EntityController extends BaseLogger {
    @Autowired
    private EntityService entityService;

    @RequestMapping(value = "/list")
    public String getEntityList(Model model){
        List list = entityService.getEntities();
        model.addAttribute("entities", JSON.toJSONString(list));
        return "entity/list";
    }



}
