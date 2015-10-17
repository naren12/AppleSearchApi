package com.magar.narendra.applesearchapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.magar.narendra.applesearchapi.ImageHelper.BitmapLruCache;
import com.magar.narendra.applesearchapi.SQLite.DbAdapter;


public class Main_Activity_Details extends ActionBarActivity
{

	private String s_title,s_img,s_collectionName,s_collectionPrice,s_trackPrice;
	private TextView title,collectionName,collectionPrice,trackPrice;
	private ImageView img;
	private RequestQueue mRequestQueue;
	private ImageLoader imageLoader;
	private DbAdapter dbadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_details);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent=getIntent();
		s_title= intent.getStringExtra("title");
		s_img= intent.getStringExtra("img");
		s_collectionName= intent.getStringExtra("collectionName");
		s_collectionPrice= intent.getStringExtra("collectionPrice");
		s_trackPrice= intent.getStringExtra("trackPrice");

		title=(TextView) findViewById(R.id.title);
		title.setText(s_title);

		collectionName=(TextView) findViewById(R.id.collectionName);
		collectionName.setText(s_collectionName);

		collectionPrice=(TextView) findViewById(R.id.collectionPrice);
		collectionPrice.setText(s_collectionPrice);

		trackPrice=(TextView) findViewById(R.id.trackPrice);
		trackPrice.setText(s_trackPrice);

		
		img=(ImageView) findViewById(R.id.imageview);

		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		imageLoader = new ImageLoader(mRequestQueue, (ImageLoader.ImageCache) new BitmapLruCache(BitmapLruCache.getDefaultLruCacheSize()));
		imageLoader.get(s_img, ImageLoader.getImageListener(img,R.drawable.loading, R.drawable.loading));

	}

	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	     // Respond to the action bar's Up/Home button
	     
	     case android.R.id.home:
	    	 finish();
			 Main_Activity_Details.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	         return true;
	     }
	     return super.onOptionsItemSelected(item);
	 }
	@Override
	public void onBackPressed()
	{
		finish();
		Main_Activity_Details.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}


}
