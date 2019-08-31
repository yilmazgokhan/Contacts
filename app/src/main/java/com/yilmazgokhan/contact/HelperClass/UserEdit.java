package com.yilmazgokhan.contact.HelperClass;

public class UserEdit {

    private String name;
    private String phone;
    private String email;
    private String type;
    private String id;

    public UserEdit() { }

    public UserEdit(String name, String phone, String email, String type, String id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.type = type;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }
}
