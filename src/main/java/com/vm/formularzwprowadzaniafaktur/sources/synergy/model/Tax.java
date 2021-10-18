package com.vm.formularzwprowadzaniafaktur.sources.synergy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Btwtrs")
@Getter @Setter
public class Tax {
    @Id
    @Column(name = "Btwtrans")
    private String id;
    @Column(name = "btwper")
    private int taxPercent;
    @Column(name = "oms30_0")
    private String description;
    @Column
    @JsonIgnore
    private char crednota;
    @Column
    @JsonIgnore
    private String companyCode;
    @Column (name = "code_iv")
    @JsonIgnore
    private char codeIv;
}
