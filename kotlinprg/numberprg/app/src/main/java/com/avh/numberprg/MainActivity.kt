package com.avh.numberprg

import android.content.Intent
import android.content.IntentSender
import android.net.Credentials
import android.net.wifi.hotspot2.pps.Credential
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.account.WorkAccount.getClient
import com.google.android.gms.auth.api.Auth.CredentialsApi
import com.google.android.gms.auth.api.credentials.Credential.EXTRA_KEY
import com.google.android.gms.auth.api.credentials.Credentials.getClient
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever.getClient
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import androidx.core.app.ActivityCompat.startIntentSenderForResult

import android.app.PendingIntent
import android.content.IntentSender.SendIntentException


class MainActivity : AppCompatActivity() {

        lateinit var open_btn: Button
        lateinit var tv1: TextView

        companion object {
        var CREDENTIAL_PICKER_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        open_btn = findViewById(R.id.btn_open)
        tv1 = findViewById(R.id.tv1)
        
        open_btn.setOnClickListener {
            phoneSelection()
        }
    }
    private fun requestHint() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val options = CredentialsOptions.Builder()
            .forceEnableSaveDialog()
            .build()
        val intent: PendingIntent = Credentials.getClient(getContext(), options)
            .getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(
                intent.intentSender,
                RESOLVE_HINT, null, 0, 0, 0, null
            )
        } catch (e: SendIntentException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK) {

            // get data from the dialog which is of type Credential
            val credential: Credential? = data?.getParcelableExtra(Credential.)

            // set the received data t the text view
            credential?.apply {
                tv1.text = credential.id
            }
        } else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE) {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }
}