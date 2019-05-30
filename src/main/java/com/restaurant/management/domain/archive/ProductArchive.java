package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractProduct;
import com.restaurant.management.domain.RestaurantInfo;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_archive")
@Audited
public class ProductArchive extends AbstractProduct {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IngredientArchive> ingredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public ProductArchive() {
    }

    public ProductArchive(Long createdAt, Long updatedAt,
                          String createdByUserId, String updatedByUserId,
                          Long id, String name, String category,
                          Double price, List<IngredientArchive> ingredients) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, category, price);
        this.ingredients = ingredients;
    }

    public List<IngredientArchive> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientArchive> ingredients) {
        this.ingredients = ingredients;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }
}
