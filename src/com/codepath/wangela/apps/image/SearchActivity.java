package com.codepath.wangela.apps.image;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	TextView tvFiltered;
	TextView tvClearFilters;
	private FilterSettings filters;
	RequestParams params = new RequestParams();
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		filters = new FilterSettings();

		setupViews();

		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				customLoadMoreDataFromApi(totalItemsCount);
				// or customLoadMoreDataFromApi(page);
			}
		});
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		tvFiltered = (TextView) findViewById(R.id.tvFiltered);
		tvClearFilters = (TextView) findViewById(R.id.tvClearFilters);
	}

	private Boolean isNetworkAvailable() {
		// TODO Call this during onCreate or onImageSearch and handle network unavailable state
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}

	public void onSettings(MenuItem mi) {
		Intent i = new Intent(this, FilterSettings.class);
		i.putExtra("filters", filters);
		startActivityForResult(i, 17);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 17) {
			if (resultCode == RESULT_OK) {
				filters = (FilterSettings) data.getSerializableExtra("filters");
				params.put("imgsz", filters.getFilterSize());
				params.put("imgtype", filters.getFilterType());
				params.put("as_sitesearch", filters.getFilterSite());
				onImageSearch(btnSearch);
				tvFiltered.setVisibility(View.VISIBLE);
				tvClearFilters.setVisibility(View.VISIBLE);
			} else {
				params = null;
			}
		}
	}

	public void onImageSearch(View v) {
		imageAdapter.clear();
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG)
				.show();
		AsyncHttpClient client = new AsyncHttpClient();
		params.put("q", Uri.encode(query));
		params.put("rsz", "8");
		params.put("v", "1.0");
		params.put("start", "0");
		// Google Image Search API standard URL is
		// https://ajax.googleapis.com/ajax/services/search/images + arguments
		client.get("https://ajax.googleapis.com/ajax/services/search/images?",
				params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject(
									"responseData").getJSONArray("results");
							imageAdapter.addAll(ImageResult
									.fromJSONArray(imageJsonResults));
							Log.d("DEBUG", imageResults.toString());
							Log.d("PARAMS", params.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					public void onFailure(Throwable e, JSONObject error) {
						// Handle the failure and alert the user to retry
						Log.e("FAILURE", e.toString());
					}
				});
	}
	
	public void onClear(View v) {
		// TODO reset filter settings
		tvFiltered.setVisibility(View.INVISIBLE);
		tvClearFilters.setVisibility(View.INVISIBLE);
		params.remove("imgsz");
		params.remove("imgtype");
		params.remove("as_sitesearch");
		onImageSearch(btnSearch);
	}

	// Append more data into the adapter for endless scrolling
	public void customLoadMoreDataFromApi(int offset) {
		// This method sends out a network request and appends new
		// data items to your adapter.
		// Use the offset value and add it as a parameter to your API request to
		// retrieve paginated data.
		// Deserialize API response and then construct new objects to append to
		// the adapter
		
		AsyncHttpClient client = new AsyncHttpClient();
		String offsetString = String.valueOf(offset);
		params.put("start", offsetString);
		client.get("https://ajax.googleapis.com/ajax/services/search/images?",
				params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject(
									"responseData").getJSONArray("results");
							//imageAdapter.clear(); // don't clear on subsequent requests
							imageAdapter.addAll(ImageResult
									.fromJSONArray(imageJsonResults));
							Log.d("PARAMS", params.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable e, JSONObject error) {
						// Handle the failure and alert the user to retry
						Log.e("FAILURE", e.toString());
					}
				});

	}
}
