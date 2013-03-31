package com.schema.bro.nova;

import java.io.InputStream;
import java.net.URL;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.schema.bro.R;

public class NovaFragment extends Fragment{

	private ImageView image;
	private static int screenWidth;
	private static int screenHeight;
	private int day = 0;
	private String classURL;
	private static final String URL_FIRST = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=80740/sv-se&type=1&id={";
	private static final String URL_SECOND = "}&period=&week=&mode=0&printer=0&colors=32&head=0&clock=0&foot=0&";
	private static final String URL_THIRD = "&maxwidth=2000&maxheight=2000.png";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		classURL = prefs.getString("class_url", "not_set");
		
		// Should only use one layout
		View view;
		if(!classURL.equals("not_set")){
			view = inflater.inflate(R.layout.image_view_activity, container, false);
			image = (ImageView) view.findViewById(R.id.imageView1);
		}else{
			view = inflater.inflate(R.layout.nova_no_content, container, false);
		}
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenWidth = metrics.widthPixels; 
		screenHeight = metrics.heightPixels;
		
		if(!classURL.equals("not_set"))
			setImageView();
	}

	public void setImageView(){
		Log.d("viewer", "w: " + screenWidth + " h: " + screenHeight);
		
		String ImageUrl = URL_FIRST + classURL + URL_SECOND
				+ "day=" + day + "&width=" + screenWidth/2 + "&height=" + screenHeight/2 + URL_THIRD;
		
		image.setImageBitmap(LoadImageFromWebOperations(ImageUrl));
	}

	public void setClassURL(String classURL){
		this.classURL = classURL;
	}
	
	public static Bitmap LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src");
	        Bitmap bitmap = Bitmap.createBitmap(((BitmapDrawable)d).getBitmap());
	        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, false);
	        return resizedBitmap;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
}
