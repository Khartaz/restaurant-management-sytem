package com.restaurant.management.domain.scrumboard;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "check_lists")
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CheckItem> checkItems;

    public CheckList() {
    }

    public CheckList(Long id, String name, List<CheckItem> checkItems) {
        this.id = id;
        this.name = name;
        this.checkItems = checkItems;
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

    public List<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }
}
