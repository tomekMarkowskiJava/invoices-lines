package com.vm.formularzwprowadzaniafaktur.sources.bi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "`OF11/12 faktury`", schema = "`Obieg faktur`")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Identyfikator")
    private int id;

    @Column(name = "[Identyfikator zadania]")
    private String requestId;

    @Column(name = "[Typ zadania]")
    private int type;

//    @Column(name = "[Warunki płatności]")
//    private int paymentConditions;
//
//    @Column(name = "Waluta")
//    private String valut;
}
