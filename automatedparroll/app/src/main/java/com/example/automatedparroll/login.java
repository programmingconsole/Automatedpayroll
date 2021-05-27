package com.example.automatedparroll;

import android.app.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import com.example.automatedparroll.R;
import com.example.automatedparroll.employeehome;

class login extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    String ip="";
    SharedPreferences sh;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.uname);
        e2 = findViewById(R.id.pass);

        b1 = findViewById(R.id.login);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = e1.getText().toString();
                final String password = e2.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(login.this);
                String url = "http://" + sh.getString("ip","") + ":5000/login";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("invalid")) {
                                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                            } else {

                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("lid", res);
                                ed.commit();
                                Intent ik = new Intent(getApplicationContext(), employeehome.class);
                                    startActivity(ik);


//                                else if(spl[1].equals("volunteer")) {
//                                    Intent ik = new Intent(getApplicationContext(), Volunteerhome.class);
//                                    startActivity(ik);
//
//                                }



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("uname", username); //params used to convert variable to key value like jason file. The vollee used to transfer to the pycharm trough the webservices in python file then request and response json object file.
                        params.put("pass", password);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);


            }


        });

    }




}