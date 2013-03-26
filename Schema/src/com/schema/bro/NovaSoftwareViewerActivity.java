package com.schema.bro;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class NovaSoftwareViewerActivity extends Activity {

	private ImageView image;
	private int screenWidth;
	private int screenHeight;
	private int day = 0;
	private Intent intent;
	
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		int themeID = mPrefs.getInt("theme_int", 0);
		super.setTheme(themeID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view_activity);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenWidth = metrics.widthPixels; 
		screenHeight = metrics.heightPixels;
		
		image = (ImageView) findViewById(R.id.imageView1);
		
		intent = getIntent();
		
		setImageView();	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.days, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case R.id.monday:
        	day = 1;
        	setImageView();
            return true;
        case R.id.tuesday:
        	day = 2;
        	setImageView();
            return true;
        case R.id.wednesday:
        	day = 4;
        	setImageView();
            return true;
        case R.id.thursday:
        	day = 8;
        	setImageView();
            return true;
        case R.id.friday:
        	day = 16;
        	setImageView();
            return true;
        case R.id.week:
        	day = 0;
        	setImageView();
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	public void setImageView(){
		
		Log.d("viewer", "w: " + screenWidth + " h: " + screenHeight);
		String ImageUrl = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=80740/sv-se&type=1&id={" 
				+ intent.getExtras().getString("url")+ "}&period=&week=&mode=0&printer=0&colors=32&head=0&clock=0&foot=0&"
				+ "day=" + day + "&width=" + screenWidth + "&height=" + screenHeight + "&maxwidth=2000&maxheight=2000.png";
		
		image.setImageBitmap(LoadImageFromWebOperations(ImageUrl));
	}

	
	public static Bitmap LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src");
	        Bitmap bitmap = Bitmap.createBitmap(((BitmapDrawable)d).getBitmap());
	        return bitmap;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
}
