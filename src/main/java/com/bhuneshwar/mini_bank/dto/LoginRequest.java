package com.bhuneshwar.mini_bank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Email
    @NotBlank(message = "enter a valid email")
    private String email;

    @NotBlank(message =" enter password")
    private String password;

    public LoginRequest(){}

    public String getEmail(){ return email;}
    public void setEmail(String email){ this.email=email;}

    public String getPassword(){ return password;}
    public void setPassword(String password){ this.password=password;}

}
