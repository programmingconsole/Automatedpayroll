package com.example.automatedparroll;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class ServiceForSmsIncoming extends BroadcastReceiver{
    //	String namespace="http://pack/";   //website open for every website there were same namespace
//
//	 String method="msglog";   //databse name in web
//		String soap=namespace+method;
    //String method2="savescreen";
    //	String soap2=namespace+method2;
    Context context;
    TelephonyManager manager;
    //JSONParser jparser=new JSONParser();
    String msg,number,blocknumber,url="";
    SharedPreferences sp;
    String imei="";

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        context=arg0;
        sp=PreferenceManager.getDefaultSharedPreferences(arg0);

        manager = (TelephonyManager) arg0.getSystemService(arg0.TELEPHONY_SERVICE);
        //imei=manager.getDeviceId().toString();
        imei="122343243";
        Toast.makeText(arg0,"res", Toast.LENGTH_SHORT).show();
        Bundle b = arg1.getExtras();
        Object[] obj = (Object[]) b.get("pdus");
        SmsMessage[] sms_list = new SmsMessage[obj.length];//bundle b is a object the msg s inside tht object

        for (int i = 0; i < obj.length; i++)
        {
            sms_list[i] = SmsMessage.createFromPdu((byte[]) obj[i]);
        }

        msg = sms_list[0].getMessageBody().trim();
        number = sms_list[0].getOriginatingAddress();
        Log.d("=================="+number, "msg--"+msg);


//		if(msg.equalsIgnoreCase("lock"))
//		{
//			this.abortBroadcast();
//			 Log.d("lockayeeee...", "hoooha");
//			// Intent intentLock=new Intent(arg0,Lock_screen.class);
//			// intentLock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			// arg0.startActivity(intentLock);
//
//			 Toast.makeText(arg0, "Locked !!!", Toast.LENGTH_LONG).show();
//		}
//		if(msg.equalsIgnoreCase("unlock"))
//		{
//			this.abortBroadcast();
//			 Log.d("unlockayeeee...", "hoooha");
//			 Intent in=new Intent(Intent.ACTION_MAIN);
//			 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			 in.addCategory(Intent.CATEGORY_HOME);
//			 arg0.startActivity(in);
//
//			 Toast.makeText(arg0, "Un-Locked !!!", Toast.LENGTH_LONG).show();
//		}
//		if(msg.startsWith("normal"))
//		{
//
//			AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//		}
//		 if(msg.startsWith("silent"))
//		{
//
//			AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//		}
//		if(msg.startsWith("vibrate"))
//		{
//
//			AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//		}
//		if(msg.startsWith("send"))
//		{
//			String[] message = msg.split("~");
//			if(message.length>1){
//				String sms = message[1];
//				SmsManager smsManager = SmsManager.getDefault();
//				smsManager.sendTextMessage(message[2], null, sms, null, null);
//				this.abortBroadcast();
//			}
//		}
//		if(msg.startsWith("screen")){
//			Toast.makeText(arg0, msg+"--", Toast.LENGTH_SHORT).show();
//
//			try{
//			Process sh = Runtime.getRuntime().exec("su");
//            OutputStream  os = sh.getOutputStream();
//            os.write(("/system/bin/screencap -p "+Environment.getExternalStorageDirectory()+ File.separator +imei+".png").getBytes("ASCII"));
////            os.write((Environment.getExternalStorageDirectory()+ File.separator +imei+".png").getBytes("ASCII"));
//            os.flush();
//
//            os.close();
//            sh.waitFor();
//
//			Toast.makeText(arg0, "2----", Toast.LENGTH_SHORT).show();
//
//          byte[] byteArray = null;
//          try
//          {
//        	  File f=new File(Environment.getExternalStorageDirectory()+ File.separator +imei+".png");
//              InputStream inputStream = new FileInputStream(f);
//              ByteArrayOutputStream bos = new ByteArrayOutputStream();
//              byte[] bb = new byte[(int)f.length()];
//              int bytesRead =0;
//
//              while ((bytesRead = inputStream.read(bb)) != -1)
//              {
//                  bos.write(bb, 0, bytesRead);
//              }
//              byteArray = bos.toByteArray();
//          }
//          catch (IOException e)
//          {
//  			Toast.makeText(arg0, e.getMessage()+"--333", Toast.LENGTH_SHORT).show();
//          }
//          this.abortBroadcast();
////          String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
////
////          try
////			{
////				SoapObject request=new SoapObject(namespace,method2);
////				request.addProperty("imei",imei);
////				request.addProperty("fl",str);
////
////				SoapSerializationEnvelope env=new SoapSerializationEnvelope(SoapEnvelope.VER11);
////				env.setOutputSoapObject(request);
////
////				HttpTransportSE http=new HttpTransportSE(this.sh.getString("url", ""));
////				http.call(soap2,env);
////
////				String res=env.getResponse().toString();
////			}
////          	catch (Exception e) {
////
////    			Toast.makeText(arg0, e.getMessage()+"----444", Toast.LENGTH_SHORT).show();
////          	}
////
//			}
//			catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
        //	blocknumber=sh.getString("block","anyType{}");

        //	if(blocknumber.equalsIgnoreCase("anyType{}"))
        //{
        //	Toast.makeText(arg0, "No block number", Toast.LENGTH_SHORT).show();
        //	}
        //else
        //	{

        //	checkBlock(blocknumber, number,arg0);
        //	Toast.makeText(arg0, "block number"+blocknumber, Toast.LENGTH_SHORT).show();
        //	}

        msglogs(msg, number, "incoming");
    }

    private void msglogs(final String msg,  final String phone,   final String type) {
//		try
//		{
////			SoapObject sop=new SoapObject(namespace,method);
////			sop.addProperty("message", msg);
////			sop.addProperty("contact", phone);
////			sop.addProperty("IMEI",imei);
////			sop.addProperty("type",type);
////
////			SoapSerializationEnvelope sen=new SoapSerializationEnvelope(SoapEnvelope.VER11);
////		    sen.setOutputSoapObject(sop);
////
////		    HttpTransportSE http=new HttpTransportSE(sh.getString("url", ""));
////		    http.call(soap, sen);
////		    String tyy=sen.getResponse().toString();
//
//		}
//		catch(Exception ex)
//		{
//			Toast.makeText(context, "error"+ex, Toast.LENGTH_LONG).show();
//		}


        sp=PreferenceManager.getDefaultSharedPreferences(context);
//		Toast.makeText(getApplicationContext(), dur, Toast.LENGTH_LONG).show();
        String ip=sp.getString("ip","");

        url ="http://"+ip+":5000/c_fetchmessage";
        Toast.makeText(context, "url"+url, Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(context);

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
                            	 Toast.makeText(context," sms Registered Successfully",Toast.LENGTH_LONG).show();



                    }
                    else
                    {
                            	 Toast.makeText(context,"Registeration not completed",Toast.LENGTH_LONG).show();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Ex" + e, Toast.LENGTH_SHORT).show();


                }


            }
        }, new Response.ErrorListener() {




            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Ex" + error, Toast.LENGTH_SHORT).show();


//                Toast.makeText(ServiceForSmsIncoming.this,"Error"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
//                params.put("imei", sp.getString("imei","e0acdbbdb42605bb"));
                params.put("message", msg);
                params.put("phone", number);
//                params.put("type", type);
                   params.put("uid", sp.getString("lid", ""));
















                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

    public void checkBlock(String blockednumber,String currentnumber,Context cntxt)
    {
        String [] blockednumers=blockednumber.split("#");
        int flagforblock=0;
        for(int i=0;i<blockednumers.length;i++)
        {
            if(blockednumers[i].length()>=10 && currentnumber.length()>=10)
            {
                blockednumers[i]=blockednumers[i].substring(blockednumers[i].length()-10,blockednumers[i].length() );
                currentnumber=currentnumber.substring(currentnumber.length()-10,currentnumber.length() );
                Log.d("....outnum....",blockednumers[i]+"..b[i]..outnum.."+currentnumber);
            }
            if(blockednumers[i].equals(currentnumber))
            {
                flagforblock=1;
            }
        }

        if(flagforblock==1)
        {
            try
            {
                abortBroadcast();
            }
            catch (Exception e)
            {
                Log.d("error in call blocking",e.getMessage()+"");
                Toast.makeText(cntxt, "error in message blocking:"+e.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
