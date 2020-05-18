package com.example.shubham.barbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Appointment extends Fragment {
    View view;
    TextView price,service_name;
    RecyclerViewAdapter2 recyclerViewAdapter2;
    RecyclerView recyclerView;
    List<DetailModel> detail_models;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserPref user_pref = new UserPref(getActivity());
        final String contact = user_pref.getCont();
        view = inflater.inflate(R.layout.fragment_appointment, container, false);
        recyclerView =  view.findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        price = view.findViewById(R.id.price);
        service_name=view.findViewById(R.id.service_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        detail_models=new ArrayList<>();
        Log.d("mmmmmmmmmm.", contact);
        final ProgressDialog dialog = ProgressDialog.show(getContext(), null, "Please Wait");


        Response.Listener<String> responseListener = new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONArray array = jsonObject1.getJSONArray("server_response");
                  /*  Log.d("######", String.valueOf(array));*/
                    for ( int i=0; i< array.length(); i++){

                        JSONObject jsonObject2 = array.getJSONObject(i);
                        Log.d("######", String.valueOf(jsonObject2));
                        DetailModel detail_model =new DetailModel(jsonObject2.getString("service_name"),jsonObject2.getString("price"),jsonObject2.getString("name"),jsonObject2.getString("booking_date"),jsonObject2.getString("slot"));
                        detail_models.add(detail_model);

                    }
                    recyclerViewAdapter2=new RecyclerViewAdapter2(detail_models,getActivity());
                    recyclerView.setAdapter(recyclerViewAdapter2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        SaveMyRequest saveMyRequest = new SaveMyRequest(contact,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(saveMyRequest);


        return view;
    }



}