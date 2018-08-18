package com.example.bett.wordscramble;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samaay.homework2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PlayActivity extends AppCompatActivity {

    String[] unscrambled = new String[]{"computer", "remote", "charger"};
    String[] scrambled = new String[]{"moctpreu", "etermo", "ragehcr"};
    int count =0;
    int score = 0;
    String userGuess = "";
    TextView scrambledView;
    EditText guessInput;
    int countdown = 0;
    Button guessButton;
    TextView Scorecounter;
    String useremail = "";

    Context context;

    String url2 = "http://wangumwenda.com/wordscramble/insert.php";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_play);

        scrambledView = findViewById(R.id.Scrambledword);

        guessInput = findViewById(R.id.inputguess);

        guessButton = findViewById(R.id.button);

        Scorecounter = findViewById(R.id.Score);

        scrambledView.setText(scrambled[count]);

        context = PlayActivity.this;

        Bundle bundle = getIntent().getExtras();


        if (bundle!=null) {
            useremail = bundle.getString("email");
        }

        Scorecounter.setText("Score : " + score);

    }

    public void onGuess(View view){
        //on guess button event

        userGuess = guessInput.getText().toString();
        timerTask();
        //Toast.makeText(this, "user input is "+userGuess+
        //" comparing to "+unscrambled[count], Toast.LENGTH_LONG ).show();
        if(count<unscrambled.length || count==unscrambled.length) {
            if (userGuess.equals(unscrambled[count])) {
                score++;
                count++;
                scrambledView.setText(scrambled[count]);
                guessInput.setText(null);
                Scorecounter.setText("Score : " + score);

                if (count == 2) {

                }


            } else {
                guessInput.setError("Wrong guess, try again!");

            }
        }
    }


    Runnable runnable;
    public void timerTask(){
        final Handler handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                //do your operation outside UI
                countdown++;
                showToast(""+countdown);
                if(countdown!=10){
                    handler.postDelayed(runnable, 1000);
                }else{
                    handler.removeCallbacks(this);
                    postJSON();
                }
            }
        };

        runnable.run();
    }



    public void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG ).show();
    }
    // Method to Sync MySQL to SQLite DB
    public void postJSON () {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar

        params.put("gamer_score", String.valueOf(score));
        params.put("gamer_email", useremail);
        params.put("gamer_time", String.valueOf(countdown));

        //Toast.makeText(getApplicationContext(),"score : "+score+" time : "+countdown,Toast.LENGTH_LONG).show();

        // Make Http call to getusers.php
        client.post(url2, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getApplicationContext(),Scores.class);

                startActivity(intent);
            }

            // When error occured

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub
                // Hide ProgressBar

                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }



}