package com.example.login

import android.provider.ContactsContract

class sign_up() {
    lateinit var username: String
    lateinit var email: String
    lateinit var password: String
    lateinit var id: String

    constructor(username: String, email: String, password: String, id: String): this(){
        this.email = email
        this.password=password
        this.id=id
        this.username=username
    }


}