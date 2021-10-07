package com.vm.formularzwprowadzaniafaktur.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Humres {
    @Id
    private Long id;
    @Column(name = "res_id")
    int resId;
    @Column
    String fullname;
}
