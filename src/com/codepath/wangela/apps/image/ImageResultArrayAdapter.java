package com.codepath.wangela.apps.image;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {
	public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;
		//ViewHolder viewHolder; // view lookup cache stored in tag
		// Check if an existing view is being reused; otherwise, inflate the view
		if (convertView == null) {
			//viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent, false);
			//ivImage.setTag(viewHolder);
		} else {
			//viewHolder = (ViewHolder) ivImage.getTag();
			ivImage = (SmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		return ivImage;
	}
	
	// View lookup cache
	//public static class ViewHolder {
	//	SmartImageView ivImage;
	//}
	
}
