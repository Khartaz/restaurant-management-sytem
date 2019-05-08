package com.restaurant.management.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_info")
@Audited
public class RestaurantInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public RestaurantInfo() {
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
