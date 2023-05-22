package com.es.nasviazi.model;

import jakarta.persistence.*;

@Entity(name = "Authority")
@Table(name = "authorities")
public class Authority {
    @Id
    private Long id;

    @Column(unique = true)
    private String authority;

    public Authority() {

    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
