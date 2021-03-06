package com.avh.jewelbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class additem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)

        val addl = findViewById<Button>(R.id.addl)
        val etex = findViewById<EditText>(R.id.etex)

        addl.setOnClickListener() {
            val etex = etex.text.toString()
            val db = DBHelper1(this, null)

            if (etex.equals("")) {
                Toast.makeText(this, "Data is Empty", Toast.LENGTH_SHORT).show();
            } else {
                val data: Boolean = db.insertData(etex)
                if (data == false) {
                    Toast.makeText(this, "Something is wrong in Database.", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    Toast.makeText(this, "Record Inserted Successfully.", Toast.LENGTH_SHORT)
                        .show();
                }
            }

        }
    }
}