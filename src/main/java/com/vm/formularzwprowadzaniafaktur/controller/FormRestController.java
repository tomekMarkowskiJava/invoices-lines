package com.vm.formularzwprowadzaniafaktur.controller;

import com.vm.formularzwprowadzaniafaktur.FormService;
import com.vm.formularzwprowadzaniafaktur.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FormRestController {
    private final FormService formService;

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

    @GetMapping(value = "/invoice/{requestId}", produces = "application/json")
    List<InvoiceLine> findInvoiceLines(@PathVariable String requestId){
        int invoiceId = formService.findInvoiceIdByRequestId(requestId);
        return formService.findInvoiceLines(invoiceId);
    }

    @GetMapping(value = "/form/")
    String getForm(){
        return "static/form.html";
    }

    @PostMapping(consumes = "application/json")
    int addInvoice(@RequestBody Invoice invoice){
        int id = formService.addInvoice(invoice);
        return id;
    }

    @DeleteMapping
    int deleteLine(@RequestParam("lineId")int lineId, @RequestParam("requestId") String requestId){
        int invoiceId = formService.findInvoiceIdByRequestId(requestId);
        return formService.deleteInvoiceLine(invoiceId,lineId);
    }
}
