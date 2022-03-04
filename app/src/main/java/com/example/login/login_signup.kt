package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class login_signup : AppCompatActivity(),View.OnClickListener {

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        val btnalready = findViewById<TextView>(R.id.btn_already)
        val btncreate = findViewById<TextView>(R.id.btn_create)

        val btnlogin = findViewById<Button>(R.id.btn_login)
        val btnsignup = findViewById<Button>(R.id.btn_signup)

        btnalready.setOnClickListener(this)
        btncreate.setOnClickListener(this)
        btnlogin.setOnClickListener(this)
        btnsignup.setOnClickListener(this)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Chatapp").child("users")

    }
    override fun onClick(v: View?) {

        val btnalready = findViewById<TextView>(R.id.btn_already)
        val btncreate = findViewById<TextView>(R.id.btn_create)

        val btnlogin = findViewById<Button>(R.id.btn_login)
        val btnsignup = findViewById<Button>(R.id.btn_signup)

        val loginscreen = findViewById<LinearLayout>(R.id.login_layout)
        val signupscreen = findViewById<LinearLayout>(R.id.signup_layout)

        when(v){
            btnalready ->
            {
                loginscreen.visibility = View.VISIBLE
                signupscreen.visibility = View.GONE
            }
            btncreate ->
            {
                loginscreen.visibility = View.GONE
                signupscreen.visibility = View.VISIBLE
            }
            btnlogin ->
            {
                login()

            }
            btnsignup ->
            {
                signUp()

            }
        }
    }

    private fun login()
    {

        val emm = findViewById<EditText>(R.id.email_login)
        val passw = findViewById<EditText>(R.id.password_login)

        var email = emm.text.toString().trim()
        var password = passw.text.toString().trim()

        if (email.isEmpty() || password.isEmpty())
        {
            showToast("All Fields Required")
        }else
        {
            if (isValidEmail(email))
            {
                isEmailExist(email, password)
            }else
            {
                showToast("Check Your Email Address")
            }
        }
    }

    private fun isEmailExist(email: String,password: String)
    {

        // Read from the database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                var list = ArrayList<sign_up>()
                var isemailexist=false
                for (postsnapshot in dataSnapshot.children)
                {
                    val value = postsnapshot.getValue<sign_up>()

                    if (value!!.email == email && value!!.password == password)
                    {
                        isemailexist=true
                    }
                    list.add(value!!)
                }
                if (isemailexist){
                    showToast("Login Successfull")
                    startActivity(Intent(applicationContext,secondActivity::class.java))
                }else{
                    showToast("Login Failed")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                showToast(error.toString())
            }
        })
    }


    private fun signUp()
    {
        val name = findViewById<EditText>(R.id.name_signup)
        val em = findViewById<EditText>(R.id.email_signup)
        val pass = findViewById<EditText>(R.id.password_signup)
        val confpass = findViewById<EditText>(R.id.confirm_password_signup)


        var username = name.text.toString().trim()
        var email = em.text.toString().trim()
        var password = pass.text.toString().trim()
        var confirmpass = confpass.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpass.isEmpty())
        {
            showToast("All Fields Required")
        }else
        {
            if (isValidEmail(email))
            {
                var id = databaseReference.push().key
                var model = sign_up(username,email,password,id!!)

                databaseReference.child(id).setValue(model)
                showToast("Signup successfull")

                startActivity(Intent(applicationContext,secondActivity::class.java))
                finish()

            }else
            {
                showToast("Check Your Email Address")
            }
        }
    }
    private fun isValidEmail(email:String):Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun showToast(text: String)
    {
        Toast.makeText(this@login_signup,text, Toast.LENGTH_SHORT).show()
    }

}