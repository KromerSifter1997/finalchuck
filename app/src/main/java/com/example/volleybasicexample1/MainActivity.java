package com.example.volleybasicexample1;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.chucknorris.client.ChuckNorrisClient;
import io.chucknorris.client.Joke;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private ImageButton btnRequest;

    private TextView txt;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.chucknorris.io/jokes/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnRequest = (ImageButton) findViewById(R.id.buttonRequest);
        txt = (TextView) findViewById(R.id.txt);
        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){
                                              sendAndRequestResponse();
                                              //ChuckNorrisClient client = new ChuckNorrisClient();
                                              //Joke joke = client.getRandomJoke();

                                              //Toast.makeText(getApplicationContext(),"Response :" + joke.getValue(), Toast.LENGTH_LONG).show();//display the response on screen

                                          }
                                      }

        );

    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] parts = response.toString().split("\",\"");
                String[] joke = parts[5].toString().split(":");
                String theJoke = joke[1];

                if (joke[1].contains("}")) {
                    theJoke = joke[1].replace("}","");
                }

                // Joke kept in 'Value'
                //Toast.makeText(MainActivity.this, joke[1].toString(), Toast.LENGTH_SHORT).show();
                txt.setText(theJoke);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}