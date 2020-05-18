package com.example.shubham.barbar;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPref {
    SharedPreferences sharedPreferences;
    Context context;
    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }
    public String getEmail() {
        email=sharedPreferences.getString("email","");
        return email;
    }
    public String getName() {
        name=sharedPreferences.getString("name","");
        return name;
    }
    public String getCont() {

        cont=sharedPreferences.getString("cont","");
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
        sharedPreferences.edit().putString("cont",cont).commit();
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("name",name).commit();
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email).commit();
    }

    private String cont;
    private String name;
    private String email;


    public UserPref(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }
}

