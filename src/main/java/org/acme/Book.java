package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Book extends PanacheEntity {

    public String name;
    public Integer publicationYear;
}
