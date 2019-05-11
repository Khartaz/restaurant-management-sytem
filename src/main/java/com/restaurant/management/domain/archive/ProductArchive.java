package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractAuditing;
import com.restaurant.management.domain.RestaurantInfo;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_archive")
@Audited
public class ProductArchive extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IngredientArchive> ingredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public ProductArchive() {
    }

    public ProductArchive(Long createdAt, Long updatedAt,
                          String createdBy, String updatedBy,
                          String name, String category,
                          Double price, List<IngredientArchive> ingredients) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
