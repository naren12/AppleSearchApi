package com.magar.narendra.applesearchapi.json;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JsonParser 
{
	public static String result = null;

	public static String parseJSON(String URL) 
	{
		InputStream is = null;

		try 
		{
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}
		catch (Exception e) 
		{
			Log.e("JsonParser", "Error in http connection." + e.toString());
		}
		try 
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");

			}
			is.close();
			result = sb.toString();

		} catch (Exception e) {
			Log.e("JsonParser", "Error converting result." + e.toString());
		}
		return result;
	}


}
