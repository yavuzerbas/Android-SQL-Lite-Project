package com.example.sql_lite_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sql_lite_project.MyExceptions.InsertException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername,etPassword,etConfirm;
    Button btnRegister,btnCancel;
    String userName,password,confirm;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_register_username);
        etPassword = findViewById(R.id.et_register_password);
        etConfirm = findViewById(R.id.et_register_confirm);
        btnRegister = findViewById(R.id.btn_register);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    @Override
    public void onClick(View view) {
        Intent returnIntent = new Intent();
        if(view.getId() == R.id.btn_register){
            if(!isUserCredentialsValid()){
                return;
            }
            dataBaseHelper = new DataBaseHelper(RegisterActivity.this);
            //try{
            if(dataBaseHelper.insertUser(userName,password)){
                Toast.makeText(RegisterActivity.this,"Registration is Successful!",
                        Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK,returnIntent);
                returnIntent.putExtra("username",userName);
            }
            //catch (InsertException e) {
            else{
                setResult(Activity.RESULT_CANCELED,returnIntent);
            //    e.printStackTrace();
            }


        }
        else if(view.getId() == R.id.btn_cancel){
            setResult(Activity.RESULT_CANCELED,returnIntent);
        }
        finish();
    }
    protected boolean isUserCredentialsValid(){
        userName = etUsername.getText().toString();
        password = etPassword.getText().toString();
        confirm = etConfirm.getText().toString();
        //userName,password,confirmPassword cannot be empty
        if(userName.length() == 0 || password.length() == 0 || confirm.length() == 0){
            Toast.makeText(RegisterActivity.this, "Fill all the blanks!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        //passwords should match
        if(!password.equals(confirm)){
            Toast.makeText(RegisterActivity.this, "Passwords should match!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6 ){
            Toast.makeText(RegisterActivity.this,
                    "Password length should be greater or equal to 6",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}