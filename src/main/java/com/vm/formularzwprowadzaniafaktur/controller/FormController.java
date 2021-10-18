package com.vm.formularzwprowadzaniafaktur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/form")
public class FormController {

    @RequestMapping(value = "/request")
    String getForm(@RequestParam String id){
//        response.("Content-Type","text/html");
        return "/form.html";
    }
}
