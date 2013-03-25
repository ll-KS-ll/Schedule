package com.schema.bro.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.schema.bro.R;
import com.schema.bro.ks.Lesson;

public class Card extends LinearLayout{

	public Card(Context context){
		super(context);
	}
	
	public Card(Context context, Lesson lesson) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.card, this, true);
	}

}
