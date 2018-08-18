package com.example.bett.wordscramble;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samaay.homework2.R;


public class RegistrationActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Email;
    private EditText password;
    private EditText confirmpass;

    SharedPreferences shared = null;
    SharedPreferences.Editor edit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        Username = findViewById(R.id.User);
        Email = findViewById(R.id.Email);
        password= findViewById(R.id.password);
        confirmpass = findViewById(R.id.confirmpass);

        shared = getSharedPreferences("database",MODE_PRIVATE);
        edit = shared.edit();

      //  String name = shared.getString("username","null");
      //  String password = shared.getString("password","null");

    }

  /*  public void toast () {

       /* String inputs =
                Username.getText().toString()+
                        Email.getText().toString()+
                        Age.getText().toString()+
                        Location.getText().toString();
        Toast.makeText(this,inputs,Toast.LENGTH_LONG).show();
    }

    */

    public void Registration (View view) {

        edit.putString("username_"+Email.getText().toString(),Username.getText().toString());
        edit.putString("password_"+Email.getText().toString(), password.getText().toString());
        edit.putString("email_"+Email.getText().toString(), Email.getText().toString());

        if (password.getText().toString().equals(confirmpass.getText().toString())) {
            edit.commit();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else {
            Toast.makeText(this, "Password did not match", Toast.LENGTH_LONG).show();
        }


       /* toast();
        Toast.makeText(this, "This is my first interactive app", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),HowActivity.class);
        intent.putExtra("Username","Wangu Mwenda");
        intent.putExtra("password", "wangu");
        startActivity(intent);


        Toast.makeText(this,name+password,Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show();


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent); */

    }

}
