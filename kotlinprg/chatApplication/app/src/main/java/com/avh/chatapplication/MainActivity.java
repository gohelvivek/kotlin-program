package com.avh.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText mgetphonenumber;
    android.widget.Button msendotp;
    CountryCodePicker mcountrycodepicker;
    String countrycode;
    String phonenumber;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarofmain;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String codesent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcountrycodepicker=findViewById(R.id.codepicker);
        msendotp=findViewById(R.id.otpbtn);
        mgetphonenumber=findViewById(R.id.phnumber);
        mprogressbarofmain=findViewById(R.id.prgbarbtn);

        firebaseAuth=FirebaseAuth.getInstance();

        countrycode=mcountrycodepicker.getSelectedCountryCodeWithPlus();

        mcountrycodepicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countrycode=mcountrycodepicker.getSelectedCountryCodeWithPlus();
            }
        });

        msendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number;
                number=mgetphonenumber.getText().toString();
                if (number.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Your Number",Toast.LENGTH_SHORT).show();
                }
                else if (number.length()<10)
                {
                    Toast.makeText(getApplicationContext(),"Enter Your Correct Number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mprogressbarofmain.setVisibility(View.VISIBLE);
                    phonenumber=countrycode+number;

                    PhoneAuthOptions options=PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phonenumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(MainActivity.this)
                            .setCallbacks(mCallbacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);

                }
            }
        });

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getApplicationContext(),"Sent OTP",Toast.LENGTH_SHORT).show();
                mprogressbarofmain.setVisibility(View.INVISIBLE);
                codesent=s;
                Intent intent=new Intent(MainActivity.this,otploding.class);
                intent.putExtra("otp",codesent);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent=new Intent(MainActivity.this,chat_part.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}