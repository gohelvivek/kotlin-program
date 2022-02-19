package com.avh.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otploding extends AppCompatActivity {

    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverifyotp;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarofotpauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otploding);

        mchangenumber=findViewById(R.id.txtview);
        mverifyotp=findViewById(R.id.veryotp);
        mgetotp=findViewById(R.id.getotp);
        mprogressbarofotpauth=findViewById(R.id.prgbtn);

        firebaseAuth=FirebaseAuth.getInstance();

        mchangenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(otploding.this,MainActivity.class);

                startActivity(intent);
            }
        });

        mverifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredotp=mgetotp.getText().toString();
                if (enteredotp.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter First OTP",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mprogressbarofotpauth.setVisibility(View.VISIBLE);
                    String coderecieved=getIntent().getStringExtra("OTP");
                    PhoneAuthCredential credential  = PhoneAuthProvider.getCredential(coderecieved,enteredotp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Login Sucess",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(otploding.this,main_profile.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}