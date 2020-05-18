package com.example.shubham.barbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.service.autofill.SaveRequest;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;

public class NewUser extends AppCompatActivity {
EditText uname,uemail,ugender;
    private AwesomeValidation awesomeValidation;
    String name,email,sessionId,gender,contact="9589412550";
    Button btn_save;
    Boolean success;
    NetworkInfo ninfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
       /* tv=findViewById(R.id.tview);*/
         sessionId= getIntent().getStringExtra("contact");
         btn_save=findViewById(R.id.save);
        uname=findViewById(R.id.name);
        uemail=findViewById(R.id.email);
        ugender=findViewById(R.id.gender);
        Log.d("3333333333",sessionId);
     /*   tv.setText(sessionId);*/


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        // internet conectivity
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        ninfo = cm.getActiveNetworkInfo();


        //registraion
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (awesomeValidation.validate()) {
                    if (ninfo != null && ninfo.isConnected()) {
                        name = uname.getText().toString().trim();
                        email = uemail.getText().toString().trim();
                        gender = ugender.getText().toString().trim();


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.i("tagconvertstr", "[" + response + "]");
                                    JSONObject jsonResponse = new JSONObject(response);
                                    success = jsonResponse.getBoolean("success");
                                        Log.d("Success==", String.valueOf(success));
                                    if (success) {
                                        UserPref user_pref = new UserPref(NewUser.this);
                                        user_pref.setCont(sessionId);


                                      /*  user_pref.set(sessionId);*/
                                        Intent intent = new Intent(NewUser.this, Welcome.class);
                                        NewUser.this.startActivity(intent);
                                    } else if (!success) {
                                        Snackbar snackbar = Snackbar
                                                .make(view, "Something went wrong", Snackbar.LENGTH_LONG)
                                                .setAction("Retry", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                });
                                        snackbar.setActionTextColor(Color.RED);
                                        snackbar.show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        };
                        SaveMyRequest saveMyRequest = new SaveMyRequest(sessionId,name, email,gender, responseListener);

                        RequestQueue queue = Volley.newRequestQueue(NewUser.this);
                        queue.add(saveMyRequest);
                    } else
                        Snackbar.make(view, " no Internet connection aviable", Snackbar.LENGTH_SHORT).show();


                }
            }
        });
    }
}
