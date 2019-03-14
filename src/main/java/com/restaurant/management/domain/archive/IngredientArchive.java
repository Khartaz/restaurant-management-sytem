package com.restaurant.management.domain.archive;

import javax.persistence.*;

@Entity
@Table(name = "ingredients_archive")
public class IngredientArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public IngredientArchive() {
    }

    public IngredientArchive(String name) {
        this.name = name;
    }

    public IngredientArchive(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
