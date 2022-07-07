package com.hyperskill.webquizengine.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class UserCreationDTO {

    @Email
    private String email;

    @Length(min = 5)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}