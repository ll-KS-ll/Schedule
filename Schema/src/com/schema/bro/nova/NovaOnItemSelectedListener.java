package com.schema.bro.nova;

import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.schema.bro.R;

public class NovaOnItemSelectedListener implements OnItemSelectedListener {

	private NovaPagerFragment fragment;

	public NovaOnItemSelectedListener(Fragment fragment) {
		this.fragment = (NovaPagerFragment) fragment;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int pos, long arg3) {
		String classURL = "not_set";
		
		TypedArray classIDs = view.getResources().obtainTypedArray(R.array.class_ids);
		if(classIDs.length() > pos)
			classURL = classIDs.getString(pos);
		classIDs.recycle();
		
		if(fragment.isResumed())
			fragment.changeClass(classURL, pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Do nothing
	}

}
