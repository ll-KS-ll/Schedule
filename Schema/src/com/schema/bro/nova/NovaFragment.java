package com.schema.bro.nova;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.schema.bro.R;
import com.schema.bro.R.color;

public class NovaFragment extends Fragment{

	Bitmap novaImage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.nova_fragment, container, false);
		final ImageView image = (ImageView) view.findViewById(R.id.novaFragmentImageView);
		
		final AnimationDrawable animation = new AnimationDrawable();
		for(int n=0; n<4; n++)
			animation.addFrame(getDrawableLoadingFrame(n), 300);
		animation.setOneShot(false);
		image.setImageDrawable(animation);
		animation.start();
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(novaImage != null)
			novaImage.recycle();
		novaImage = null;
	}

	public void setImageView(Bitmap bitmap){
		if(bitmap == null){
			Log.e("NovaFragment", "Bitmap is null");
			return;
		}
		
		novaImage = Bitmap.createBitmap(bitmap);
		
		if(getView() == null){
			Log.e("NovaFragment", "View is null");
			return;
		}
		
		final ImageView image = (ImageView) getView().findViewById(R.id.novaFragmentImageView);
		if(image != null){
			image.setScaleType(ScaleType.FIT_XY);
			image.setImageBitmap(novaImage);
		}else{
			Log.e("NovaFragment", "ImageView is null");
		}
	}
	
	public BitmapDrawable getDrawableLoadingFrame(int n){
		Bitmap bitmap = Bitmap.createBitmap(235, 55, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(50);
		paint.setColor(color.gray);
		String text = "Loading";
		for(int i=0; i<n; i++)
			text += ".";
		canvas.drawText(text, 5, 40, paint);
		return new BitmapDrawable(getResources(), bitmap);
	}

}
