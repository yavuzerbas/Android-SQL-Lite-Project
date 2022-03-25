package com.example.sql_lite_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUsername,etPassword;
    TextView tvRegister;
    Button btnLogin,btnListUsers;
    ListView listView;
    List<String> userList;
    ArrayAdapter<String> arrayAdapter;

    DataBaseHelper dataBaseHelper;
    final static int REGISTER_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataBaseHelper = new DataBaseHelper(LoginActivity.this);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvRegister = findViewById(R.id.tv_register);
        btnLogin = findViewById(R.id.btn_login);
        btnListUsers = findViewById(R.id.btn_list_users);
        listView = findViewById(R.id.listView_user_list);

        //userList = new ArrayList<>();


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            if(isLoginCredentialsValid()){
                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(dataBaseHelper.isUserValid(userName,password)) {
                    Intent homeIntent = new Intent(LoginActivity.this,HomeActivity.class);
                    homeIntent.putExtra("USERNAME",userName);
                    startActivity(homeIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Username or Password incorrect!",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                return;//code might get bigger and this part might be necessary
            }
        }
        else if(view.getId() == R.id.tv_register){
            Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivityForResult(registerIntent,REGISTER_REQUEST_CODE);
        }
        else if(view.getId() == R.id.btn_list_users){
            userList = dataBaseHelper.getAllUsers();
            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,userList);
            listView.setAdapter(arrayAdapter);
            Toast.makeText(getApplicationContext(),"listing",Toast.LENGTH_SHORT).show();
        }
        else{//unexpected case
            Log.e("Onclick error","Fell into else case!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REGISTER_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                //Toast.makeText(LoginActivity.this,data.getStringExtra("username"), Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(LoginActivity.this,"Couldn't register!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LoginActivity.this,"Unexpected result Code!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(LoginActivity.this,"Unexpected request Code!", Toast.LENGTH_SHORT).show();
        }
    }
    protected boolean isLoginCredentialsValid(){
        String userName = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if(userName.length() == 0 || password.length() == 0){
            Toast.makeText(LoginActivity.this, "You should fill the blanks!"
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6){
            Toast.makeText(LoginActivity.this, "Password or username incorrect"
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}