package com.schema.bro.ks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.schema.bro.R.color;

public class TextLoaderAnimator {

	private AnimationDrawable animation;
	private Context context;
	private ImageView imageView;
	private int textSize;
	
	public TextLoaderAnimator(Context context, ImageView imageView, String text) {
		this.context = context;
		this.imageView = imageView;
		textSize = 24;
		
		createNewAnimation(text);
	}
	
	public TextLoaderAnimator(Context context, ImageView imageView, String text, int textSize) {
		this.context = context;
		this.imageView = imageView;
		this.textSize = textSize;
		
		createNewAnimation(text);
	}
	
	public void setTextSize(int textSize){
		this.textSize = textSize;
	}
	
	public void setScaleType(ScaleType scaleType){
		imageView.setScaleType(scaleType);
	}
	
	public void stop(){
		animation.stop();
	}

	public void start(){
		animation.start();
	}
	
	private void createNewAnimation(String text){
		animation = new AnimationDrawable();
		for (int n = 0; n < 4; n++)
			animation.addFrame(getDrawableLoadingFrame(text, n), 300);
		animation.setOneShot(false);
		imageView.setImageDrawable(animation);
		animation.start();
	}
	
	public void changeText(String text){
		createNewAnimation(text);
	}
	
	public BitmapDrawable getDrawableLoadingFrame(String text, int frame){
		final float densityMultiplier = context.getResources().getDisplayMetrics().density;
		final float scaledTextSize = textSize * densityMultiplier;
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color.gray);
		paint.setTextSize(scaledTextSize);
		
		final float size_text = paint.measureText(text + "...");
		final Rect bounds = new Rect();
	    paint.getTextBounds(text , 0, text.length(), bounds);
		
		Bitmap bitmap = Bitmap.createBitmap((int) size_text, bounds.height(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		for(int i=0; i<frame; i++)
			text += ".";
		canvas.drawText(text, 0, bounds.height() - bounds.bottom , paint);
		return new BitmapDrawable(context.getResources(), bitmap);
	}
	
}
