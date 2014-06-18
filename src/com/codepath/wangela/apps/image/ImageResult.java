package com.codepath.wangela.apps.image;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {
	private static final long serialVersionUID = -337638616613839359L;
	private String fullUrl;
	private String thumbUrl;
	private String visUrl;

	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
			this.visUrl = json.getString("visibleUrl");
		} catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
			this.visUrl = null;
		}
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}
	
	public String getVisUrl() {
		return visUrl;
	}

	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray imageJsonResults) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int x=0; x < imageJsonResults.length(); x++) {
			try {
				results.add(new ImageResult(imageJsonResults.getJSONObject(x)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
