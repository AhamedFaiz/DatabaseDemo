package com.example.databasedemo.models;

public class SubmissionForm  {
    private String state;
    private String district;
    private String name;
    private String post;
    private String mobileNumber;
    private String whatsAppNumber;
    private String address;

    public SubmissionForm(String state, String district, String name, String post, String mobileNumber, String whatsAppNumber, String address) {
        this.state = state;
        this.district = district;
        this.name = name;
        this.post = post;
        this.mobileNumber = mobileNumber;
        this.whatsAppNumber = whatsAppNumber;
        this.address = address;
    }

    public SubmissionForm() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWhatsAppNumber() {
        return whatsAppNumber;
    }

    public void setWhatsAppNumber(String whatsAppNumber) {
        this.whatsAppNumber = whatsAppNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
