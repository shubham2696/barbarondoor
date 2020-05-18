package com.example.shubham.barbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;


import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class Book extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText add_1,add_2,zip_code;
    String add1,add2,zip,contact,email,name,slot,date,category_id;
    private AwesomeValidation awesomeValidation;
    String item1,price1;
    TextView price,item,showDate;
    Button getAppointment,selectDate;
    Boolean success;
    NetworkInfo ninfo;
    Button time_slot;
    TextView chooseTime;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    private static final String url = "https://beangate.in/barber_app/booking";
    private SimpleDateFormat simpleDateFormat;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        price=findViewById(R.id.price);
        item=findViewById(R.id.item);
        time_slot=findViewById(R.id.time_slot);
        showDate=findViewById(R.id.showDate);
        selectDate=findViewById(R.id.selectDate);
        getAppointment=findViewById(R.id.get_appointment);
        add_1=findViewById(R.id.add1);
        add_2=findViewById(R.id.add2);
        simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
        zip_code=findViewById(R.id.zip);
        item1=this.getIntent().getExtras().getString("item");
        price1=this.getIntent().getExtras().getString("price");
        category_id=this.getIntent().getExtras().getString("category_id");
        price.setText(price1);
        item.setText(item1);


        //backbutton

      /*  ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Hello");*/

      /*  ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        // internet conectivity
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        ninfo = cm.getActiveNetworkInfo();
        UserPref user_pref = new UserPref(this);

        contact=user_pref.getCont();
        email=user_pref.getEmail();
        name=user_pref.getName();

        Log.d("Get Contact", user_pref.getCont());
        Log.d("Get Email", user_pref.getEmail());
        Log.d("Get Name", user_pref.getName());
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

selectDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        DialogFragment datepicker =new MyDatePickerFragment();
        datepicker.show(getSupportFragmentManager(),"date Picker");
    }
});

        time_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Book.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                     /*   showDate.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);*/

                        slot=String.format("%02d:%02d", hourOfDay, minutes) + amPm;
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });


        //Appointment
        getAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (awesomeValidation.validate()) {
                    if (ninfo != null && ninfo.isConnected()) {
                        add1 = add_1.getText().toString().trim();
                        add2 = add_2.getText().toString().trim();
                        zip=zip_code.getText().toString().trim();


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Book.this).create();

                                // Setting Dialog Title
                                alertDialog.setTitle("Message");

                                // Setting Dialog Message
                                alertDialog.setMessage("Success");


                                try {
                                    Log.i("tagconvertstr", "[" + response + "]");
                                    JSONObject jsonResponse = new JSONObject(response);
                                    success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        /* Toast.makeText(this,"",Toast.LENGTH_SHORT).show();*/
                                        Log.d("Successfully Booked","");
                                        // Showing Alert Message
                                        alertDialog.show();


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
                        SaveMyRequest saveMyRequest = new SaveMyRequest(price1,item1,contact,name,email,add1,add2,zip,slot,date,category_id, responseListener);

                        RequestQueue queue = Volley.newRequestQueue(Book.this);
                        queue.add(saveMyRequest);
                    } else
                        Snackbar.make(view, " no Internet connection aviable", Snackbar.LENGTH_SHORT).show();


                }
            }
        });
    }


@Override
public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    Calendar calendar=Calendar.getInstance();
    calendar.set(Calendar.YEAR,year);
    calendar.set(Calendar.MONTH,month);
    calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
    String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    /*showDate.setText(currentDateString);*/
    date=currentDateString;
    /*TextView textView=(TextView) findViewById(R.id.textview);
    textView.setText(currentDateString);*/
}



}
