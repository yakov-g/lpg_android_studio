package com.yakov.goldberg.lpg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.res.AssetManager;
import android.os.Environment;
import android.text.format.Time;

public class LPGData {

	private ArrayList<JSONObject> data_arr;
	private Map<Integer, JSONObject> data_map;
	private JSONObject config_data;

	public LPGData() {
		data_arr = new ArrayList<JSONObject>();
		data_map = new HashMap<Integer, JSONObject>();
	}

	/* Read all data from InputStream */
	private String getStringFromInputStream(InputStream is) {
		String ret = "";
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				total.append(line);
			}
			r.close();
			is.close();
			ret = total.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void parseJsonTimestampAndData(String in_str) {
		JSONArray arr = null;
		try {
			JSONObject jo = new JSONObject(in_str);
			config_data = jo.getJSONObject("config_data");
			arr = jo.getJSONArray("data");
			jo = null;

			data_arr.clear();
			data_map.clear();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject item = arr.getJSONObject(i);
				data_arr.add(item);
				try {
					data_map.put(item.getInt("id") , item);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dumpToFile(FileOutputStream fos) {
		JSONObject root = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			for (int i = 0; i < data_arr.size(); i++) {
				JSONObject jo = data_arr.get(i);
				arr.put(jo);
			}

			root.put("data", arr);
			root.put("config_data", config_data);
			putStringIntoOutputStream(root.toString(), fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Read data from Assets file */
	private String getStrFromAssets(AssetManager am) {
		// read file into json string
		String ret = "";
		try {
			InputStream is = am.open("output");
			ret = getStringFromInputStream(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/* Read data from Internal file */
	public void loadDataFromFile(FileInputStream fis) {
		String str = "";
		str = getStringFromInputStream(fis);
		parseJsonTimestampAndData(str);
/*
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"myfile");
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(file));
			buf.append(str);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	public int getTimestamp() {
		int ret = 0;
		try {
			ret = config_data.getInt("timestamp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public double getMinPrice() {
		double ret = 0;
		try {
			ret = config_data.getDouble("min_price");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public double getMaxPrice() {
		double ret = 0;
		try {
			ret = config_data.getDouble("max_price");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public ArrayList<JSONObject> getArr() {
		return data_arr;
	}

	/* Copy data from Assets file into Internal file after first install */
	public void fromAssetsToFile(AssetManager am, FileOutputStream fos) {
		String str_data = getStrFromAssets(am);
		putStringIntoOutputStream(str_data, fos);
	}

	/* Write string in file. */
	private void putStringIntoOutputStream(String in_str, FileOutputStream fos) {
		try {
			byte[] contentInBytes = in_str.getBytes();
			fos.write(contentInBytes);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setConfigDataAndData(FileOutputStream fos,
			JSONObject config_data, JSONArray data) {
		JSONObject jo = new JSONObject();
		try {
			jo.put("config_data", config_data);
			jo.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String str_data = jo.toString();
		putStringIntoOutputStream(str_data, fos);
		parseJsonTimestampAndData(str_data);
	}

	public void updatePriceByKey(int idx, double price) {
		try {
			JSONObject jo = data_map.get(idx);
			jo.put("price", price);
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			String date = today.format("%d.%m.%Y");
			jo.put("updated", date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject getRecordByKey(int idx) {
		return data_map.get(idx);
	}
}
