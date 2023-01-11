package com.example.apartmentreservations.models;

public class TokenResponse {
    private String idToken;
    private int id;
    private String role;

    public TokenResponse(String idToken, int id, String role) {
        this.idToken = idToken;
        this.id = id;
        this.role = role;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
