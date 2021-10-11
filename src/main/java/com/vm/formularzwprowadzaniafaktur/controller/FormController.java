package com.vm.formularzwprowadzaniafaktur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("FormularzWprowadzaniaFaktur/form")
public class FormController {

    @RequestMapping(value = "/")
    String getForm(HttpServletResponse response){
        System.out.println("form");
        response.setHeader("Content-Type","text/html");
        return "/form.html";
    }
}
