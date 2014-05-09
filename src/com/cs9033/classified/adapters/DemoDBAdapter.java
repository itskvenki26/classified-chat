package com.cs9033.classified.adapters;

import java.security.SecureRandom;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cs9033.classified.crypto.SecureMessage;

public class DemoDBAdapter extends SQLiteOpenHelper {
	public static final String TAG = "DemoDBAdapter";
	private static final String Create_demo_post_table = "create table demo_post(_id integer primary key autoincrement,"
			+ " post text)";
	private static final String Create_demo_keys_table = "create table demo_keys(_id integer primary key autoincrement,"
			+ " current_mac text, current_e text, old_mac text)";
	private static final String Create_demo_keys_exchange_table = "create table demo_x_keys(_id integer primary key autoincrement,"
			+ " current_mac text, current_e text)";

	private static final String DB_NAME = "demo";
	private static final int DB_VERSION = 1;

	private Context context;

	public DemoDBAdapter(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Create_demo_keys_exchange_table);
		db.execSQL(Create_demo_keys_table);
		db.execSQL(Create_demo_post_table);

		// SecureRandom sr = new SecureRandom();
		// byte[] keyBytes = new byte[256];
		// sr.nextBytes(keyBytes);
		ContentValues insertValues = new ContentValues();
		String new_mackey;
		new_mackey = SecureMessage.getNewMacKey();

		/*
		 * new String((new SecretKeySpec("11111111".getBytes("ASCII"),
		 * "HmacSHA1").getEncoded()),"ASCII");
		 */
		// sr.nextBytes(keyBytes);
		String ekey = SecureMessage.getNewEKey();
		/*
		 * new String((new SecretKeySpec( "22222222" .getBytes("ASCII"),
		 * "HmacSHA1" ).getEncoded()), "ASCII");
		 */
		// sr.nextBytes(keyBytes);
		String old_mac_key = SecureMessage.getNewMacKey();
		/*
		 * new String((new SecretKeySpec( "00000000" .getBytes ("ASCII"),
		 * "HmacSHA1" ).getEncoded()), "ASCII");
		 */

		insertValues.put("current_mac", new_mackey);
		insertValues.put("current_e", ekey);

		db.insert("demo_x_keys", null, insertValues);

		/*
		 * execSQL("Insert into demo_keys(current_mac,current_e,old_mac) values('"
		 * + new_mackey + "','" + ekey + "','" + old_mac_key + "')");
		 */
		insertValues.put("current_mac", new_mackey);
		db.insert("demo_keys", null, insertValues);
		/*
		 * db.execSQL("Insert into demo_x_keys(current_mac,current_e) values('"
		 * + new_mackey + "','" + ekey + "')");
		 */
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public String[] getKeys() {
		Log.d(TAG, "getKeys");
		final SQLiteDatabase db = getWritableDatabase();

		Cursor cursor = db.query("demo_keys", new String[] { "current_mac",
				"current_e", "old_mac", "_id" }, null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
			String current_mac = cursor.getString(cursor
					.getColumnIndex("current_mac"));
			String current_e = cursor.getString(cursor
					.getColumnIndex("current_e"));
			String old_mac = cursor.getString(cursor.getColumnIndex("old_mac"));
			db.close();
			return new String[] { current_mac, current_e, old_mac };
		}
		db.close();
		return null;
	}

	public long updateKeys(String current_mac, String current_e, String old_mac) {
		final SQLiteDatabase db = getWritableDatabase();

		db.delete("demo_keys", null, null);

		ContentValues values = new ContentValues();
		values.put("current_mac", current_mac);
		values.put("current_e", current_e);
		values.put("old_mac", old_mac);
		long result = db.insert("demo_keys", null, values);
		db.close();
		if (result > -1)
			return result;

		db.close();
		return -1L;
	}

	public String[] getXKeys() {
		Log.d(TAG, "getXKeys");
		final SQLiteDatabase db = getWritableDatabase();

		Cursor cursor = db.query("demo_x_keys", new String[] { "current_mac",
				"current_e", "_id" }, null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
			String current_mac = cursor.getString(cursor
					.getColumnIndex("current_mac"));
			String current_e = cursor.getString(cursor
					.getColumnIndex("current_e"));
			db.close();
			return new String[] { current_mac, current_e };
		}
		db.close();
		return null;
	}

	public long updateXKeys(String current_mac, String current_e) {
		final SQLiteDatabase db = getWritableDatabase();

		db.delete("demo_x_keys", null, null);

		ContentValues values = new ContentValues();
		values.put("current_mac", current_mac);
		values.put("current_e", current_e);
		long result = db.insert("demo_x_keys_table", null, values);
		db.close();
		if (result > -1)
			return result;

		db.close();
		return -1L;
	}

	public String[] getPost() {
		Log.d(TAG, "getPosts");
		final SQLiteDatabase db = getWritableDatabase();

		Cursor cursor = db.query("demo_post", new String[] { "post", "_id" },
				null, null, null, null, null);

		/*
		 * if (cursor != null) { cursor.moveToFirst(); String current_mac =
		 * cursor.getColumnName(cursor .getColumnIndex("current_mac")); String
		 * current_e = cursor.getColumnName(cursor
		 * .getColumnIndex("current_e")); db.close(); return new String[] {
		 * current_mac, current_e, old_mac }; } db.close();
		 */

		if (cursor != null) {
			ArrayList<String> posts = new ArrayList<String>();
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {

				String post = cursor.getString((cursor.getColumnIndex("post")));
				posts.add(post);

			}
			return (posts.toArray(new String[] {}));
		}
		return new String[] {};
	}

	public long updatePost(String post) {
		final SQLiteDatabase db = getWritableDatabase();

		// db.delete("demo_x_keys", null, null);

		ContentValues values = new ContentValues();
		values.put("post", post);
		long result = db.insert("demo_pos", null, values);
		db.close();
		if (result > -1)
			return result;
		return -1L;
	}

}
