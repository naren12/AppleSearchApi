package com.magar.narendra.applesearchapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.magar.narendra.applesearchapi.ImageHelper.BitmapLruCache;
import com.magar.narendra.applesearchapi.SQLite.DbAdapter;
import com.magar.narendra.applesearchapi.service.MyService;


public class Main_Activity_Details extends ActionBarActivity implements View.OnClickListener
{

	private String s_title,s_img,s_previewUrl,s_collectionName,s_collectionPrice,s_trackPrice;
	private TextView title,collectionName,collectionPrice,trackPrice;
	private ImageView img;
	private Button play,stop;
	private CardView layout_player;
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
		s_previewUrl=intent.getStringExtra("previewUrl");
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
		layout_player=(CardView) findViewById(R.id.layout_player);
		play=(Button) findViewById(R.id.play);
		play.setVisibility(Button.VISIBLE);
		stop=(Button) findViewById(R.id.stop);
		stop.setVisibility(Button.GONE);


		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		imageLoader = new ImageLoader(mRequestQueue, (ImageLoader.ImageCache) new BitmapLruCache(BitmapLruCache.getDefaultLruCacheSize()));
		imageLoader.get(s_img, ImageLoader.getImageListener(img, R.drawable.loading, R.drawable.loading));

		ConnectivityManager cn=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf=cn.getActiveNetworkInfo();
		if(nf != null && nf.isConnected()==true )
		{
			layout_player.setVisibility(View.VISIBLE);

			SharedPreferences prefs = getSharedPreferences("URLP", MODE_PRIVATE);
			String urlvalue = prefs.getString("URL", null);
			Boolean svalue = prefs.getBoolean("play", false);



			if(urlvalue==null)
			{
				play.setVisibility(Button.VISIBLE);
				stop.setVisibility(Button.GONE);
			}

			else if(urlvalue.equalsIgnoreCase(s_previewUrl))
			{

				if(svalue==true) {

					play.setVisibility(Button.GONE);
					stop.setVisibility(Button.VISIBLE);

				}
				else
				{
					play.setVisibility(Button.VISIBLE);
					stop.setVisibility(Button.GONE);
				}


			}


		}
		else
		{
			layout_player.setVisibility(View.GONE);
		}


		play.setOnClickListener(this);
		stop.setOnClickListener(this);

	}
	@Override
	public void onResume()
	{
		super.onResume();
		SharedPreferences prefs = getSharedPreferences("URLP", Main_Activity_Details.MODE_PRIVATE);
		String urlvalue = prefs.getString("URL", null);
		Boolean svalue = prefs.getBoolean("play", false);


		if(urlvalue==null)
		{
			play.setVisibility(Button.VISIBLE);
			stop.setVisibility(Button.GONE);
		}
		else if(urlvalue.equalsIgnoreCase(s_previewUrl) )
		{
			if(svalue==true) {

				play.setVisibility(Button.GONE);
				stop.setVisibility(Button.VISIBLE);

			}
			else
			{
				play.setVisibility(Button.VISIBLE);
				stop.setVisibility(Button.GONE);
			}



		}

	}

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {

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


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.play:
				play.setVisibility(Button.GONE);
				stop.setVisibility(Button.VISIBLE);
				stopService(new Intent(this, MyService.class));

				SharedPreferences prefs = getSharedPreferences("URLP", Main_Activity_Details.MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("URL", s_previewUrl);
				editor.putString("s_title", s_title);
				editor.commit();


				Intent intentDemo=new Intent(getApplicationContext(),MyService.class);
				intentDemo.putExtra("URL",s_previewUrl);
				startService(intentDemo);
				break;
			case R.id.stop:

				stop.setVisibility(Button.GONE);
				play.setVisibility(Button.VISIBLE);
				stopService(new Intent(this, MyService.class));
				break;
		}

	}
}
