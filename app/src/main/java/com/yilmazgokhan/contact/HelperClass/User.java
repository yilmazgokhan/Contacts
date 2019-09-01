package com.yilmazgokhan.contact.HelperClass;

public class User {

    private String email;
    private String name;
    private String password;

    /*User log in*/
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /*User register*/
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
