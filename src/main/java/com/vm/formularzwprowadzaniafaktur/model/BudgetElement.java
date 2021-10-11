package com.vm.formularzwprowadzaniafaktur.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "`Element budżetu`",schema = "Budżety")
public class BudgetElement {

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "[Identyfikator budżetu]")
    @JsonBackReference
    private Budget budget;

    @Id
    @Column(name = "[Kod stanowiska kosztowego]")
    private String mpk;

    @Column(name = "[Kod podprojektu]")
    @JsonIgnore
    private String projectNr;

//    @Id
//    @Column(name = "[Identyfikator]")
//    private Long id;

}
