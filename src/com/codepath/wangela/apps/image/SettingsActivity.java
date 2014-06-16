package com.codepath.wangela.apps.image;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends Activity implements Serializable {
	public String colorFilter;
	public String typeFilter;
	CheckBox cbTypeFilter;
	//FilterSettings filters;
	
	public void toParams() {
		// formatting the URL based on filters like ?type=faces&color=blue 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		String hello = getIntent().getStringExtra("hello");
	//	filters = (FilterSettings) getIntent().getSerializableExtra("filters");
		cbTypeFilter = (CheckBox) findViewById(R.id.checkBox1);
	}
	
	public void onSubmit(View v) {
		// Pass filter settings back to Search (parent) activity
		String typeValue = cbTypeFilter.getText().toString();
		//filters.typeFilter = typeValue;
		Intent i = new Intent();
		//i.putExtra("filters", filters);
		setResult(RESULT_OK, i);
		finish();
	}
}
