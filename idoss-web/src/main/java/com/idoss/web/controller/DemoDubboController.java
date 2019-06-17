package com.idoss.web.controller;

import com.idoss.api.dubbo.DemoDubboService;
import com.idoss.web.model.JsonResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiaoxuwang on 2019/6/14.
 */
@Controller
@RequestMapping("/demo")
public class DemoDubboController {

    @Reference(version = "1.0.0", timeout = 90000, check = false)
    private DemoDubboService demoDubboService;

    @GetMapping("/{name}")
    @ResponseBody
    public JsonResult testDubbo(@PathVariable("name")String name) {
        return JsonResult.success(demoDubboService.sayHello(name));
    }

    @GetMapping(value = "/jsp")
    public String test(Model model){
        model.addAttribute("demo","这是测试");
        return "index";
    }

    @GetMapping(value = "/error")
    @ResponseBody
    public JsonResult error(){
        int i = 1/0;
        return JsonResult.success();
    }

}
