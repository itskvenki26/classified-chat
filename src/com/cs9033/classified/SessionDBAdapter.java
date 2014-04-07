package com.cs9033.classified;

import java.util.ArrayList;
import java.util.List;

import Models.Comments;
import Models.CurrentUser;
import Models.Posts;
import Models.UserData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SessionDBAdapter {

	/***** if debug is set true then it will show all Logcat message ****/
	public static final boolean DEBUG = true;

	/******************** Logcat TAG ************/
	public static final String TAG = "DBAdapter";

	/******************** Table Fields ************/
	public static final String KEY_ID = "_id";

	public static final String KEY_USER_EMAIL_ID = "user_email";
	public static final String KEY_USER_NAME = "user_name";
	public static final String KEY_USER_PH_NO = "user_phone_num";

	public static final String KEY_POST_CREATOR = "post_creator";
	public static final String KEY_POST_TIME = "post_time";
	public static final String KEY_POST_MSG = "post_message";

	public static final String KEY_COMMENT_CREATOR = "comment_creator";
	public static final String KEY_COMMENT_TIME = "comment_time";
	public static final String KEY_COMMENT_MSG = "comment_message";
	public static final String KEY_COMMENT_POST_ID = "comment_post_id";

	// public static final String KEY_DEVICE_IMEI = "device_imei";
	// public static final String KEY_DEVICE_NAME = "device_name";
	// public static final String KEY_DEVICE_EMAIL = "device_email";
	// public static final String KEY_DEVICE_REGID = "device_regid";

	/******************** Database Name ************/
	public static final String DATABASE_NAME = "DB_sqllite";

	/**** Database Version (Increase one if want to also upgrade your database) ****/
	public static final int DATABASE_VERSION = 1;// started at 1

	/** Table names */
	public static final String USER_TABLE = "tbl_user";
	// public static final String DEVICE_TABLE = "tbl_device";
	public static final String POSTS_TABLE = "tbl_posts";
	public static final String COMMENTS_TABLE = "tbl_comments";

	/*** Set all table with comma seperated like USER_TABLE,ABC_TABLE ***/
	private static final String[] ALL_TABLES = { USER_TABLE, POSTS_TABLE,
			COMMENTS_TABLE };

	/** Create table syntax */

	private static final String USER_CREATE = "create table tbl_user(_id integer primary key autoincrement,  user_email_lid text not null, user_name text not null,user_ph_no text not null);";
	private static final String POSTS_CREATE = "create table tbl_user(_id integer primary key autoincrement,  creator text not null, time text not null,message text not null);";
	private static final String COMMENTS_CREATE = "create table tbl_user(_id integer primary key autoincrement,  creator text not null, time text not null,message text not null, post_id text not null);";

	// private static final String DEVICE_CREATE =
	// "create table tbl_device(_id integer primary key autoincrement, device_name text not null,device_email text not null,device_regid text not null,device_imei text not null);";

	/**** Used to open database in syncronized way ****/
	private static DataBaseHelper DBHelper = null;

	protected SessionDBAdapter() {
	}

	/******************* Initialize database *************/
	public static void init(Context context) {
		if (DBHelper == null) {
			if (DEBUG)
				Log.d("DBAdapter", context.toString());
			DBHelper = new DataBaseHelper(context);
		}
	}

	/***** Main Database creation INNER class ******/
	private static class DataBaseHelper extends SQLiteOpenHelper {
		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (DEBUG)
				Log.d(TAG, "new DB create");
			try {
				// db.execSQL(USER_MAIN_CREATE);
				db.execSQL(USER_CREATE);
				// db.execSQL(DEVICE_CREATE);
				db.execSQL(POSTS_CREATE);
				db.execSQL(COMMENTS_CREATE);
			} catch (Exception exception) {
				if (DEBUG)
					Log.i(TAG, "Exception onCreate() exception");
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (DEBUG)
				Log.w(TAG, "Upgrading database from version" + oldVersion
						+ "to" + newVersion + "...");

			for (String table : ALL_TABLES) {
				db.execSQL("DROP TABLE IF EXISTS " + table);
			}
			onCreate(db);
		}

	} // Inner class closed

	/**** Open database for insert,update,delete in syncronized manner ****/
	private static synchronized SQLiteDatabase open() throws SQLException {
		return DBHelper.getWritableDatabase();
	}

	// Insert installing device data
	/*
	 * public static void addDeviceData(String DeviceName, String DeviceEmail,
	 * String DeviceRegID,String DeviceIMEI) { try{ final SQLiteDatabase db =
	 * open();
	 * 
	 * String imei = sqlEscapeString(DeviceIMEI); String name =
	 * sqlEscapeString(DeviceName); String email = sqlEscapeString(DeviceEmail);
	 * String regid = sqlEscapeString(DeviceRegID);
	 * 
	 * ContentValues cVal = new ContentValues(); cVal.put(KEY_DEVICE_IMEI,
	 * imei); cVal.put(KEY_DEVICE_NAME, name); cVal.put(KEY_DEVICE_EMAIL,
	 * email); cVal.put(KEY_DEVICE_REGID, regid);
	 * 
	 * db.insert(DEVICE_TABLE, null, cVal); db.close(); // Closing database
	 * connection } catch (Throwable t) { Log.i("Database", "Exception caught: "
	 * + t.getMessage(), t); } }
	 */

	// Adding new user

	public static void addUserData(UserData uData) {
		try {
			final SQLiteDatabase db = open();

			String email_id = sqlEscapeString(uData.getEMAIL());
			String name = sqlEscapeString(uData.getName());
			String ph_no = sqlEscapeString(uData.getPH_NO());

			ContentValues cVal = new ContentValues();
			cVal.put(KEY_USER_EMAIL_ID, email_id);
			cVal.put(KEY_USER_NAME, name);
			cVal.put(KEY_USER_PH_NO, ph_no);
			db.insert(USER_TABLE, null, cVal);
			db.close(); // Closing database connection
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	// Getting single user data
	public static UserData getUserData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(USER_TABLE, new String[] { KEY_ID,
				KEY_USER_EMAIL_ID, KEY_USER_NAME, KEY_USER_PH_NO }, KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();

		UserData data = new UserData(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		// return contact
		return data;
	}

	public static void addPostsData(Posts pt) {
		try {
			final SQLiteDatabase db = open();

			String creator = sqlEscapeString(pt.getCREATOR());
			String time = sqlEscapeString(pt.getTIME());
			String msg = sqlEscapeString(pt.getMessage());

			ContentValues cVal = new ContentValues();
			cVal.put(KEY_POST_CREATOR, creator);
			cVal.put(KEY_POST_TIME, time);
			cVal.put(KEY_POST_MSG, msg);
			db.insert(POSTS_TABLE, null, cVal);
			db.close(); // Closing database connection

			Log.i("Database", "Added Post " + pt.getMessage());
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public static void addPostsData(String message) {

		Posts post = new Posts(0, CurrentUser.getName(), "00.00", message);
		addPostsData(post);

	}

	public static Posts getPostsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(USER_TABLE, new String[] { KEY_ID,
				KEY_POST_CREATOR, KEY_POST_TIME, KEY_POST_MSG }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Posts data = new Posts(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		// return contact
		return data;
	}

	public static Cursor getAllPostsData() {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(USER_TABLE, new String[] { KEY_POST_MSG },
				null, null, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		return cursor;
	}

	public static void addCommentsData(Comments ct) {
		try {
			final SQLiteDatabase db = open();

			String creator = sqlEscapeString(ct.getCREATOR());
			String time = sqlEscapeString(ct.getTIME());
			String msg = sqlEscapeString(ct.getMessage());
			String post_id = sqlEscapeString(ct.getPOST_ID());
			ContentValues cVal = new ContentValues();
			cVal.put(KEY_COMMENT_CREATOR, creator);
			cVal.put(KEY_COMMENT_TIME, time);
			cVal.put(KEY_COMMENT_MSG, msg);
			cVal.put(KEY_COMMENT_POST_ID, post_id);
			db.insert(POSTS_TABLE, null, cVal);
			db.close(); // Closing database connection
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public static void addCommentsData(String message, String postId) {
		Comments ct = new Comments(0, CurrentUser.getName(), "00.00", message,
				postId);
		addCommentsData(ct);
	}

	public static Comments getCommentsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(USER_TABLE, new String[] { KEY_ID,
				KEY_POST_CREATOR, KEY_POST_TIME, KEY_POST_MSG,
				KEY_COMMENT_POST_ID }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Comments data = new Comments(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4));
		// return contact
		return data;
	}

	// Getting All user data
	public static List<UserData> getAllUserData() {
		List<UserData> contactList = new ArrayList<UserData>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + USER_TABLE + " ORDER BY "
				+ KEY_ID + " desc";

		final SQLiteDatabase db = open();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				UserData data = new UserData();
				data.setID(Integer.parseInt(cursor.getString(0)));
				data.setEMAIL(cursor.getString(1));
				data.setName(cursor.getString(2));
				data.setPH_NO(cursor.getString(3));

				// Adding contact to list
				contactList.add(data);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}

	// Getting users Count
	public static int getUserDataCount() {
		String countQuery = "SELECT  * FROM " + USER_TABLE;
		final SQLiteDatabase db = open();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	// Getting installed device have self data or not
	/*
	 * public static int validateDevice() { String countQuery =
	 * "SELECT  * FROM " + DEVICE_TABLE; final SQLiteDatabase db = open();
	 * Cursor cursor = db.rawQuery(countQuery, null);
	 * 
	 * int count = cursor.getCount(); cursor.close();
	 * 
	 * // return count return count; }
	 */

	// Getting distinct user data use in spinner
	public static List<UserData> getDistinctUser() {
		List<UserData> contactList = new ArrayList<UserData>();
		// Select All Query
		String selectQuery = "SELECT  distinct(user_email_id),user_name  FROM "
				+ USER_TABLE + "ORDER BY " + KEY_ID + " desc";

		final SQLiteDatabase db = open();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				UserData data = new UserData();

				data.setEMAIL(cursor.getString(0));
				data.setName(cursor.getString(1));
				// Adding contact to list
				contactList.add(data);
			} while (cursor.moveToNext());
		}
		cursor.close();

		return contactList;
	}

	// Getting imei already in user table or not
	public static int validateNewMessageUserData(String Email) {
		int count = 0;
		try {
			String countQuery = "SELECT " + KEY_ID + "FROM " + USER_TABLE
					+ "WHERE user_email_id='" + Email + "'";

			final SQLiteDatabase db = open();
			Cursor cursor = db.rawQuery(countQuery, null);

			count = cursor.getCount();
			cursor.close();
		} catch (Throwable t) {
			count = 10;
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
		return count;
	}

	// Escape string for single quotes (Insert,Update)
	private static String sqlEscapeString(String aString) {
		String aReturn = "";

		if (null != aString) {
			// aReturn = aString.replace("'", "''");
			aReturn = DatabaseUtils.sqlEscapeString(aString);
			// Remove the enclosing single quotes ...
			aReturn = aReturn.substring(1, aReturn.length() - 1);
		}

		return aReturn;
	}

	// UnEscape string for single quotes (show data)
	@SuppressWarnings("unused")
	private static String sqlUnEscapeString(String aString) {

		String aReturn = "";

		if (null != aString) {
			aReturn = aString.replace("''", "'");
		}

		return aReturn;
	}
}