package com.magar.narendra.applesearchapi.SQLite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.magar.narendra.applesearchapi.model.main_activity_info;


public class DbAdapter
{
	DbHelper dbHelper;
	SQLiteDatabase db;
	private static final String DB_NAME ="applesearchapi.db";
	private static int VERSION =3;
	private static final String TABLE_ONE ="tbl_apple";
	private static final String KEY ="_id";
	public static final String TITLE="title";
	public static final String IMAGE ="image";
	//public static final String PRIMARYGENRE_NAME ="primaryGenreName";
	public static final String COLLECTION_NAME ="collectionName";
	public static final String COLLECTION_PRICE ="collectionPrice";
	public static final String TRACK_PRICE ="trackPrice";

	
	
private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_ONE+"(" +KEY+" INTEGER PRIMARY KEY AUTOINCREMENT,"
+TITLE+" TEXT, "+IMAGE+" TEXT, "+COLLECTION_NAME+" TEXT, "+COLLECTION_PRICE+" TEXT, "+TRACK_PRICE+" TEXT)";


 public DbAdapter(Context context)
 {
	 dbHelper=new DbHelper(context,DB_NAME,null,VERSION);
	 
 }
  public DbAdapter open() throws SQLException
  {
	  db=dbHelper.getWritableDatabase();
	  return this;
	  
  }
  
  public void close()
  {
	  db.close();
	  
  }
 
  public Cursor getItemInfo() throws Exception
  {

	return db.rawQuery("SELECT * FROM " + TABLE_ONE , null);

  }
	public void deleteAll() {

		db.delete(TABLE_ONE, null, null);
	}
  
public void insertItem(main_activity_info item)
{
	ContentValues cv=new ContentValues();
	cv.put(TITLE, item.getTitle());
	cv.put(IMAGE, item.getImg());
	//cv.put(PRIMARYGENRE_NAME, item.getPrimaryGenreName());
	cv.put(COLLECTION_NAME, item.getCollectionName());
	cv.put(COLLECTION_PRICE, item.getcollectionPrice());
	cv.put(TRACK_PRICE, item.getTrackPrice());
	
	db.insert(TABLE_ONE, TITLE, cv);
}
  
  
  
 private static class DbHelper extends SQLiteOpenHelper
 {

	public DbHelper(Context context, String name, CursorFactory factory,int version) 
	{
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{


	}
	 
 }

}
