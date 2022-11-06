package com.example.springInitDemo.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal balance;

//    @ManyToOne(optional = false)
    private User user;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public Account(Long id, BigDecimal balance, User user) {
        this.id = id;
        this.balance = BigDecimal.ZERO;
        this.user = user;
    }
//
//    public Account(BigDecimal balance) {
//        this.balance = balance;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

//    public User getUser() {
//        return user;
//    }
//
    public void setUser(User user) {
        this.user = user;
    }
}
