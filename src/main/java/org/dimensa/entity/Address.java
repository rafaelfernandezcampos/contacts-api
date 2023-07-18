package org.dimensa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "cep")
    private String cep;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    public Address(String street, Integer number, String cep, Contact contact) {
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.contact = contact;
    }

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public String getCep() {
        return cep;
    }
}
