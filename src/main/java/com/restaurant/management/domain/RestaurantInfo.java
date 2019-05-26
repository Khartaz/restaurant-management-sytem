package com.restaurant.management.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_info")
@Audited
public class RestaurantInfo extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantAddress restaurantAddress;

    public RestaurantInfo() {
    }

    public RestaurantInfo(Long id, Long createdAt, Long updatedAt, String createdByUserId,
                          String updatedByUserId, String name, RestaurantAddress restaurantAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.name = name;
        this.restaurantAddress = restaurantAddress;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantAddress getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(RestaurantAddress restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

}
