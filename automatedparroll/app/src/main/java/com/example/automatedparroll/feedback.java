package com.example.automatedparroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class feedback extends AppCompatActivity {
    EditText e1;
    Button b1;
    String ip="";
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        e1=findViewById(R.id.forgot1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final LinearLayout linearLayout=findViewById(R.id.linear);
//                findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final Snackbar snackbar=Snackbar.make(linearLayout,"", Snackbar.LENGTH_SHORT);
//                        View custom_view=getLayoutInflater().inflate(R.layout.activity_feedback, null);
//                        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
//                        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
//                        snackBarView.setPadding(0, 0, 0, 0);
//
//                        snackBarView.addView(custom_view, 0);
//                        snackbar.show();
//                    }
//
//                });


                final String feedback = e1.getText().toString();
                if (feedback.equalsIgnoreCase("")) {
                    e1.setError("Enter Feedback");
                } else {

                    RequestQueue queue = Volley.newRequestQueue(feedback.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/feedback";

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
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();


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
                            params.put("feedback", feedback); //params used to convert variable to key value like jason file. The vollee used to transfer to the pycharm trough the webservices in python file then request and response json object file.
                            params.put("userid", sh.getString("lid", ""));

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);


                }
            }
        });




    }

    }