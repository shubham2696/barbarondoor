package com.example.shubham.barbar;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaveMyRequest extends StringRequest {

    private static final String REGISTRATION_REQUEST_URL = "https://beangate.in/barber_app/registration";
    private static final String APPOINTMENT_REQUEST = "https://beangate.in/barber_app/booking";
    private static final String  FETCH_APPOINTMENT="https://beangate.in/barber_app/fetch_booking";
    private static final String  FETCH_USER="https://beangate.in/barber_app/fetch_user";

    private Map<String, String> params;


    public SaveMyRequest(String contact,String name, String email,String gender, Response.Listener<String> responseListener) {
        super(Method.POST, REGISTRATION_REQUEST_URL,responseListener, null);
        params = new HashMap<>();
        params.put("contact",contact);
        params.put("name",name);
        params.put("email",email);
        params.put("gender",gender);





    }

    public SaveMyRequest(String contact,String s,Response.Listener<String> responseListener) {
        super(Method.POST, FETCH_USER,responseListener, null);
        params = new HashMap<>();
        params.put("contact",contact);






    }

    public SaveMyRequest(String price,String item,String contact,String name, String email,String add1,String add2, String zip,String slot,String date,String category_id, Response.Listener<String> responseListener) {
        super(Method.POST, APPOINTMENT_REQUEST,responseListener, null);
        params = new HashMap<>();
        params.put("contact",contact);
        params.put("name",name);
        params.put("item",item);
        params.put("price",price);
        params.put("email",email);
        params.put("add1",add1);
        params.put("add2",add2);
        params.put("zip",zip);
        params.put("date",date);
        params.put("slot",slot);
        params.put("category_id",category_id);
    }

    public SaveMyRequest(String contact, Response.Listener<String> responseListener) {
        super(Method.POST, FETCH_APPOINTMENT,responseListener, null);
        params = new HashMap<>();
        params.put("contact",contact);
    }






    @Override
    public Map<String, String> getParams() {
        return params;
    }
}