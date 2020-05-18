package com.example.shubham.barbar;

import java.io.Serializable;

/**
 * Created by shubham on 11/5/2017.
 */

public class Item_Model  {

    /*  Model class for List and Recycler Items  */
    private String item, price,category_id;



    public Item_Model(String item, String price, String category_id) {
        this.item = item;
        this.price = price;
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

