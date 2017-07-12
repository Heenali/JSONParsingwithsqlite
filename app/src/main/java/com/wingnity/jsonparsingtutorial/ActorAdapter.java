package com.wingnity.jsonparsingtutorial;

import java.io.InputStream;
import java.util.ArrayList;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActorAdapter extends ArrayAdapter<Actors> {
	ArrayList<Actors> actorList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public ActorAdapter(Context context, int resource, ArrayList<Actors> objects) 
	{
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		actorList = objects;
	}
 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			holder.tvDescription = (TextView) v.findViewById(R.id.tvDescriptionn);
			holder.tvDOB = (TextView) v.findViewById(R.id.tvDateOfBirth);
			holder.tvCountry = (TextView) v.findViewById(R.id.tvCountry);
			holder.tvHeight = (TextView) v.findViewById(R.id.tvHeight);
			holder.tvSpouse = (TextView) v.findViewById(R.id.tvSpouse);
			holder.tvChildren = (TextView) v.findViewById(R.id.tvChildren);
			v.setTag(holder);
		} 
		else
		{
			holder = (ViewHolder) v.getTag();
		}
		
		UrlImageViewHelper.setUrlDrawable(holder.imageview, actorList.get(position).geteight().toString(), R.drawable.ff);
		
		holder.tvName.setText(actorList.get(position).gettwo());
		holder.tvDescription.setText(actorList.get(position).getthree());
		holder.tvDOB.setText("B'day: " + actorList.get(position).getfour());
		holder.tvCountry.setText(actorList.get(position).getfive());
		holder.tvHeight.setText("Height: " + actorList.get(position).getsix());
		holder.tvSpouse.setText("Spouse: " + actorList.get(position).getseven());
		holder.tvChildren.setText("Children: " + actorList.get(position).getone());
		return v;

	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView tvName;
		public TextView tvDescription;
		public TextView tvDOB;
		public TextView tvCountry;
		public TextView tvHeight;
		public TextView tvSpouse;
		public TextView tvChildren;

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
}