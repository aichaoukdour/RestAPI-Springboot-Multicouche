package com.example.RestAPI.Enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum RoleEnum {

    USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER");

    private final String role;

}
