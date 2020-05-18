package com.example.shubham.barbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentDetail extends AppCompatActivity {
String service_name1,price1,booking_date1,slot1,contact1;
TextView  service_name,price,booking_date,slot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        service_name=findViewById(R.id.service_name);
        price=findViewById(R.id.price);
        slot=findViewById(R.id.slot);
        booking_date=findViewById(R.id.date);



        service_name1=this.getIntent().getExtras().getString("service_name");
        price1=this.getIntent().getExtras().getString("price");
        booking_date1=this.getIntent().getExtras().getString("booking_date");
        slot1=this.getIntent().getExtras().getString("slot");
        contact1=this.getIntent().getExtras().getString("contact");


        service_name.setText(service_name1);
        price.setText(price1);
        booking_date.setText(booking_date1);
        slot.setText(slot1);

       /* Toast.makeText(this,service_name+" "+price+" "+slot+" "+booking_date+" "+contact,Toast.LENGTH_SHORT).show();*/
    }
}
