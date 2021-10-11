package com.vm.formularzwprowadzaniafaktur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("FormularzWprowadzaniaFaktur/form")
public class FormController {

    @RequestMapping(value = "/request")
    String getForm(HttpServletResponse response, @RequestParam String id){

        response.setHeader("Content-Type","text/html");
        return "/form.html";
    }
}
