package com.example.automatedparroll;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
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

public class Forgotpassword extends AppCompatActivity {

    EditText e1;
    Button b1;
    String ip="";
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        e1=findViewById(R.id.forgot1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = e1.getText().toString();
                if (number.equalsIgnoreCase("")) {
                    e1.setError("Enter number");
                } else {

                    RequestQueue queue = Volley.newRequestQueue(Forgotpassword.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/Forgotpassword";

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

                                    Intent intent=new Intent (getApplicationContext(),login1.class);
                                    PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,intent,0);
                                    SmsManager sms=SmsManager.getDefault();
                                    sms.sendTextMessage(number,null,"password:"+res,pi,null);

                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
//                                    Intent f=new Intent (getApplicationContext(),login1.class);
//                                      startActivity( f);

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
                            params.put("number", number); //params used to convert variable to key value like jason file. The vollee used to transfer to the pycharm trough the webservices in python file then request and response json object file.
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