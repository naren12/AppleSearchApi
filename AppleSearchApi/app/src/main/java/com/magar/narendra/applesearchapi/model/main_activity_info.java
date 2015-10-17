package com.magar.narendra.applesearchapi.model;

public class main_activity_info
{

	private int id;
	private String title;
	private String img;
	private String primaryGenreName;
	private String collectionName;
	private String collectionPrice;
	private String trackPrice;
	
	public main_activity_info(String title, String img, String collectionName,String collectionPrice,String trackPrice)
	{
		super();

		this.title = title;
		this.img=img;
		this.primaryGenreName=primaryGenreName;
		this.collectionName=collectionName;
		this.collectionPrice=collectionPrice;
		this.trackPrice=trackPrice;
	}
	

	public int getId()
	{
		return id;
	}
	public String getTitle() {return title;}
	public String getImg() 
	{
		return img;
	}
	public String getPrimaryGenreName() { return primaryGenreName; }
	public String getCollectionName() { return collectionName; }
	public String getcollectionPrice() { return collectionPrice; }
	public String getTrackPrice() { return  trackPrice; }
  

}
