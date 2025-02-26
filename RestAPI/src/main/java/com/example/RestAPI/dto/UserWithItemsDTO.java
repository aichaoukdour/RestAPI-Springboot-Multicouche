package com.example.RestAPI.dto;

import java.io.Serializable;
import java.util.List;

import com.example.RestAPI.Enumeration.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWithItemsDTO {

    @NotBlank
    @JsonProperty("name")
    private String userName;

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    @JsonProperty("pwd")
    private String password;

    @Email(message = "L'email n'est pas valide")
    @NotBlank(message = "L'email est obligatoire")
    @JsonProperty("mail")
    private String email;

    @JsonProperty("role")
    private RoleEnum role;

    private List<ItemResponse> items;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }

}