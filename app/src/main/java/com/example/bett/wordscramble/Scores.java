package com.example.bett.wordscramble;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.samaay.homework2.R;
import com.levitnudi.legacytableview.LegacyTableView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Scores extends AppCompatActivity  {

    SharedPreferences shared = null;
    SharedPreferences.Editor edit = null;
    String url = "http://wangumwenda.com/wordscramble/getScores.php";
    LegacyTableView legacyTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_scores);

        //set table title labels
        LegacyTableView.insertLegacyTitle("Username", "Score", "Date & Time", "Time Taken");
        //set table contents as string arrays


        legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
        //depending on the phone screen size default table scale is 100
        //you can change it using this method
        //legacyTableView.setInitialScale(100);//default initialScale is zero (0)

        //if you want a smaller table, change the padding setting
        legacyTableView.setTablePadding(7);

        //to enable users to zoom in and out:
        legacyTableView.setZoomEnabled(true);
        legacyTableView.setShowZoomControls(true);



        getJSON();


        shared = getSharedPreferences("database", MODE_PRIVATE);
        edit = shared.edit();
    }



    public void WriteJson (View view) {

            JSONObject obj = new JSONObject();
            obj.put("name", "mkyong.com");
            obj.put("age", new Integer(100));

        //Toast.makeText(getApplicationContext(),)

            JSONArray list = new JSONArray();
            list.add("msg 1");
            list.add("msg 2");
            list.add("msg 3");

            obj.put("messages", list);

            try (FileWriter file = new FileWriter("f:\\test.json")) {

                file.write(obj.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }



            System.out.print(obj);

    }

    // Method to Sync MySQL to SQLite DB
    public void getJSON () {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();

        // Make Http call to getusers.php
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                 syncDatabase (response);
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
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



    public void syncDatabase (String response) {
        try {

            // Extract JSON array from the response
            org.json.JSONArray arr = new org.json.JSONArray(response);
            System.out.println(arr.length());
            //Toast.makeText(context, "count is "+arr.length(), Toast.LENGTH_LONG).show();
            // If no of array elements is not zero
            if (arr.length() != 0) {
                // Loop through each array element, get JSON object which has userid and username
                for (int i = 0; i < arr.length(); i++) {
                    // Get JSON object
                    org.json.JSONObject object = (org.json.JSONObject) arr.get(i);
                    try {

                        //Toast.makeText(context, "Got response now...", Toast.LENGTH_LONG).show();
                        String id = object.getString("game_id").replaceAll("'", "''");
                        String name = object.getString("gamer_email").replaceAll("'", "''");
                        String code = object.getString("gamer_score").replaceAll("'", "''");
                        String info = object.getString("gamer_time").replaceAll("'", "''");

                        LegacyTableView.insertLegacyContent(name,code,new Date().toLocaleString().replaceAll(":",""), info);





                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error now... "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());


                //remember to build your table as the last step
                legacyTableView.build();


            }
        } catch (JSONException e) {
        }

    }

    }

