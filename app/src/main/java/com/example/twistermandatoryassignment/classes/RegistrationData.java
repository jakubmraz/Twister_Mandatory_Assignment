package com.example.twistermandatoryassignment.classes;

public class RegistrationData {
   private String username;
   private String email;
   private String password1;
   private String password2;

   public RegistrationData(String username, String email, String password1, String password2){
       this.username = username;
       this.email = email;
       this.password1 = password1;
       this.password2 = password2;
   }

   public String getUsername() {return this.username;}
   public String getEmail() {return this.email;}
   public String getPassword1() {return this.password1;}
   public String getPassword2() {return this.password2;}
}
