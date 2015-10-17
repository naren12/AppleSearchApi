package com.magar.narendra.applesearchapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends Activity implements Animation.AnimationListener {


	private TextView txt;
	private Animation animBlink;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		txt=(TextView) findViewById(R.id.textView);

		animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
		animBlink.setAnimationListener(this);
		txt.startAnimation(animBlink);

		Thread thread = new Thread()
		{
			public void run(){
				try{
					sleep(5000);
				}catch(Exception e){
					System.out.println("erro :"+e.getMessage());
				}finally{
					startActivity(new Intent(SplashScreen.this,MainActivity.class));
				}
			}
		};
		thread.start();




	}


	@Override
	public void onAnimationStart(Animation animation) {
		// check for blink animation
		if (animation == animBlink) {
		}

	}

	@Override
	public void onAnimationEnd(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}





