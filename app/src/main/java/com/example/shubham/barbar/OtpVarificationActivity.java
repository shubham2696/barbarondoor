package com.example.shubham.barbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class OtpVarificationActivity extends AppCompatActivity {





        Button done;
        Pinview pinview;
        String fullotp;
        TextView resendCode,editMobileNo_otpverification_txt;
        String verificationId,mobileNo;
        SpotsDialog spotsDialog ;
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_otp_varification);
            spotsDialog = new SpotsDialog(this);
            verificationId = getIntent().getStringExtra("verificationId");
            mobileNo = getIntent().getStringExtra("mobileno");
            done = (Button)findViewById(R.id.doneVerification_otpverification_linearlayout);
            pinview = (Pinview)findViewById(R.id.pinview);
            resendCode = (TextView)findViewById(R.id.resendCode_otpVerification_text);
            editMobileNo_otpverification_txt=findViewById(R.id.editMobileNo_otpverification_txt);

            // Setting Timer
            new CountDownTimer(60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    resendCode.setText(""+millisUntilFinished / 1000);
                    resendCode.setEnabled(false);
                }

                public void onFinish() {
                    resendCode.setText("RESEND CODE");
                    resendCode.setEnabled(true);
                }
            }.start();

            editMobileNo_otpverification_txt.setText(mobileNo);
            //setting OtpCOde
            pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
                @Override
                public void onDataEntered(Pinview pinview, boolean b) {
                    fullotp = pinview.getValue();
                }
            });

            // Submit Button
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spotsDialog.show();
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,fullotp);
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }
            });

            //resending Code
            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNo
                            ,60
                            , TimeUnit.SECONDS
                            ,OtpVarificationActivity.this
                            ,mCallback);

                    Toast.makeText(getBaseContext(),"We Have Sent To You Another Otp ",Toast.LENGTH_SHORT).show();
                }
            });


            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
            {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    signInWithPhoneAuthCredential(phoneAuthCredential);

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(getApplicationContext(),"Invalid Mobile Number",Toast.LENGTH_SHORT).show();

                    } else if (e instanceof FirebaseTooManyRequestsException) {

                        Toast.makeText(getApplicationContext(),"Quate Exceed",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCodeSent(String verificationIdresend,
                                       PhoneAuthProvider.ForceResendingToken token) {

                    verificationId = verificationIdresend;
                    new CountDownTimer(60000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            resendCode.setText(""+millisUntilFinished / 1000);
                            resendCode.setEnabled(false);
                        }

                        public void onFinish() {
                            resendCode.setText("RESEND CODE");
                            resendCode.setEnabled(true);
                        }
                    }.start();

                }
            };


        }

        public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        //Checking User is New Or Old
                        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(task.getResult().getUser().getUid().toString()).exists())
                                {
                                    Log.d("vkfdvdfv","if");
                                    // Sending to mainActivity
                                    Log.d("contact",task.getResult().getUser().getPhoneNumber());

                                    String session_id=task.getResult().getUser().getPhoneNumber();
                                    Intent intent = new Intent(OtpVarificationActivity.this, Welcome.class);
                                    /* intent.putExtra("unique_id", id);*/

                                    UserPref user_pref = new UserPref(OtpVarificationActivity.this);
                                    user_pref.setCont(session_id);
                                   /* intent.putExtra("contact", task.getResult().getUser().getPhoneNumber());*/
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Log.d("vkfdvdfv","else");
                                    String id =task.getResult().getUser().getUid().toString();
                                    Log.d("###################",id);
                                    Intent intent = new Intent(OtpVarificationActivity.this, NewUser.class);
                                   /* intent.putExtra("unique_id", id);*/

                                    intent.putExtra("contact", task.getResult().getUser().getPhoneNumber());
                                    startActivity(intent);

                                    /* startActivity(new Intent(OtpVarificationActivity.this,NewUser.class));*/
                                    finish();

                                    HashMap<String,String> user = new HashMap<>();
                                    user.put("mobile",task.getResult().getUser().getPhoneNumber());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(task.getResult().getUser().getUid()).setValue(user);

                                    // Sending to mainActivity



                                }
                                spotsDialog.dismiss();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Enter Correct Otp Code",Toast.LENGTH_SHORT).show();
                        spotsDialog.dismiss();

                    }
                }
            });

        }
    }