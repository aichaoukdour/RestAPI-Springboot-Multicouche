package com.example.RestAPI.dto;

import java.io.Serializable;

import com.example.RestAPI.Enumeration.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

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

    @NotNull(message = "Le rôle est obligatoire")
    @JsonProperty("role")
    private RoleEnum role;

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

}
