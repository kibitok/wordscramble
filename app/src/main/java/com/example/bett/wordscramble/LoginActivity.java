package com.example.bett.wordscramble;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samaay.homework2.R;


public class LoginActivity extends AppCompatActivity {

    EditText Email;
    EditText Password;
    SharedPreferences shared = null;
    SharedPreferences.Editor edit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.password);
        shared = getSharedPreferences("database", MODE_PRIVATE);
        edit = shared.edit();

    }

    public void loginbtn (View view) {
    if (shared.getString("email_"+Email.getText().toString(), "").equals(Email.getText().toString())
                && shared.getString("password_"+Email.getText().toString(), "").equals(Password.getText().toString()))
        {
            edit.putString("login", Email.getText().toString());
            edit.commit();
            Intent intent = new Intent(getApplicationContext(),PlayActivity.class);
            intent.putExtra("email",Email.getText().toString());
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Wrong Email or Password", Toast.LENGTH_LONG).show();

        }

//   runnable.run();

  // postJSON();

    }

    public void signupbtn (View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }



    Runnable runnable;
    public void timerTask(){
        final Handler handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                //do your operation outside UI
            }
        };

        runnable.run();
    }



}
