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

    @Column(name = "phone")
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CompanyAddress companyAddress;

    public Company() {
    }

    public Company(Long id, Long createdAt, Long updatedAt, String createdByUserId,
                   String updatedByUserId, String name, String phone, CompanyAddress companyAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CompanyAddress getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(CompanyAddress companyAddress) {
        this.companyAddress = companyAddress;
    }

}
