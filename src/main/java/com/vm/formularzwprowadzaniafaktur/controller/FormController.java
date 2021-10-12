package com.vm.formularzwprowadzaniafaktur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/form")
public class FormController {

    @RequestMapping(value = "/request")
    String getForm(HttpServletResponse response, @RequestParam String id){
        response.setHeader("Content-Type","text/html");
        return "/form.html";
    }
}
