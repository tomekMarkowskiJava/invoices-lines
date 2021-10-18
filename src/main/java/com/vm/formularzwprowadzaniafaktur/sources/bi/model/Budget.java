package com.vm.formularzwprowadzaniafaktur.sources.bi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "Budżet", schema = "Budżety")
public class Budget {
    @Id
    @Column(name = "Identyfikator")
    private Long id;

    @Column(name = "Obowiązujący")
    private byte valid;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<BudgetElement> budgetElements;

}
