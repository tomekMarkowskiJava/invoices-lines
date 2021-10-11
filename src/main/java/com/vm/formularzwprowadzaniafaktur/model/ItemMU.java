package com.vm.formularzwprowadzaniafaktur.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class ItemMU {

    @Id
    private Long id;
    @Column(name = "[Opis towaru]")
    private String description;
    @Column(name = "[Kod towaru]")
    private String itemCode;
}
