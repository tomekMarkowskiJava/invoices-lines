package com.vm.formularzwprowadzaniafaktur.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "`Lista projektów v001`", schema = "`Projekty`")
public class Project {
    @Id
    @Column(name = "[Kod projektu]")
    String id;
    @Column(name = "[Kod projektu]",insertable=false, updatable=false)
    String projectNr;
    @Column(name = "[Nazwa projektu]")
    String description;
    @Column
    char status;
}
