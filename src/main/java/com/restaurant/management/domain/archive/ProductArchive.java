package com.restaurant.management.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_archive")
public class ProductArchive extends AbstractProduct {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IngredientArchive> ingredients = new ArrayList<>();

    public ProductArchive() {
    }

    public ProductArchive(String uniqueId, String name, String category,
                          Double price, Instant createdAt, List<IngredientArchive> ingredients) {
        super(uniqueId, name, category, price, createdAt);
        this.ingredients = ingredients;
    }


    public List<IngredientArchive> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientArchive> ingredients) {
        this.ingredients = ingredients;
    }
}
