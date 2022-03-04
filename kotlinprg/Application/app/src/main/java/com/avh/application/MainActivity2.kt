package com.avh.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_main)

        val abc = findViewById<TextView>(R.id.lin_1)
        abc.setOnClickListener(){
            startActivity(Intent(this@MainActivity2,MainActivity::class.java))
        }
    }
}