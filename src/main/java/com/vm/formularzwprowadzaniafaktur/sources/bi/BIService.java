package com.vm.formularzwprowadzaniafaktur.sources.bi;

import com.vm.formularzwprowadzaniafaktur.sources.bi.model.*;
import com.vm.formularzwprowadzaniafaktur.sources.bi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("anotherService")
public class BIService {
    private final ProjectRepo projectRepo;
    private final ItemMuRepo itemMuRepo;
    private final InvoiceRepo invoiceRepo;
    private final InvoiceLineRepo invoiceLineRepo;
    private final BudgetRepo budgetRepo;

    @Autowired
    public BIService( ProjectRepo projectRepo, ItemMuRepo itemMuRepo, InvoiceRepo invoiceRepo, InvoiceLineRepo invoiceLineRepo,BudgetRepo budgetRepo) {
        this.projectRepo = projectRepo;
        this.itemMuRepo = itemMuRepo;
        this.invoiceRepo = invoiceRepo;
        this.invoiceLineRepo = invoiceLineRepo;
        this.budgetRepo = budgetRepo;
    }

    public List<Project> findProjects(String description) {
        return projectRepo.findThirdLevelProjects(description);
    }

    public List<Budget> findBudgets(String projectNr) {
        return budgetRepo.findAllBudgets(projectNr);
    }

    public List<ItemMU> findMu(String projectNumber, String mpk) {
        return itemMuRepo.findItemMuByProjectNumerAndMPK(projectNumber, mpk);
    }

    public int addInvoice(Invoice invoice) {
        int id;
        try {
            Invoice savedInvoice = invoiceRepo.saveAndFlush(invoice);
            id = savedInvoice.getId();
        } catch (Exception e) {
            id = 0;
        }
        return id;
    }

    public int addInvoiceLine(InvoiceLine invoiceLine){
        int id;
        try {
            System.out.println("POST");
            InvoiceLine savedInvoiceLine = invoiceLineRepo.saveAndFlush(invoiceLine);
            System.out.println(savedInvoiceLine.toString());
            id = savedInvoiceLine.getId();
        } catch (Exception e) {
            id = 0;
        }
        System.out.println(id);
        return id;
    }

    public int findInvoiceIdByRequestId(String requestId){
            Invoice invoice = invoiceRepo.findByRequestId(requestId);
            return invoice.getId();
    }

    public int deleteInvoiceLine(int id){
        return invoiceLineRepo.deleteByInvoiceIdAndLineId(id);
    }

    public List<InvoiceLine> findInvoiceLines(int invoiceId) {
        return invoiceLineRepo.findAllByInvoiceId(invoiceId);
    }


}
