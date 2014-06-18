package com.codepath.wangela.apps.image;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		ivImage.setImageUrl(result.getFullUrl());
		
		TextView tvImageURL = (TextView) findViewById(R.id.tvImageURL);
		tvImageURL.setText(result.getVisUrl());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   if (item.getItemId() == android.R.id.home) {
	        //NavUtils.navigateUpFromSameTask(this); // keeps returning to parent activity without saving state
	        //return true;
	    	this.finish();
	    }
	    return true;
	}
	
	public void onReturn(View v) {
		this.finish();
	}
}
