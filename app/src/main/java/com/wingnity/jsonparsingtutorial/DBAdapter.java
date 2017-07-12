package com.wingnity.jsonparsingtutorial;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ONE = "one";
	public static final String KEY_TWO = "two";
	public static final String KEY_THREE = "three";
	public static final String KEY_FOUR = "four";
	public static final String KEY_FIVE = "five";
	public static final String KEY_SIX = "six";
	public static final String KEY_SEVEN = "seven";
	
	
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "MyDB";
	private static final String DATABASE_TABLE = "contacts";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE =
			"create table contacts (_id integer primary key autoincrement, "
					+ "one text not null, two text not null,three text not null,four text not null,five text not null,six text not null,seven text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}
	
	
	//---opens the database---
	public DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	
	//---closes the database---
	public void close()
	{
		DBHelper.close();
	}
	
	
	//---insert a contact into the database---
	public long insertContact(String one, String two,String three,String four,String five,String six,String seven)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ONE, one);
		initialValues.put(KEY_TWO, two);
		initialValues.put(KEY_THREE, three);
		initialValues.put(KEY_FOUR, four);
		initialValues.put(KEY_FIVE, five);
		initialValues.put(KEY_SIX, six);
		initialValues.put(KEY_SEVEN, seven);
		
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	
	//---deletes a particular contact---
	public int deleteContact()
	{
		//return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		return db.delete(DATABASE_TABLE, null, null);
	}
	
	
	//---retrieves all the contacts---
	public Cursor getAllContacts()
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ONE,KEY_TWO,KEY_THREE,KEY_FOUR,KEY_FIVE,KEY_SIX,KEY_SEVEN}, 
				null, null, null, null, null);
		
	}
	
	
	//---retrieves a particular contact---
	public Cursor getContact(long rowId) throws SQLException
	{	
		Cursor mCursor =db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
				KEY_ONE, KEY_TWO,KEY_THREE,KEY_FOUR,KEY_FIVE,KEY_SIX,KEY_SEVEN}, KEY_ROWID + "=" + rowId, null,
				null, null, null, null);
		if (mCursor != null) {
   			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	
	//---updates a contact---
	public boolean updateContact(long rowId, String one, String two,String three,String four,String five,String six,String seven)
	{
		ContentValues args = new ContentValues();
		
		args.put(KEY_ONE, one);
		args.put(KEY_TWO, two);
		args.put(KEY_THREE, three);
		args.put(KEY_FOUR, four);
		args.put(KEY_FIVE, five);
		args.put(KEY_SIX, six);
		args.put(KEY_SEVEN, seven);
		
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}

