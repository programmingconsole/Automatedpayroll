package com.example.automatedparroll;



import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;




import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Calldetails extends Service {

	String opn;
	String dt="",tm="";
	long diffinmin,diffinhr;
	TelephonyManager telephonyManager;
	TelephonyManager telman;
	
	List<String>phonenm,name,id;
	String imei="";
	 public static int flg=0;	
	 String phnop="";
	 SharedPreferences sp;
	 @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate()
	 {
		 Toast.makeText(getApplicationContext(), "service started", Toast.LENGTH_SHORT).show();

		 name=new ArrayList<String>();
			phonenm=new ArrayList<String>();
			id=new ArrayList<String>();

		// TODO Auto-generated method stub
		super.onCreate();
		
		if (Build.VERSION.SDK_INT > 9)
		{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
		

		 SimpleDateFormat tet=new SimpleDateFormat("hh:mm:ss");
		 tm=tet.format(new Date());
		 telman=(TelephonyManager)getApplicationContext().getSystemService(TELEPHONY_SERVICE);
		// imei=telman.getDeviceId().toString();
		
		 telman.listen(phlist,PhoneStateListener.LISTEN_CALL_STATE);
		 Log.d("....old...", ".....00");
	}
  
	 public PhoneStateListener phlist=new PhoneStateListener()
   {
	   public void onCallStateChanged(int state, String inNum) 
	   {
		  // Toast.makeText(getApplicationContext(), "state"+state, Toast.LENGTH_LONG).show();
		  switch (state) 
		  {
		  
		
		  
		  
		     case TelephonyManager.CALL_STATE_IDLE:
						
		    	 		SimpleDateFormat dd=new SimpleDateFormat("dd/MM/yyyy");
						SimpleDateFormat tt=new SimpleDateFormat("hh:mm:ss");
						
						String d=dd.format(new Date());
						String t=tt.format(new Date());
					   Toast.makeText(getApplicationContext(), "IDLE",Toast.LENGTH_LONG).show();
						String duration="";
						long tmdiff=0;
						
					//	Log.d("....old...", ".....3");
						try 
						{
								Date dt1=tt.parse(t);
								Date dt2=tt.parse(tm);
							
								tmdiff=dt1.getTime()-dt2.getTime();
							
								tmdiff=TimeUnit.MILLISECONDS.toSeconds(tmdiff);
								diffinmin=tmdiff/(60);
								diffinhr=diffinmin/(60);
								tmdiff-=(diffinmin*60);

			
								duration=diffinhr+":"+ diffinmin + ":"+ tmdiff;
								
								
								SharedPreferences shp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								Editor edit=shp.edit();
								edit.putString("duration", duration);
								edit.commit();
								
								
								
								
								//Toast.makeText(getApplicationContext(), "call duration" +duration, Toast.LENGTH_LONG).show();
						
						} 
						catch (Exception e) 
						{
							// TODO Auto-generated catch block
							Toast.makeText(getApplicationContext(), "error1 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
							Log.d("error1",e.getMessage());
						}
						
						
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
						String name = preferences.getString("callstatus", "hi");
				//Toast.makeText(getApplicationContext(), "ttt"+name, Toast.LENGTH_LONG).show();
						if(name.equalsIgnoreCase("incoming"))
						{
							Log.d("....13333....", "..incallggggggggggggg..");
							

							SharedPreferences shpr=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
							Editor edit1=shpr.edit();


							//edit1.putString("Number", "incoming call");
							
							edit1.commit();
							
							
							
							try 
							{
								//call(MainActivity.phoneid,phnop,"incoming",duration,d,t);
							} 
							catch (Exception e)
							{
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(), "error2 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
								Log.d("error2",e.getMessage());
							} 
							
							
							SharedPreferences sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					
						String ss=sh1.getString("num", "");
							
							String dur=sh1.getString("duration", "");
							
							//Toast.makeText(getApplicationContext(),ss  +"\n"+  "incoming call" +"\n" +dur , Toast.LENGTH_LONG).show();
						
								
							 flg=0;
						 }
						
						
						 else if(flg==1)
						 {
							 Log.d("....1....", "..outcall..");
							 try 
							 {
								SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								opn=sh.getString("num", "");
								
								//call1(MainActivity.phoneid,opn,"outgoing",duration,d,t);
							 } 
							 catch (Exception e) 
							 {
								// TODO Auto-generated catch block
								 Toast.makeText(getApplicationContext(), "error3 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
								 Log.d("error3",e.getMessage());
							 }
							 
							 
							 SharedPreferences sh2=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								
								
								String dur=sh2.getString("duration", "");
								//Toast.makeText(getApplicationContext(),opn+"\n"+"Outgoing call"+"\n"+dur , Toast.LENGTH_LONG).show();
							
							 
							 
							 flg=0;
						 }
						 
						
				         Editor editor = preferences.edit();
				         editor.putString("callstatus","idle");
				         editor.commit();
				         
				         break;
				         
				         
				         
				         
				         
			
			
		     case TelephonyManager.CALL_STATE_OFFHOOK:
			
		    	 		SimpleDateFormat sm=new SimpleDateFormat("dd/MM/yyyy");
		    	 		SimpleDateFormat sn=new SimpleDateFormat("hh:mm:ss");
			
		    	 		flg=1;
			
		    	 		dt=sm.format(new Date());
		    	 		tm=sn.format(new Date());
		    	 
		    	 		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    	 		String opn=pref.getString("num", "");
		    	 		Toast.makeText(getApplicationContext(), dt + "  " + tm+opn, Toast.LENGTH_LONG).show();
						
		    	 		if(opn.equalsIgnoreCase(""))
		    	 		{
		    	 			Toast.makeText(getApplicationContext(), opn	, Toast.LENGTH_SHORT).show();
		    	 				opn=phnop;
		    	 		}
		    	 		inscall(imei,opn,"outgoing");
		    	 		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    	 		String blknum=sh.getString("block","");
			
		    	 		int xy=0;
		    	 		Log.d("...outn..",blknum+"..outn.."+opn);
		    	 		if(!blknum.equalsIgnoreCase("#"))
		    	 		{
			
		    	 			String b[]=blknum.split("#");
		    	 			for(int i=0;i<b.length;i++)
		    	 			{
		    	 				if(b[i].length()>=10 && opn.length()>=10)
		    	 				{
		    	 					b[i]=b[i].substring(b[i].length()-10,b[i].length() );
		    	 					opn=opn.substring(opn.length()-10,opn.length() );
		    	 					Log.d("....outnum....",b[i]+"..b[i]..outnum.."+opn);
		    	 				}				
		    	 				if(b[i].equals(opn))
		    	 				{
		    	 					xy=1;
		    	 				}
		    	 			}		
		    	 		}
			
		    	 		if(xy==1)
		    	 		{
		    	 			////call reject					
		    	 			try 
		    	 			{			      		   
    	 			} 
		    	 			catch (Exception e)
		    	 			{
		    	 				// TODO: handle exception
		    	 				Toast.makeText(getApplicationContext(), "error4 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
		    	 				Log.d("error4",e.getMessage());
		    	 			}
		    	 		}
		    	 		break;
		    	 		
		    	 		
		    	 		
		    	 		
		    	 		
	
		     case TelephonyManager.CALL_STATE_RINGING:
		    	// Toast.makeText(getApplicationContext(), "No"+inNum, Toast.LENGTH_LONG).show();
		     			phnop=inNum;
		     			Toast.makeText(getApplicationContext(), "No"+phnop, Toast.LENGTH_LONG).show();
					
						//phnop="+91 9747 360170";
						
						phnop=	phnop.replace(" ","");
						
					//    Toast.makeText(getApplicationContext(), phnop +"clear", Toast.LENGTH_SHORT).show();
						
//						if(phnop.length()>=10)
//						 phnop=phnop.substring(phnop.length()-10, phnop.length());
						
						inscall(imei,phnop,"incoming");
						
		     		   
						sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						String st= sh.getString("active", "");
						
						
						if(st.equalsIgnoreCase("ok"))
						{
						
							String blknums=sh.getString("block","");
							//Toast.makeText(getApplicationContext(), "blockinc"+blknums, Toast.LENGTH_LONG).show();
			    	 		int xys=0;
			    	 		Log.d("...outn..",blknums+"..outn.."+phnop);
			    	 		if(!blknums.equalsIgnoreCase("#"))
			    	 		{
				
			    	 			String b[]=blknums.split("#");
			    	 			for(int i=0;i<b.length;i++)
			    	 			{
			    	 				if(b[i].length()>=10 && phnop.length()>=10)
			    	 				{
			    	 					b[i]=b[i].substring(b[i].length()-10,b[i].length() );
			    	 					phnop=phnop.substring(phnop.length()-10,phnop.length() );
			    	 					Log.d("....outnum....",b[i]+"..b[i]..outnum.."+phnop);
			    	 				}				
			    	 				if(b[i].equals(phnop))
			    	 				{
			    	 					xys=1;
			    	 				}
			    	 			}		
			    	 		}
				
			    	 		if(xys==1)
			    	 		{
			    	 			////call reject					
			    	 			try 
			    	 			{			      		   
	    	 			} 
			    	 			catch (Exception e)
			    	 			{
			    	 				// TODO: handle exception
			    	 				Toast.makeText(getApplicationContext(), "error4 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
			    	 				Log.d("error4",e.getMessage());
			    	 			}
			    	 		}
				//		String rng=blckornot(phnop);
//						if(rng.equalsIgnoreCase("Block"))
//						{
							try
							{
								
								Log.d("...rnggg..","cutng........");  
			      		//Toast.makeText(getApplicationContext(), "...rnggg..cutng....", Toast.LENGTH_LONG).show();
								telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
								Class c = Class.forName(telephonyManager.getClass().getName());
								Method m = c.getDeclaredMethod("getITelephony");
								m.setAccessible(true);
							//	ITelephony telephonyService = (ITelephony)m.invoke(telephonyManager);
							//	telephonyService.endCall();
								
								
							   //   checknum(phnop);
								
								
							 				 
							}
							catch (Exception e) 
							{
								// TODO: handle exception
								Toast.makeText(getApplicationContext(), "error5 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
								Log.d("error5",e.getMessage());
								
							}
						}
				//		}
		  }	
	   }
		 
	   

   };
   
   
   
//
//   public void checknum(String num)
//   {
//
//	   String res="";
//
//
//
//	   SQLiteDatabase sqd=openOrCreateDatabase("sqlidb", SQLiteDatabase.CREATE_IF_NECESSARY,null);
//		sqd.setVersion(1);
//		sqd.setLocale(Locale.getDefault());
//
//		String sql="create table if not exists friend(id Integer PRIMARY KEY AUTOINCREMENT, name text,phonenm text)";
//
//
//	    sqd.execSQL(sql);
//
//	    Log.d("---phonenumber", num+"");
//
//		String query="select * from friend where phonenm ='"+num+"'";
//
//		Cursor cr=sqd.rawQuery(query, null);
//
//
//		Log.d("---COUNT", cr.getCount()+"");
//
//
//		if(cr.getCount()>0){
//
//			cr.moveToFirst();
//
//			do{
//
//		    	Toast.makeText(getApplicationContext(), "res1"+cr.getString(0), Toast.LENGTH_LONG).show();
//
//		    	id.add(cr.getString(0));
//		    	name.add(cr.getString(1));
//				phonenm.add(cr.getString(2));
//
//
//				SmsManager sm=SmsManager.getDefault();
//
//				sm.sendTextMessage(cr.getString(2), null, "im very busy", null, null);
//
//			}
//
//			while(cr.moveToNext());
//
//			res="ok";
//		}
//
//		else
//		{
//			res="no";
//		}
//
//		sqd.close();
//
//
//
//   }
//
//
	
   
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
  
	private void inscall(final String imei,  final String ss,  final String type) {

	
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
	//	Toast.makeText(getApplicationContext(), dur, Toast.LENGTH_LONG).show();
		String  url="http://"+sp.getString("ip", "")+":5000/c_fetchcall";
	        
		
		SimpleDateFormat sdate=new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
		String dt=sdate.format(new Date());
		
		
		 RequestQueue queue = Volley.newRequestQueue(Calldetails.this);

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
                             	 Toast.makeText(getApplicationContext()," call Registered Successfully",Toast.LENGTH_LONG).show();



                             }
                             else
                             {
                             	 Toast.makeText(getApplicationContext(),"Registeration not completed",Toast.LENGTH_LONG).show();


                             }


                               } catch (JSONException e) {
                             e.printStackTrace();
                         }


                     }
                 }, new Response.ErrorListener() {




                 	@Override
             public void onErrorResponse(VolleyError error) {

                 Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
             }
         }){
             @Override
             protected Map<String, String> getParams()
             {
                 Map<String, String>  params = new HashMap<String, String>();
                 params.put("userid", sp.getString("lid",""));
                 params.put("type", type);
                 params.put("phnum", ss);
                 params.put("date", dt);
				 params.put("time", tm);
















				 return params;
             }
         };
        // Add the request to the RequestQueue.
         queue.add(stringRequest);
}}
