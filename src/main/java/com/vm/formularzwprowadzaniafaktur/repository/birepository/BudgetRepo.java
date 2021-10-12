package com.vm.formularzwprowadzaniafaktur.repository.birepository;

import com.vm.formularzwprowadzaniafaktur.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepo extends JpaRepository<Budget,Long> {

    @Query("SELECT DISTINCT b FROM Budget b JOIN b.budgetElements e WHERE e.projectNr LIKE %:projectNr% AND b.valid = 1")
    List<Budget>findAllBudgets(@Param("projectNr")String projectNr);

//    @Query
//    List<Budget>findOtherBudgets
}
