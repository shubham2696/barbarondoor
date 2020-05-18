package com.example.shubham.barbar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText mobileNo;
    Button proceed;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String cont;
    SpotsDialog spotsDialog ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobileNo = (EditText) findViewById(R.id.mobileno_login_edt);
        proceed = (Button)findViewById(R.id.proceed_login_btn);


        spotsDialog = new SpotsDialog(this);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG,"setOnClickListener");
                // Doing Mobile Number Validation
                if(validateMobileNo(mobileNo.getText().toString()))
                {
                    Log.i(TAG,"valid");
                    sendVerificationCode(mobileNo.getText().toString());

                }
                else{
                    mobileNo.setError("Please Enter Correct Mobile Number");
                }

            }
        });


        // Different States after otp sent
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                spotsDialog.show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(),"Invalid Mobile Number",Toast.LENGTH_SHORT).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Toast.makeText(getApplicationContext(),"Quate Exceed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);

                Intent i = new Intent(Login.this,OtpVarificationActivity.class);
                i.putExtra("verificationId",verificationId);
                i.putExtra("resendToken",token);
                i.putExtra("mobileno",mobileNo.getText().toString());

                startActivity(i);


            }
        };



    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //Checking User is New Or Old
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(task.getResult().getUser().getUid().toString()).exists())
                            {
                                spotsDialog.dismiss();
                                // Sending to mainActivity
                                cont=task.getResult().getUser().getPhoneNumber();
                                UserPref user_pref = new UserPref(Login.this);
                                user_pref.setEmail(cont);

                                startActivity(new Intent(Login.this,OtpVarificationActivity.class));
                                finish();
                            }
                            else
                            {
                                spotsDialog.dismiss();
                                HashMap<String,String> user = new HashMap<>();
                                user.put("mobile",task.getResult().getUser().getPhoneNumber());
                                databaseReference.child("Users").child(task.getResult().getUser().getUid()).setValue(user);
                                cont=task.getResult().getUser().getPhoneNumber();
                                UserPref user_pref = new UserPref(Login.this);
                                user_pref.setEmail(cont);
                                // Sending to mainActivity
                                startActivity(new Intent(Login.this,Welcome.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{

                    spotsDialog.dismiss();

                }
            }
        });

    }



    public void sendVerificationCode(String text) {
        Log.d(TAG, "sendVerificationCode: Verification Code Sent");

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+text.toString()
                ,60
                , TimeUnit.SECONDS
                ,this
                ,mCallback);
    }

    private boolean validateMobileNo(String text) {

        if(text.length()<10 || text.length()>10)
        {
            return false;
        }
        return true;
    }


}