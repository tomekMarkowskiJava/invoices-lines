package com.vm.formularzwprowadzaniafaktur.sources.bi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "`OF11/12, linia`", schema = "`Obieg faktur`")
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "[Id faktury]")
    private int invoiceId;

    @Column(name = "[Id linii]")
    private int lineId;

    @Column(name = "[Item ID]")
    private int itemId;

    @Column(name = "[Kod towaru]")
    private String itemCode;

    @Column(name = "[Opis]")
    private String description;

    @Column(name = "[Kod stanowiska kosztowego]")
    private String mpk;

    @Column(name = "[Projekt]")
    private String project;

    @Column(name = "[Nazwa projektu]")
    private String projectName;

    @Column(name = "[Kod projektu]")
    private String projectCode;

    @Column(name = "[Ilość]")
    private double quantity;

    @Column(name = "[Kwota netto]")
    private double netAmount;

    @Column(name = "[VAT]")
    private String tax;

    @Column(name = "[Kwota brutto]")
    private double grossAmount;

    @Column(name = "[Wartość]")
    private double value;

    @Column(name = "[Procent VAT]")
    private String taxPercent;

    @Override
    public String toString() {
        return "InvoiceLine{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", lineId=" + lineId +
                ", itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", mpk='" + mpk + '\'' +
                ", project='" + project + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", quantity=" + quantity +
                ", netAmount=" + netAmount +
                ", tax='" + tax + '\'' +
                ", grossAmount=" + grossAmount +
                ", value=" + value +
                ", taxPercent='" + taxPercent + '\'' +
                '}';
    }
}
