package com.example.automatedparroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class salary extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView t1;
    Spinner s1;
    String mnth;
    int salary=0;
    SharedPreferences sp;
//    String mnth="";
    String [] Month = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        s1=findViewById(R.id.spinner2);
        t1=findViewById(R.id.textView3);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         ArrayAdapter<String> ad=new ArrayAdapter<>(salary.this,android.R.layout.simple_list_item_1,Month);
        s1.setAdapter(ad);
        s1.setOnItemSelectedListener(salary.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(Month[position].equalsIgnoreCase("JANUARY"))
        {
            mnth="1";
        }
        if(Month[position].equalsIgnoreCase("FEBRUARY"))
        {
            mnth="2";
        }
        if(Month[position].equalsIgnoreCase("march"))
        {
            mnth="3";
        }
        if(Month[position].equalsIgnoreCase("april"))
        {
            mnth="4";
        }
        if(Month[position].equalsIgnoreCase("may"))
        {
            mnth="5";
        }
        if(Month[position].equalsIgnoreCase("june"))
        {
            mnth="6";
        }
        if(Month[position].equalsIgnoreCase("july"))
        {
            mnth="7";
        }
        if(Month[position].equalsIgnoreCase("August"))
        {
            mnth="8";
        }
        if(Month[position].equalsIgnoreCase("September"))
        {
            mnth="9";
        }
        if(Month[position].equalsIgnoreCase("October"))
        {
            mnth="10";
        }
        if(Month[position].equalsIgnoreCase("November"))
        {
            mnth="11";
        }
        if(Month[position].equalsIgnoreCase("December"))
        {
            mnth="12";
        }

//        mnth=s1.getSelectedItem().toString();
        String url ="http://"+sp.getString("ip", "") + ":5000/salary";
        RequestQueue queue = Volley.newRequestQueue(salary.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);




                        JSONObject jo=ar.getJSONObject(0);
                        salary=Integer.parseInt(jo.getString("hr"))+Integer.parseInt(jo.getString("mint"));
                        t1.setText(""+salary);





                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);



                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(salary.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sp.getString("lid","empid"));
                params.put("mnth", mnth);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
