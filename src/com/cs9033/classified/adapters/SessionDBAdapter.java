package com.cs9033.classified.adapters;

import java.util.ArrayList;
import java.util.Calendar;
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
	private static final String TAG = "DBAdapter";

	/******************** Table Fields ************/
	public static final String KEY_ID = "_id";

	public static final String U_EMAIL_ID = "user_email";
	public static final String U_NAME = "user_name";
	public static final String U_PH_NO = "user_phone_num";

	public static final String P_CREATOR = "creator";
	public static final String P_TIME = "time";
	public static final String P_MSG = "message";

	public static final String C_CREATOR = "comment_creator";
	public static final String C_TIME = "comment_time";
	public static final String C_MSG = "comment_message";
	public static final String C_POST_ID = "comment_post_id";

	/******************** Database Name ************/
	public static final String DATABASE_NAME = "DB_sqllite";

	/**** Database Version (Increase one if want to also upgrade your database) ****/
	public static final int DATABASE_VERSION = 3;// started at 1

	/** Table names */
	public static final String TABLE_USER = "tbl_user";
	public static final String TABLE_POSTS = "tbl_posts";
	public static final String TABLE_COMMENTS = "tbl_comments";

	/*** Set all table with comma seperated like USER_TABLE,ABC_TABLE ***/
	private static final String[] ALL_TABLES = { TABLE_USER, TABLE_POSTS,
			TABLE_COMMENTS };
	public static final String[] ALL_POSTS = { "_id", P_CREATOR, P_MSG, P_TIME };
	// public static final String[] ALL_USERS = { "_id", P_CREATOR, P_MSG,
	// P_TIME };
	public static final String[] ALL_COMMENTS = { "_id", C_CREATOR, C_MSG,
			C_TIME, C_POST_ID };

	/** Create table syntax */

	private static final String USER_CREATE = "create table " + TABLE_USER
			+ " (" + KEY_ID + " integer primary key autoincrement,  "
			+ U_EMAIL_ID + "  text not null, " + U_NAME + "  text not null, "
			+ U_PH_NO + "  text not null);";

	private static final String POSTS_CREATE = "create table " + TABLE_POSTS
			+ " (" + KEY_ID + " integer primary key autoincrement,  "
			+ P_CREATOR + "  text not null, " + P_TIME + "  text not null, "
			+ P_MSG + "  text not null);";

	private static final String COMMENTS_CREATE = "create table "
			+ TABLE_COMMENTS + " (" + KEY_ID
			+ " integer primary key autoincrement,  " + C_CREATOR
			+ "  text not null, " + C_TIME + "  text not null, " + C_MSG
			+ "  text not null, " + C_POST_ID + "  text not null);";

	// private static final String DEVICE_CREATE =
	// "create table tbl_device(_id integer primary key autoincrement, device_name text not null,device_email text not null,device_regid text not null,device_imei text not null);";

	/**** Used to open database in syncronized way ****/
	private DataBaseHelper DBHelper = null;

	private Context context;

	@SuppressWarnings("unused")
	private SessionDBAdapter() {
	}

	public SessionDBAdapter(Context context) {
		this.context = context;
		init();
	}

	/******************* Initialize database *************/
	public void init() {
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
	private synchronized SQLiteDatabase open() throws SQLException {
		if (DBHelper == null) {
			Log.d(TAG, "open: DBHelper is null");
		}
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

	public void addUserData(UserData uData) {
		try {
			final SQLiteDatabase db = open();

			String email_id = sqlEscapeString(uData.getEMAIL());
			String name = sqlEscapeString(uData.getName());
			String ph_no = sqlEscapeString(uData.getPH_NO());

			ContentValues cVal = new ContentValues();
			cVal.put(U_EMAIL_ID, email_id);
			cVal.put(U_NAME, name);
			cVal.put(U_PH_NO, ph_no);
			db.insert(TABLE_USER, null, cVal);
			db.close(); // Closing database connection
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	// Getting single user data
	public UserData getUserData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID, U_EMAIL_ID,
				U_NAME, U_PH_NO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		UserData data = new UserData(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		// return contact
		return data;
	}

	public void addPostsData(Posts pt) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();

			String creator = sqlEscapeString(pt.getCREATOR());
			String time = sqlEscapeString(pt.getTIME());
			String msg = sqlEscapeString(pt.getMessage());

			ContentValues cVal = new ContentValues();
			cVal.put(P_CREATOR, creator);
			cVal.put(P_TIME, time);
			cVal.put(P_MSG, msg);
			db.insert(TABLE_POSTS, null, cVal);
			db.close(); // Closing database connection

			Log.i("Database", "Added Post " + pt.getMessage());
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	private String getTime() {

		Calendar c = Calendar.getInstance();
		String time = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + " "
				+ c.get(Calendar.HOUR_OF_DAY) + "." + c.get(Calendar.MINUTE);
		return time;
	}

	public void addPostsData(String message) {

		String time = getTime();
		Posts post = new Posts(0, CurrentUser.getName(), time, message);
		addPostsData(post);

	}

	public Posts getPostsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID, P_CREATOR,
				P_TIME, P_MSG }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Posts data = new Posts(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		// return contact
		return data;
	}

	public Cursor getAllPostsData() {
		Log.d(TAG, "getAllPostData");

		final SQLiteDatabase db = open();
		Log.d(TAG, "getAllPostData: got Writer");
		Cursor cursor = db.query(TABLE_POSTS, ALL_POSTS, null, null, null,
				null, null, null);
		Log.d(TAG, "getAllPostData: queried Cursor");
		if (cursor != null) {
			cursor.moveToFirst();
			Log.d(TAG,
					"getAllPostData: cursor is not null"
							+ cursor.getColumnCount() + " " + cursor.getCount());
		}
		return cursor;
	}

	// Time is set to system time by default for now. Change it later

	public void addCommentsData(Comments ct) {

		final SQLiteDatabase db = open();

		String creator = sqlEscapeString(ct.getCREATOR());
		String time = getTime();
		String msg = sqlEscapeString(ct.getMessage());
		String post_id = sqlEscapeString(ct.getPOST_ID());
		ContentValues cVal = new ContentValues();
		cVal.put(C_CREATOR, creator);
		cVal.put(C_TIME, time);
		cVal.put(C_MSG, msg);
		cVal.put(C_POST_ID, post_id);
		db.insert(TABLE_COMMENTS, null, cVal);
		db.close(); // Closing database connection

		// catch (Throwable t) {
		// Log.i("Database", "Exception caught: " + t.getMessage(), t);
		// }
	}

	public void addCommentsData(String message, String postId) {
		Comments ct = new Comments(0, CurrentUser.getName(), null, message,
				postId);
		addCommentsData(ct);
	}

	public Comments getCommentsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID, P_CREATOR,
				P_TIME, P_MSG, C_POST_ID }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Comments data = new Comments(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4));
		// return contact
		return data;
	}

	public Cursor getAllCommentsData() {
		Log.d(TAG, "getAllCommentsData");

		final SQLiteDatabase db = open();
		Log.d(TAG, "getAllCommentsData: got Writer");
		Cursor cursor = db.query(TABLE_COMMENTS, ALL_COMMENTS, null, null,
				null, null, null, null);
		Log.d(TAG, "getAllCommentsData: queried Cursor");
		if (cursor != null) {
			cursor.moveToFirst();
			Log.d(TAG,
					"getAllCommentsData: cursor is not null"
							+ cursor.getColumnCount() + " " + cursor.getCount());
		}
		return cursor;
	}

	// Getting All user data
	public List<UserData> getAllUserData() {
		List<UserData> contactList = new ArrayList<UserData>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USER + " ORDER BY "
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
	public int getUserDataCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER;
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
	public List<UserData> getDistinctUser() {
		List<UserData> contactList = new ArrayList<UserData>();
		// Select All Query
		String selectQuery = "SELECT  distinct(user_email_id),user_name  FROM "
				+ TABLE_USER + "ORDER BY " + KEY_ID + " desc";

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
	public int validateNewMessageUserData(String Email) {
		int count = 0;
		try {
			String countQuery = "SELECT " + KEY_ID + "FROM " + TABLE_USER
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