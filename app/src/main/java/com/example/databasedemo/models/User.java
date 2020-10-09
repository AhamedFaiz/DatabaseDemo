package com.example.databasedemo.models;

public class User {
    private String username;
    private String email;
    private String password;
    private String mobileNumber;
    private String userID;

    public User(String username, String email, String password, String mobileNumber, String userID) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.userID = userID;
    }

    public User() {
    }

    public User(String username, String email, String password, String mobileNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
