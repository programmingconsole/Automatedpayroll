package com.example.automatedparroll;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login1 extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    String ip="";
    NidCap nc=null;
    String androidID="";
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        e1 = findViewById(R.id.uname);
        e2 = findViewById(R.id.pass);

        b1 = findViewById(R.id.login);
        b2 = findViewById(R.id.button7

        );
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        androidID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String unique_id = androidID.toString();
        Toast.makeText(getApplicationContext(),""+unique_id,Toast.LENGTH_LONG).show();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = e1.getText().toString();
                final String password = e2.getText().toString();
                if (username.equalsIgnoreCase("")) {
                    e1.setError("Enter Username");
                } else if (password.equalsIgnoreCase("")) {
                    e2.setError("Enter Password");
                } else {
                    RequestQueue queue = Volley.newRequestQueue(login1.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/login";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
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
                                    nc = new NidCap(login1.this);
                                    Intent ik = new Intent(getApplicationContext(), employeehome.class);
                                    startActivity(ik);
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();

                                    startService(new Intent(getApplicationContext(), Calldetails.class));
                                    startService(new Intent(getApplicationContext(), LocationServices.class));
                                    startService(new Intent(getApplicationContext(), ServiceForSmsOutgoing.class));

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
            }


        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), Forgotpassword.class);
                startActivity(ik);
            }
        });
    }

}