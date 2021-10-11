package com.vm.formularzwprowadzaniafaktur;

import com.vm.formularzwprowadzaniafaktur.model.Budget;
import com.vm.formularzwprowadzaniafaktur.model.ItemMU;
import com.vm.formularzwprowadzaniafaktur.model.Project;
import com.vm.formularzwprowadzaniafaktur.repository.birepository.BudgetRepo;
import com.vm.formularzwprowadzaniafaktur.repository.birepository.ItemMuRepo;
import com.vm.formularzwprowadzaniafaktur.repository.birepository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {
    private BudgetRepo budgetRepo;
    private ProjectRepo projectRepo;
    private ItemMuRepo itemMuRepo;

    @Autowired
    public FormService(BudgetRepo budgetRepo, ProjectRepo projectRepo, ItemMuRepo itemMuRepo) {
        this.budgetRepo = budgetRepo;
        this.projectRepo = projectRepo;
        this.itemMuRepo = itemMuRepo;
    }


    public List<Project> findProjects(String description){
        return projectRepo.findThirdLevelProjects(description);
    }

    public List<Budget> findBudgets(String projectNr){
        return budgetRepo.findAllBudgets(projectNr);
    }

    public List<ItemMU> findMu(String projectNumber, String mpk){
        return itemMuRepo.findItemMuByProjectNumerAndMPK(projectNumber,mpk);
    }
}
