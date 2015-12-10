package com.yakov.goldberg.lpg;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

public class BackgroundService extends IntentService {

	public BackgroundService()
	{
		super("BackgroundService");
	}
	protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        Uri u = workIntent.getData();
        String op = u.getQueryParameter("op");

        Intent localIntent = null;

        DBData dbdata = new DBData();
        if (op.equals("get_timestamp"))
        {
        	JSONObject config_data = dbdata.getDBConfig();
        	if (config_data == null) return;
        	String config_data_str = config_data.toString();
	   	    String uriString = String.format("lpg://?answer=time&config_data=%s", config_data_str);
            localIntent = new Intent("AnswerIntent").putExtra("extra", uriString);
        }
        else if (op.equals("get_all"))
        {
        	JSONObject config_data = dbdata.getDBConfig();
        	if (config_data == null) return;
        	String config_data_str = config_data.toString();
        	
        	JSONArray arr = dbdata.getDBJsonArray();
        	if (arr == null) return;
        	String data_str = arr.toString();
        	
        	String uriString = String.format("lpg://?answer=all&config_data=%s&data=%s", config_data_str, data_str);
        	localIntent = new Intent("AnswerIntent").putExtra("extra", uriString);
        	
        	//ArrayList<String> params = new ArrayList<String>();
            //params.add(int_str);
            //params.add(arr_str);
        	//localIntent = new Intent("AnswerIntent").putStringArrayListExtra("data", params);
        } 
        else if (op.equals("update_price"))
        {
        	String lat = u.getQueryParameter("lat");
        	String lng = u.getQueryParameter("lng");
        	String price = u.getQueryParameter("price");
        	dbdata.updateDBPrice(lat, lng, price);
        	
        	String uriString = String.format("lpg://?answer=price");
        	localIntent = new Intent("AnswerIntent").putExtra("extra", uriString);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}