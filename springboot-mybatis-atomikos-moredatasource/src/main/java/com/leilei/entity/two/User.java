package com.leilei.entity.two;

public class User {
    private Long id;

    private String username;

    private String cardId;

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
        this.username = username == null ? null : username.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public User(String username, String cardId) {
        this.username = username;
        this.cardId = cardId;
    }

    public User() {
    }
}