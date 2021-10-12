package com.vm.formularzwprowadzaniafaktur;

import com.vm.formularzwprowadzaniafaktur.model.*;
import com.vm.formularzwprowadzaniafaktur.repository.birepository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {
    private final BudgetRepo budgetRepo;
    private final ProjectRepo projectRepo;
    private final ItemMuRepo itemMuRepo;
    private final InvoiceRepo invoiceRepo;
    private final InvoiceLineRepo invoiceLineRepo;

    @Autowired
    public FormService(BudgetRepo budgetRepo, ProjectRepo projectRepo, ItemMuRepo itemMuRepo, InvoiceRepo invoiceRepo, InvoiceLineRepo invoiceLineRepo) {
        this.budgetRepo = budgetRepo;
        this.projectRepo = projectRepo;
        this.itemMuRepo = itemMuRepo;
        this.invoiceRepo = invoiceRepo;
        this.invoiceLineRepo = invoiceLineRepo;
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
            Invoice savedInvoice = invoiceRepo.save(invoice);
            id = savedInvoice.getId();
        } catch (Exception e) {
            id = 0;
        }
        return id;
    }

    public int findInvoiceIdByRequestId(String requestId){
        Invoice invoice = invoiceRepo.findByRequestId(requestId);
        return invoice.getId();
    }

    public int deleteInvoiceLine(int invoiceId, int lineId){
        return invoiceLineRepo.deleteByInvoiceIdAndLineId(invoiceId,lineId);
    }
}
