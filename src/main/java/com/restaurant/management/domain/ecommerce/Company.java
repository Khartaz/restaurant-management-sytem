package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Audited
public class Company extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CompanyAddress companyAddress;

    public Company() {
    }

    public Company(Long id, Long createdAt, Long updatedAt, String createdByUserId,
                   String updatedByUserId, String name, CompanyAddress companyAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.name = name;
        this.companyAddress = companyAddress;
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

    public CompanyAddress getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(CompanyAddress companyAddress) {
        this.companyAddress = companyAddress;
    }

}
