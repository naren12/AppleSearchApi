package com.magar.narendra.applesearchapi;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.magar.narendra.applesearchapi.SQLite.DbAdapter;
import com.magar.narendra.applesearchapi.adapter.Main_Activity_Adapter;
import com.magar.narendra.applesearchapi.model.main_activity_info;
import com.magar.narendra.applesearchapi.service.MyService;
import com.magar.narendra.applesearchapi.service.main_activity_service;
import com.magar.narendra.applesearchapi.service.main_activity_service_offline;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {


	public ListView lst;
	private Main_Activity_Adapter adapter;
	private List<main_activity_info> items;
	private ProgressBar pd;
	private String title,img,previewUrl,collectionName,collectionPrice,trackPrice,url;
	private DbAdapter dbadapter;
	private Cursor cur;
	private List<String> list1, list2;
	private InputStream is = null;
	private String line = null,urlvalue,songtitle;
	private String result = null;
	private TextView error_message,stitle;
	private CardView layout_player;
	private Button play,stop;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		error_message=(TextView) findViewById(R.id.errormessage);
		error_message.setVisibility(View.GONE);
		stitle=(TextView) findViewById(R.id.title);

		layout_player=(CardView) findViewById(R.id.layout_player);
		layout_player.setVisibility(View.GONE);


		lst = (ListView) findViewById(R.id.listView);
		items=new ArrayList<main_activity_info>();
		adapter=new Main_Activity_Adapter(getApplicationContext(),items);
		lst.setAdapter(adapter);
		dbadapter=new DbAdapter(getApplicationContext());
		pd=(ProgressBar) findViewById(R.id.progressBar);
		lst.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position,long id)
				{

					main_activity_info item=(main_activity_info)adapter.getItemAtPosition(position);
					title =item.getTitle();
					img=item.getImg();
					previewUrl=item.getpreviewUrl();
					collectionName=item.getCollectionName();
					collectionPrice=item.getcollectionPrice();
					trackPrice=item.getTrackPrice();

					Intent intentDemo=new Intent(getApplicationContext(),Main_Activity_Details.class);

					intentDemo.putExtra("title",title);
					intentDemo.putExtra("img",img);
					intentDemo.putExtra("previewUrl",previewUrl);
					intentDemo.putExtra("collectionName", collectionName);
					intentDemo.putExtra("collectionPrice", collectionPrice);
					intentDemo.putExtra("trackPrice", trackPrice);

					startActivity(intentDemo);



				}

			});

       ConnectivityManager cn=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf=cn.getActiveNetworkInfo();
       if(nf != null && nf.isConnected()==true )
       {

    	   pd.setVisibility(View.VISIBLE);
		   url="https://itunes.apple.com/search?term=bb+king&limit=20";

		   try{
			   SharedPreferences prefs = getSharedPreferences("URLP", MainActivity.MODE_PRIVATE);
			   urlvalue = prefs.getString("URL", null);
			   songtitle= prefs.getString("s_title",null);
			   Boolean svalue = prefs.getBoolean("play", false);

			   if(!urlvalue.isEmpty() || urlvalue!=null)
			   {

				   layout_player.setVisibility(View.VISIBLE);
				   play=(Button) findViewById(R.id.play);
				   stop=(Button) findViewById(R.id.stop);
				   stop.setVisibility(View.GONE);
				   play.setOnClickListener(this);
				   stop.setOnClickListener(this);
				   stitle.setText(songtitle);

				   if(svalue==true) {

					   play.setVisibility(Button.GONE);
					   stop.setVisibility(Button.VISIBLE);

				   }
				   else
				   {
					   ;
					   play.setVisibility(Button.VISIBLE);
					   stop.setVisibility(Button.GONE);
				   }


			   }
			   else
			   {
				   layout_player.setVisibility(View.GONE);
			   }

		   }
		   catch (Exception e)
		   {

		   }



    	   Thread thread=new Thread(doInBackground);
   		   thread.start();


		   Thread threadD=new Thread(doInBackgroundDataSave);
		   threadD.start();


       }
       else
       {
		   pd.setVisibility(View.GONE);
		   error_message.setVisibility(View.VISIBLE);
		   error_message.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View v) {
				   ConnectivityManager cn=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				   NetworkInfo nf=cn.getActiveNetworkInfo();
				   if(nf != null && nf.isConnected()==true ) {
					   url="https://itunes.apple.com/search?term=bb+king&limit=20";
					   Thread thread = new Thread(doInBackground);
					   thread.start();

					   Thread threadD = new Thread(doInBackgroundDataSave);
					   threadD.start();
				   }
				   else
				   {
					   Toast.makeText(getApplicationContext(),"Enable WIFI/DATA",Toast.LENGTH_SHORT).show();
				   }
			   }
		   });

		   Thread threadO=new Thread(doInBackgroundOffline);
		   threadO.start();


	   }


    }
	@Override
	public void onResume()
	{
		super.onResume();
		ConnectivityManager cn=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf=cn.getActiveNetworkInfo();
		if(nf != null && nf.isConnected()==true ) {


			try {
				SharedPreferences prefs = getSharedPreferences("URLP", MainActivity.MODE_PRIVATE);
				urlvalue = prefs.getString("URL", null);
				songtitle= prefs.getString("s_title",null);
				Boolean svalue = prefs.getBoolean("play", false);


				if(!urlvalue.isEmpty() || urlvalue!=null)
				{

					layout_player.setVisibility(View.VISIBLE);
					play=(Button) findViewById(R.id.play);
					stop=(Button) findViewById(R.id.stop);
					stitle.setText(songtitle);
					play.setOnClickListener(this);
					stop.setOnClickListener(this);


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
				else
				{
					layout_player.setVisibility(View.GONE);
				}


			} catch (Exception e) {

			}
		}

	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		stopService(new Intent(this, MyService.class));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.play:
				play.setVisibility(Button.GONE);
				stop.setVisibility(Button.VISIBLE);
				stopService(new Intent(this, MyService.class));
				Intent intentDemo = new Intent(getApplicationContext(), MyService.class);
				intentDemo.putExtra("URL",urlvalue);
				startService(intentDemo);
				break;
			case R.id.stop:

				stop.setVisibility(Button.GONE);
				play.setVisibility(Button.VISIBLE);
				stopService(new Intent(this, MyService.class));
				break;
		}
	}

	Runnable doInBackground=new Runnable()
	 {

		@Override
		public void run()
		{


			try
			{

				items = main_activity_service.getAllData(url);

			}
			catch(Exception exception)
			{

			}
			try {
				runOnUiThread(runOnUi);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	 };

	 Runnable runOnUi=new Runnable()
	 {

		@Override
		public void run()
		{
			int limit = 0;
				for(main_activity_info jitems:items)
				{
						 adapter.add(jitems);

				}

			adapter.notifyDataSetChanged();
			pd.setVisibility(View.INVISIBLE);
			error_message.setVisibility(View.GONE);

		}

	 };
	Runnable doInBackgroundDataSave=new Runnable()
	{

		@Override
		public void run()
		{


			try
			{
				webservice();

			}
			catch(Exception exception)
			{

			}
			try {
				runOnUiThread(runOnUiD);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	};

	Runnable runOnUiD=new Runnable()
	{

		@Override
		public void run()
		{
			pd.setVisibility(View.GONE);


		}

	};




	private void webservice() {
		// TODO Auto-generated method stub

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}
		catch (Exception e) {
			Log.e("Webservice", e.toString());
		}
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();

			while((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}

			is.close();
			result = sb.toString();


		}
		catch (Exception e) {
			Log.e("Webservice", e.toString());
		}
		try {

			items = main_activity_service_offline.getAllData(result);

			try
			{

				dbadapter.open();

				dbadapter.deleteAll();

				for(main_activity_info offjitems:items)
				{
					dbadapter.insertItem(offjitems);

				}

				dbadapter.close();

			}
			catch(Exception exception)
			{

			}


		}catch (Exception e) {
			Log.e("Webservice", e.toString());
		}

	}

	Runnable doInBackgroundOffline=new Runnable()
	{

		@Override
		public void run()
		{


			try
			{
				//onResume();
				adapter.clear();
				dbadapter.open();

				try
				{
					cur=dbadapter.getItemInfo();

					if(cur.moveToFirst()){
						do{
							String title = cur.getString(cur.getColumnIndex(DbAdapter.TITLE));
							String img = cur.getString(cur.getColumnIndex(DbAdapter.IMAGE));
							String collectionName = cur.getString(cur.getColumnIndex(DbAdapter.COLLECTION_NAME));
							String collectionPrice = cur.getString(cur.getColumnIndex(DbAdapter.COLLECTION_PRICE));
							String trackPrice = cur.getString(cur.getColumnIndex(DbAdapter.TRACK_PRICE));

							main_activity_info item= new main_activity_info(title,img,collectionName,collectionPrice,trackPrice);
							items.add(item);

							if(!title.isEmpty() || title!=null)
							{
								pd.setVisibility(View.GONE);
								error_message.setVisibility(View.GONE);

							}



						}
						while(cur.moveToNext());

						adapter.notifyDataSetChanged();

					}
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dbadapter.close();
				adapter.notifyDataSetChanged();


			}
			catch(Exception exception)
			{

			}


		}

	};

	@Override
	public void onBackPressed() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}


}





