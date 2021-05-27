package com.example.automatedparroll;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Bundle bun=arg1.getExtras();
        if(bun==null)
        {
            return;
        }
        else
        {
            String num=arg1.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(arg0);
            Editor ed=sh.edit();
            Log.d("======", "outcall");
            Log.d("======", "outcall");
            Log.d("======", "outcall");
            ed.putString("num", num);
            ed.putString("callstatus", "incoming");
            ed.commit();
        }
    }


}




