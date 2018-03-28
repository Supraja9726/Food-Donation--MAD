package com.example.supraja.fooddonate;



public class Donor {
    public String foodtype,city,contact,name,state;
    public Donor(){

    }
    public Donor(String bloodgroup, String city, String contact, String name, String state) {
        this.foodtype = bloodgroup;
        this.city = city;
        this.contact = contact;
        this.name = name;
        this.state = state;
    }

    public String getBloodgroup() {
        return foodtype;
    }

    public void setBloodgroup(String bloodgroup) {
        this.foodtype = bloodgroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }






}
