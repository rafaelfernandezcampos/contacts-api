package org.dimensa.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth")
    private LocalDateTime birth;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy="contact", fetch = FetchType.EAGER)
    @Fetch(value= FetchMode.SELECT)
    private List<Address> addressList = new ArrayList<Address>();

    public Contact(String name, String email, String phone, LocalDateTime birth) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birth = birth;
    }

    public Contact() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirth(LocalDateTime birth) {
        this.birth = birth;
    }

    public void setAddressList(List addressList) {
        this.addressList = addressList;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public long getId() {
        return id;
    }
}
