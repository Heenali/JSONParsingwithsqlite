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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class sqlite_display extends Activity {
	
	ArrayList<Actors> actorsList;
	Button sql;
	ActorAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlite);
		
		sql=(Button)findViewById(R.id.button1);
		actorsList = new ArrayList<Actors>();
		
		ListView listview = (ListView)findViewById(R.id.list);
		
		final DBAdapter db = new DBAdapter(sqlite_display.this);
		
		db.open();
		Cursor c = db.getAllContacts();
		if (c.moveToFirst())
		{
			do 
				
			{
				DisplayContact(c);
				
			} while (c.moveToNext());
			
			adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);
			
			listview.setAdapter(adapter);
		}
		db.close();
		
	}

	private void DisplayContact(Cursor c)
	{
		// TODO Auto-generated method stub
		//Toast.makeText(getBaseContext(),"id: " + c.getString(0) + "\n" +"Name: " + c.getString(1) + "\n" +"Email: " + c.getString(2),
		//Toast.LENGTH_LONG).show();
		
		Actors actor = new Actors();
		
		actor.setone(c.getString(0).toString());
		actor.settwo(c.getString(1).toString());
		actor.setthree(c.getString(2).toString());
		actor.setfour(c.getString(3).toString());
		actor.setfive(c.getString(4).toString());
		actor.setsix(c.getString(5).toString());
		actor.setseven(c.getString(6).toString());
		actor.seteight(c.getString(7).toString());
		
		
		
		actorsList.add(actor);
		
		
		
	}
	
	
	
	
}
