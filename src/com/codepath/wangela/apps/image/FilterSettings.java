package com.codepath.wangela.apps.image;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

public class FilterSettings extends Activity implements Serializable {
	private String size = "";
	private String type = "";
	private String site = "";
	FilterSettings filters;
	RadioGroup rgSize;
	RadioGroup rgType;
	EditText etSite;
	int rbSizeSelected;
	int rbTypeSelected;
	//String sizeFilter;
	//String typeFilter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		rgSize = (RadioGroup) findViewById(R.id.rgSize);
		rgType = (RadioGroup) findViewById(R.id.rgType);
		etSite = (EditText) findViewById(R.id.etSite);
		
		filters = (FilterSettings) getIntent().getSerializableExtra("filters");
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
//	public RequestParams toParams() {
		// formatting the URL based on filters like ?type=faces&color=blue 
	//	RequestParams params = new RequestParams();
		//params.put("imgcolor", rbImgColor);
	//	params.put("size", sizeFilter);
	//	params.put("type", typeFilter);
		//params.put("as_rights", rbAsRights);
	//	return params;
//	}

	public String getFilterSize() {
		return size;
	}
	
	public String getFilterType() {
		return type;
	}
	
	public String getFilterSite() {
		return site;
	}
	
	public void onSubmit(View v) {
		// Pass filter settings back to Search (parent) activity
		rbSizeSelected = rgSize.getCheckedRadioButtonId();
		RadioButton rbSize = (RadioButton) findViewById(rbSizeSelected);
		size = rbSize.getTag().toString();
		
		rbTypeSelected = rgType.getCheckedRadioButtonId();
		RadioButton rbType = (RadioButton) findViewById(rbTypeSelected);
		type = rbType.getTag().toString();
		
		site = etSite.getText().toString();
		
		filters.size = size;
		filters.type = type;
		filters.site = site;
		
		Intent i = new Intent();
		i.putExtra("filters", filters);
		setResult(RESULT_OK, i);
		finish();
	}
}
