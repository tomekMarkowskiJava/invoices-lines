package com.vm.formularzwprowadzaniafaktur.repositories.birepository;

import com.vm.formularzwprowadzaniafaktur.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.List;

@Validated
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findPRProjectsByDescriptionContaining(@Size(max=5)String description);

    @Query("SELECT p FROM Project p " +
            "WHERE LENGTH(p.projectNr) > 11 " +
            "AND p.status = 'A' " +
            "AND (p.description LIKE %:description% OR p.projectNr LIKE %:description%)")
    List<Project> findThirdLevelProjects(@Param("description") String description);


}

