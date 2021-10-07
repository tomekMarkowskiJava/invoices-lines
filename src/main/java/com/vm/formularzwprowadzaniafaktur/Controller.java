package com.vm.formularzwprowadzaniafaktur;

import com.vm.formularzwprowadzaniafaktur.model.Project;
import com.vm.formularzwprowadzaniafaktur.repositories.birepository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("FormularzWprowadzaniaFaktur")
public class Controller {
    private ProjectRepository projectRepository;


    @Autowired
    public Controller(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping(value = "/project/{description}",produces = "application/json")
    List<Project> findHumres(@PathVariable String description){
        return projectRepository.findThirdLevelProjects(description);
    }




}
