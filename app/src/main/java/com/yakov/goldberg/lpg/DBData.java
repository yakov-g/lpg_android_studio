package com.yakov.goldberg.lpg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBData {

	final static String url_db_get = "http://lpg.site40.net/lpg_get.php";
	final static String url_db_set = "http://lpg.site40.net/lpg_set.php";
	static InputStream is = null;
	
	public JSONObject getDBConfig()
	{  
		JSONObject ret = null;
		try
		{
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
          params.add(new BasicNameValuePair("op", "get_config"));
        
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpPost httpPost = new HttpPost(url_db_get);
		  httpPost.setEntity(new UrlEncodedFormEntity(params));
		  
		  HttpResponse httpResponse = httpClient.execute(httpPost);
          HttpEntity httpEntity = httpResponse.getEntity();
          is = httpEntity.getContent();
		}
		catch (UnsupportedEncodingException e)
		{
            e.printStackTrace();
        }
		catch (ClientProtocolException e)
		{
            e.printStackTrace();
        }
	    catch (IOException e) {
            e.printStackTrace();
        }
  			
		try {
     	   BufferedReader r = new BufferedReader(new InputStreamReader(is));
     	   StringBuilder total = new StringBuilder();
     	   String line;
     	   while ((line = r.readLine()) != null) {
     	       total.append(line);
     	   }
     	   String js = total.toString();
     	   JSONObject jo = new JSONObject(js);
     	   if (jo.getInt("status") == 1)
     	   {	   
     	     ret = jo.getJSONObject("config_data");
     	   }
         }
        catch (Exception e) {
         e.printStackTrace();
        }
	  return ret;
	}
	
	public JSONArray getDBJsonArray()
	{  
		JSONArray ret = null;
		try
		{
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
          params.add(new BasicNameValuePair("op", "get_all"));
        
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpPost httpPost = new HttpPost(url_db_get);
		  httpPost.setEntity(new UrlEncodedFormEntity(params));
		  
		  HttpResponse httpResponse = httpClient.execute(httpPost);
          HttpEntity httpEntity = httpResponse.getEntity();
          is = httpEntity.getContent();
		}
		catch (UnsupportedEncodingException e)
		{
            e.printStackTrace();
        }
		catch (ClientProtocolException e)
		{
            e.printStackTrace();
        }
	    catch (IOException e) {
            e.printStackTrace();
        }
  			
		try {
     	   BufferedReader r = new BufferedReader(new InputStreamReader(is));
     	   StringBuilder total = new StringBuilder();
     	   String line;
     	   while ((line = r.readLine()) != null) {
     	       total.append(line);
     	   }
     	   String js = total.toString();
     	   JSONObject jo = new JSONObject(js);
     	   if (jo.getInt("status") == 1)
 	         ret = jo.getJSONArray("data");
         }
        catch (Exception e) {
         e.printStackTrace();
       }
	  return ret;
	}
	
	public String updateDBPrice(String lat, String lng, String price)
	{  
		String ret = "";
		try
		{
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
          params.add(new BasicNameValuePair("op", "set_price"));
          params.add(new BasicNameValuePair("lat", lat));
          params.add(new BasicNameValuePair("lng", lng));
          params.add(new BasicNameValuePair("price", price));
        
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpPost httpPost = new HttpPost(url_db_set);
		  httpPost.setEntity(new UrlEncodedFormEntity(params));
		  
		  HttpResponse httpResponse = httpClient.execute(httpPost);
          HttpEntity httpEntity = httpResponse.getEntity();
          is = httpEntity.getContent();
		}
		catch (UnsupportedEncodingException e)
		{
            e.printStackTrace();
        }
		catch (ClientProtocolException e)
		{
            e.printStackTrace();
        }
	    catch (IOException e) {
            e.printStackTrace();
        }
		
		try {
	     	   BufferedReader r = new BufferedReader(new InputStreamReader(is));
	     	   StringBuilder total = new StringBuilder();
	     	   String line;
	     	   while ((line = r.readLine()) != null) {
	     	       total.append(line);
	     	   }
	     	   String js = total.toString();
	     	   JSONObject jo = new JSONObject(js);
	     	   return jo.toString();
	         }
	        catch (Exception e) {
	         e.printStackTrace();
	       }
  			
		
	  return ret;
	}
}
