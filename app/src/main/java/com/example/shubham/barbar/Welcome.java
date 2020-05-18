package com.example.shubham.barbar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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

public class Welcome extends AppCompatActivity {
    String sessionId;
    private static final String url = "https://beangate.in/barber_app/fetch_user";
    private TextView mTextMessage;
    private ActionBar toolbar;
    String uname,uemail,contact,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        mTextMessage = (TextView) findViewById(R.id.message);

        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new Home());
        UserPref user_pref = new UserPref(this);

        contact=user_pref.getCont();
        loadUserData();
    }

    private void loadUserData() {

        Response.Listener<String> responseListener = new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONArray array = jsonObject1.getJSONArray("server_response");
                    /*  Log.d("######", String.valueOf(array));*/
                    for ( int i=0; i< array.length(); i++){

                        JSONObject jsonObject2 = array.getJSONObject(i);
                        Log.d("######", String.valueOf(jsonObject2));
                        uname=jsonObject2.getString("name");
                        uemail=jsonObject2.getString("email");
                        UserPref user_pref = new UserPref(Welcome.this);
                        user_pref.setEmail(uemail);
                        user_pref.setName(uname);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        SaveMyRequest saveMyRequest = new SaveMyRequest(contact,s,responseListener);
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        queue.add(saveMyRequest);
    }


    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new Home();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_explore:
                        fragment = new Explore();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_appointment:
                        fragment = new Appointment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_profile:
                        fragment = new Profile();
                        loadFragment(fragment);

                        return true;
                }
                return false;
            }
        };




    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    }
