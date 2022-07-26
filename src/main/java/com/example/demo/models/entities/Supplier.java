package com.example.demo.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity{

    private String name;
    private boolean isImporter;

    public Supplier() {
    }

    public Supplier(String name, boolean isImporter) {
        this.name = name;
        this.isImporter = isImporter;
    }
    @Column(name ="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_importer")
    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
