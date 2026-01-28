package com.bhuneshwar.mini_bank.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String phone;
    private String address;

    private String signatureUrl;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUser user;

    public Customer(){}

    public Customer(String phone,String address,AppUser user)
    {
        this.phone=phone;
        this.address=address;
        this.user=user;
    }
    public long getId(){ return id;}

    public String getPhone(){ return phone;}
    public void setPhone(String phone){ this.phone=phone;}

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSignatureUrl() { return signatureUrl; }
    public void setSignatureUrl(String signatureUrl) { this.signatureUrl = signatureUrl; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

}
