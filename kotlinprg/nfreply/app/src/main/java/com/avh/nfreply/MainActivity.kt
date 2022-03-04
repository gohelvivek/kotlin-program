package com.avh.nfreply

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.mlkit.nl.smartreply.*

class MainActivity : AppCompatActivity() {
    lateinit var etSendMsg: EditText
    lateinit var tvReplyMsg: TextView
    lateinit var btnTxtMsg: Button


    lateinit var conversations: ArrayList<TextMessage>
    var userUID="123456" //in production application its come from user uid

    lateinit var smartReplyGenerator: SmartReplyGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSendMsg=findViewById(R.id.sendmsg)
        tvReplyMsg=findViewById(R.id.textmsg)
        btnTxtMsg=findViewById(R.id.btnsendmsg)

        conversations= ArrayList()
        smartReplyGenerator=SmartReply.getClient()

        btnTxtMsg.setOnClickListener(){
            val message = etSendMsg.text.toString().trim()
            conversations.add(TextMessage.createForRemoteUser(message,System.currentTimeMillis(),userUID))

            smartReplyGenerator.suggestReplies(conversations).addOnSuccessListener {
                if(it.status==SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE){
                    tvReplyMsg.text="STATUS_NOT_SUPPORTED_LANGUAGE"
                }else if (it.status==SmartReplySuggestionResult.STATUS_SUCCESS){
                    var reply=""
                    for (suggestion:SmartReplySuggestion in it.suggestions){
                        reply=reply+suggestion.text+"\n"
                    }

                    tvReplyMsg.text=reply
                }
            }.addOnCanceledListener {
                tvReplyMsg.text="Error"
            }

        }
    }
}
