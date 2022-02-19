package com.avh.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import com.avh.form.databinding.RegisterActivityBinding
import java.lang.Error

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    private lateinit var mBinding: RegisterActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = RegisterActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
    }

    private fun validateFullName(): Boolean {
        var errorMessage: String? = null
        val value:String = mBinding.fullNameEt.text.toString()
        if(value.isEmpty()){
            errorMessage="Full Name Is Required"
        }

        if (errorMessage!=null){
            mBinding.fullNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateEmail(): Boolean{
        var error: String? = null
        val value:String = mBinding.emailEt.text.toString()
        if(value.isEmpty()){
            error="Email is required"
        }else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            error="Email address is invalid"
        }
        return error == null
    }

    private fun validatePassword(): Boolean{
        var error: String? = null
        val value:String = mBinding.passwordEt.text.toString()
        if (value.isEmpty()){
            error="Password is required"
        }else if (value.length<6){
            error="Password must be 6 character long"
        }
        return error == null
    }

    private fun validateConfirmPassword():Boolean {
        var error: String? = null
        val value:String = mBinding.cPasswordEt.text.toString()
        if (value.isEmpty()){
            error="Confirm Password is Required"
        }else if (value.length<6){
            error="Confirm Password must be 6 character long"
        }
        return error == null
    }

    private fun validatePasswordAndConfirmPassword():Boolean{
        var error: String? = null
        val Password = mBinding.passwordEt.text.toString()
        val ConfirmPassword = mBinding.cPasswordEt.text.toString()
        if (Password != ConfirmPassword){
            error="Confirm Password Doesn't Match with password"
        }
        return error == null
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}