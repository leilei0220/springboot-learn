package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author leilei
 */
public class User {
    private Long id;

    private String username;

    private String cardId;

    private List<RealEseate> realEseates;

    public User(String username, String cardId) {
        this.username = username;
        this.cardId = cardId;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public List<RealEseate> getRealEseates() {
        return realEseates;
    }

    public void setRealEseates(List<RealEseate> realEseates) {
        this.realEseates = realEseates;
    }
}