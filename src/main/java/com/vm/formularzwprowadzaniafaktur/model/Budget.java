package com.vm.formularzwprowadzaniafaktur.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
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
