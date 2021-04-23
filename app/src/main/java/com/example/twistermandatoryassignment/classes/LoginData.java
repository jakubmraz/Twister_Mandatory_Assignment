package com.example.twistermandatoryassignment.classes;

public class LoginData {
    private String email;
    private String password;

    public LoginData(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}
}
