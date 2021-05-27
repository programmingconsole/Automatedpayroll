package com.example.automatedparroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ipset extends AppCompatActivity {
  EditText e1;
  Button b1;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);
        e1 = findViewById(R.id.ipedit);
        b1=findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ip = e1.getText().toString();
                if (ip.equalsIgnoreCase("")) {
                    e1.setError("Enter IP");
                } else {
                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ip);
                    ed.commit();
                    Intent i = new Intent(getApplicationContext(), login1.class);
                    startActivity(i);

                }
            }
        });
    }
}