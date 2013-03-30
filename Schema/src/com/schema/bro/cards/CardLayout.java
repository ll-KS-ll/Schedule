package com.schema.bro.cards;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.schema.bro.R;
import com.schema.bro.ks.Lesson;

public class CardLayout extends LinearLayout implements OnGlobalLayoutListener {

	public CardLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayoutObserver();
	}

	public CardLayout(Context context) {
		super(context);
		initLayoutObserver();
	}

	private void initLayoutObserver() {
		setOrientation(LinearLayout.VERTICAL);
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	public void addCard(Lesson lesson){
		Card card = new Card(this.getContext(), lesson);
		addView(card, 0);
	}
	
	public void addNextLessonCard(Lesson nextLesson, boolean during){
		NextLessonCard nextCard = new NextLessonCard(getContext(), nextLesson, during);
		addView(nextCard, 0);
	}
	
	public void addCard(Lesson lesson, int position){
		Card card = new Card(this.getContext(), lesson);
		addView(card, position);
	}
	
	@Override
	public void onGlobalLayout() {
		
		final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);

			int[] location = new int[2];

			child.getLocationOnScreen(location);

			if (location[1] > heightPx) {
				break;
			}

			child.startAnimation(AnimationUtils.loadAnimation(getContext(), R.animator.slide_down));

		}
		 
	}

}
