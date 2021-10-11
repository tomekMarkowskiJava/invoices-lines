package com.vm.formularzwprowadzaniafaktur.controller;

import com.vm.formularzwprowadzaniafaktur.FormService;
import com.vm.formularzwprowadzaniafaktur.model.Budget;
import com.vm.formularzwprowadzaniafaktur.model.ItemMU;
import com.vm.formularzwprowadzaniafaktur.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("FormularzWprowadzaniaFaktur/api")
public class FormRestController {
    private FormService formService;

    @Autowired
    public FormRestController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping(value = "/project={description}", produces = "application/json")
    List<Project> findProjects(@PathVariable String description) {
        return formService.findProjects(description);
    }

    @GetMapping(value = "/mpk/{projectNr}", produces = "application/json")
    List<Budget> findBudgets(@PathVariable String projectNr) {
        return formService.findBudgets(projectNr);
    }

    @GetMapping(value = "/mu/{projectNr}/{mpk}", produces = "application/json")
    List<ItemMU> findMu(@PathVariable String projectNr, @PathVariable String mpk){
        return formService.findMu(projectNr,mpk);
    }

    @GetMapping(value = "/form/")
    String getForm(){
        return "static/form.html";
    }
}
