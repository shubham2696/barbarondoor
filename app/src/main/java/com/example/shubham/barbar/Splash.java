package com.example.shubham.barbar;


import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Splash screen timer
        final UserPref userPref = new UserPref(Splash.this);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (userPref.getCont() != "") {
                    startActivity(new Intent(Splash.this, Welcome.class));

                    Log.d("@@@@@@@@@", "Welcome");
                }
                else {
                    startActivity(new Intent(Splash.this, Login.class));
                    Log.d("@@@@@@@@@","Login");
                    // close this activity
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
