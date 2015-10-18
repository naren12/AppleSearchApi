package com.magar.narendra.applesearchapi.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;
import com.magar.narendra.applesearchapi.ImageHelper.BitmapLruCache;
import com.magar.narendra.applesearchapi.model.main_activity_info;
import java.util.List;
import com.magar.narendra.applesearchapi.R;


public class Main_Activity_Adapter extends  ArrayAdapter<main_activity_info>
{
	


	private LayoutInflater inflater;
	public static String imgurl="";
	private RequestQueue mRequestQueue;
   	private ImageLoader imageLoader;
    private int mCount = 20;
	public Main_Activity_Adapter(Context context, List<main_activity_info> newsadapter)
	{
		super(context,R.layout.activity_main_adapter,newsadapter);
		// TODO Auto-generated constructor stub
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
		mRequestQueue = Volley.newRequestQueue(getContext());
		imageLoader = new ImageLoader(mRequestQueue, (ImageCache) new BitmapLruCache(BitmapLruCache.getDefaultLruCacheSize()));




	}

	
	public View getView(int postion,View contentView,ViewGroup parent)
	{
		View item=inflater.inflate(R.layout.activity_main_adapter,parent,false);

		TextView tv1=(TextView) item.findViewById(R.id.title);
		TextView tv2=(TextView) item.findViewById(R.id.description);
		ImageView img=(ImageView) item.findViewById(R.id.imageview);

		main_activity_info items=getItem(postion);

		tv1.setText(items.getTitle());
		tv2.setText(items.getCollectionName());
		imgurl=items.getImg();

		imageLoader.get(imgurl, ImageLoader.getImageListener(img, R.drawable.loading, R.drawable.loading));


		return item;	
	}


	
}
