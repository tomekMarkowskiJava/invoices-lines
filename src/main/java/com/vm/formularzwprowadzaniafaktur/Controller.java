package com.vm.formularzwprowadzaniafaktur;

import com.vm.formularzwprowadzaniafaktur.model.Budget;
import com.vm.formularzwprowadzaniafaktur.model.Project;
import com.vm.formularzwprowadzaniafaktur.repositories.birepository.BudgetRepo;
import com.vm.formularzwprowadzaniafaktur.repositories.birepository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("FormularzWprowadzaniaFaktur")
public class Controller {
    private ProjectRepo projectRepo;
    private BudgetRepo budgetRepo;

    @Autowired
    public Controller(ProjectRepo projectRepo, BudgetRepo budgetRepo) {
        this.projectRepo = projectRepo;
        this.budgetRepo = budgetRepo;
    }

    @GetMapping(value = "/project={description}",produces = "application/json")
    List<Project> findProjects(@PathVariable String description){
        return projectRepo.findThirdLevelProjects(description);
    }

    @GetMapping(value = "/mpk/projectnr={projectNr}",produces = "application/json")
    List<Budget> findBudgets(@PathVariable String projectNr){
        return budgetRepo.findAllBudgets(projectNr);
    }




}
