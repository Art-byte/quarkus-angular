package com.artbyte.model;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Hero extends PanacheEntity{
    
    String name;

    public Hero(){}

    public Hero(String name){
        this.name = name;
    }

    public Hero(Long id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
