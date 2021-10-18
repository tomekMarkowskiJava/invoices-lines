package com.vm.formularzwprowadzaniafaktur.controller;

import com.vm.formularzwprowadzaniafaktur.sources.bi.BIService;
import com.vm.formularzwprowadzaniafaktur.sources.bi.model.*;
import com.vm.formularzwprowadzaniafaktur.sources.synergy.SynergyService;
import com.vm.formularzwprowadzaniafaktur.sources.synergy.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FormRestController {
    private final BIService biService;
    private final SynergyService synergyService;

    @Autowired
    public FormRestController(BIService biService, SynergyService synergyService) {
        this.biService = biService;
        this.synergyService = synergyService;
    }

    @GetMapping(value = "/project={description}", produces = "application/json")
    List<Project> findProjects(@PathVariable String description) {
        return biService.findProjects(description);
    }

    @GetMapping(value = "/mpk/{projectNr}", produces = "application/json")
    List<Budget> findBudgets(@PathVariable String projectNr) {
        return biService.findBudgets(projectNr);
    }

    @GetMapping(value = "/mu/{projectNr}/{mpk}", produces = "application/json")
    List<ItemMU> findMu(@PathVariable String projectNr, @PathVariable String mpk) {
        return biService.findMu(projectNr, mpk);
    }

    @GetMapping(value = "/invoice/{requestId}", produces = "application/json")
    List<InvoiceLine> findInvoiceLines(@PathVariable String requestId) {
        int invoiceId = biService.findInvoiceIdByRequestId(requestId);
        return biService.findInvoiceLines(invoiceId);
    }

    @GetMapping(produces = "application/json")
    List<Tax> findTaxes() {
        return synergyService.findTaxes();
    }


    @PostMapping(consumes = "application/json")
    int addInvoice(@RequestBody Invoice invoice) {
        return biService.addInvoice(invoice);
    }

    @PostMapping(value = "/addline/{requestId}", consumes = "application/json")
    int addInvoiceLine(@RequestBody InvoiceLine invoiceLine, @PathVariable String requestId) {
        invoiceLine.setInvoiceId(biService.findInvoiceIdByRequestId(requestId));
        return biService.addInvoiceLine(invoiceLine);
    }

    @DeleteMapping
    int deleteLine(@RequestParam("id") int id, @RequestParam("requestId") String requestId) {
        return biService.deleteInvoiceLine(id);
    }
}
