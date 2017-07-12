package com.wingnity.jsonparsingtutorial;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts.Intents;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	ArrayList<Actors> actorsList;
	Button sql;
	ActorAdapter adapter;
	 Boolean isInternetPresent = false;
	    ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sql=(Button)findViewById(R.id.button1);
		actorsList = new ArrayList<Actors>();
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		final DBAdapter db = new DBAdapter(MainActivity.this);
        // check for Internet status
        if (isInternetPresent)
        {
        	db.open();
			db.deleteContact();
			db.close();	
			
        	new JSONAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");
            
        } else
        {
           Toast.makeText(getApplicationContext(), "No Internet Connection",20).show();
                   
        }
		
		
		
		
		ListView listview = (ListView)findViewById(R.id.list);
		adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);
		
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), actorsList.get(position).getone(), Toast.LENGTH_LONG).show();				
			}
		});
		sql.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(getApplicationContext(),sqlite_display.class);
				
			    startActivity(i);
				
				
			}
		});
	}

	private void DisplayContact(Cursor c)
	{
		// TODO Auto-generated method stub
		//Toast.makeText(getBaseContext(),"id: " + c.getString(0) + "\n" +"Name: " + c.getString(1) + "\n" +"Email: " + c.getString(2),
			//Toast.LENGTH_LONG).show();
	}
	class JSONAsyncTask extends AsyncTask<String, Void, Boolean>
	{
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}
		
		@Override
		protected Boolean doInBackground(String... urls) 
		{
			try {
				
				//------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				// StatusLine stat = response.getStatusLine();
				int status = response.getStatusLine().getStatusCode();

				if (status == 200)
				{
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					final DBAdapter db = new DBAdapter(MainActivity.this);
				
					JSONObject jsono = new JSONObject(data);
					JSONArray jarray = jsono.getJSONArray("actors");
					
					
					
					for (int i = 0; i < jarray.length(); i++) 
					{
						JSONObject object = jarray.getJSONObject(i);
					
						Actors actor = new Actors();
						
						actor.setone(object.getString("name"));
						actor.settwo(object.getString("description"));
						actor.setthree(object.getString("dob"));
						actor.setfour(object.getString("country"));
						actor.setfive(object.getString("height"));
						actor.setsix(object.getString("spouse"));
						actor.setseven(object.getString("children"));
						actor.seteight(object.getString("image"));
						
						db.open();
						db.insertContact(object.getString("name").toString(),object.getString("description").toString(),object.getString("dob"),object.getString("country"),object.getString("height"),object.getString("spouse"),object.getString("image"));
						db.close();
						
						actorsList.add(actor);
					}
					return true;
				}
				
				//------------------>>
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			adapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

		}
	}
	
	

	
	
	
}
