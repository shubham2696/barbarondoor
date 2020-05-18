package com.example.shubham.barbar;

import java.io.Serializable;

/**
 * Created by shubham on 11/5/2017.
 */

public class DetailModel  {

    /*  Model class for List and Recycler Items  */
    private String service_name, price,contact,date,time;

    public DetailModel(String service_name, String price) {
        this.service_name = service_name;
        this.price = price;
    }

    public String getService() {
        return service_name;
    }

    public String getPrice() {
        return price;
    }

    public DetailModel(String service_name, String price, String contact, String date, String time) {
        this.service_name = service_name;
        this.price = price;
        this.contact = contact;
        this.date = date;
        this.time = time;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setService(String service_name) {
        this.service_name = service_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

