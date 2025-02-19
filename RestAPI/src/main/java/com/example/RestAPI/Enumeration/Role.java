package com.example.RestAPI.Enumeration;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
