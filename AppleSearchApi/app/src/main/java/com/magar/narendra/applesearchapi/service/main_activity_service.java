package com.magar.narendra.applesearchapi.service;


import com.magar.narendra.applesearchapi.json.JsonParser;
import com.magar.narendra.applesearchapi.model.main_activity_info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class main_activity_service
{

	public static ArrayList<main_activity_info> getAllData(String url)
	{
		String result= JsonParser.parseJSON(url);
		ArrayList<main_activity_info> main_items=new ArrayList<main_activity_info>();

		try 
		{
			JSONObject parent = new JSONObject(result);
			JSONArray jr = parent.getJSONArray("results");


			for(int i=0;i<jr.length();i++) {
				JSONObject obj = jr.getJSONObject(i);

					int id = -1;
					String title = "";
					String img = "";
					String primaryGenreName = "";
				    String collectionName="";
				    String collectionPrice="";
				 	String trackPrice="";

					if (obj.has("id")) {
						id = Integer.parseInt(obj.getString("id"));

					}
					if (obj.has("trackName")) {
						title = obj.getString("trackName");


					}
					if (obj.has("artworkUrl100")) {
						img = obj.getString("artworkUrl100");


					}
					if (obj.has("primaryGenreName")) {
						primaryGenreName = obj.getString("primaryGenreName");


					}
					if (obj.has("collectionName")) {
						collectionName = obj.getString("collectionName");


					}
					if (obj.has("collectionPrice")) {
						collectionPrice = obj.getString("collectionPrice");


					}
					if (obj.has("trackPrice")) {
						trackPrice = obj.getString("trackPrice");


					}


				main_activity_info items = new main_activity_info(title, img,collectionName,collectionPrice,trackPrice);
				main_items.add(items);
				}

			
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return main_items;
	}
}
