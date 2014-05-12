package com.cs9033.classified.adapters;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cs9033.classified.models.ChatRoom;
import com.cs9033.classified.models.Comment;
import com.cs9033.classified.models.MyProfile;
import com.cs9033.classified.models.Post;
import com.cs9033.classified.models.User;

public class ChatRoomsDBAdapter extends SQLiteOpenHelper {

	private static final String TAG = "ChatRoomsDBAdapter";

	/***** if debug is set true then it will show all Logcat message ****/
	// public static final boolean DEBUG = true;
	/******************** Logcat TAG ************/
	// private static final String TAG = "DBAdapter";

	/******************** Table Fields ************/
	public static final String KEY_ID = "_id";

	public static final String U_EMAIL_ID = "email";
	public static final String U_NAME = "name";
	public static final String U_PH_NO = "phone_num";
	public static final String U_XMPP_HOST = "xmpp_host";
	public static final String U_XMPP_SERVER = "xmpp_server";
	public static final String U_XMPP_PORT = "xmpp_port";
	public static final String U_XMPP_USER_NAME = "xmpp_user_name";
	public static final String U_CR_ID = "user_cr_id";

	public static final String P_CR_ID = "cr_id";
	public static final String P_TITLE = "title";
	public static final String P_MSG = "message";

	public static final String C_P_ID = "p_id";
	public static final String C_CR_ID = "c_rid";
	public static final String C_MSG = "comment_message";

	public static final String CR_NAME = "name";
	public static final String CR_TIME = "time";
	public static final String CR_DESCRIPTION = "desc";
	public static final String CR_CURRENT_MAC = "current_mac";
	public static final String CR_OLD_MAC = "old_mac";
	public static final String CR_CURRENT_E = "current_e";

	public static final String MP_EMAIL_ID = "email";
	public static final String MP_NAME = "name";
	public static final String MP_PH_NO = "phone_num";
	public static final String MP_XMPP_HOST = "xmpp_host";
	public static final String MP_XMPP_SERVER = "xmpp_server";
	public static final String MP_XMPP_PORT = "xmpp_port";
	public static final String MP_XMPP_USER_NAME = "xmpp_user_name";
	public static final String MP_XMPP_PASSWORD = "xmpp_password";
	public static final String MP_CR_ID = "cr_id";

	/******************** Database Name ************/
	public static final String DATABASE_NAME = "classified";

	/**** Database Version (Increase one if want to also upgrade your database) ****/
	public static final int DATABASE_VERSION = 1;// started at 1

	/** Table names */

	public static final String TABLE_CHATROOMS = "chat_room";
	public static final String TABLE_USER = "user";
	public static final String TABLE_POSTS = "posts";
	public static final String TABLE_COMMENTS = "comments";
	public static final String TABLE_MY_PROFILE = "my_profile";

	/*** Set all table with comma separated like USER_TABLE,ABC_TABLE ***/
	public static final String[] ALL_TABLES = { TABLE_USER, TABLE_POSTS,
			TABLE_COMMENTS, TABLE_CHATROOMS, TABLE_MY_PROFILE };
	public static final String[] ALL_POSTS = { P_CR_ID, P_TITLE, P_MSG, KEY_ID };

	public static final String[] ALL_COMMENTS = { C_P_ID, C_CR_ID, C_MSG,
			KEY_ID };
	public static final String[] ALL_CR = { CR_NAME, CR_TIME, CR_DESCRIPTION,
			CR_CURRENT_MAC, CR_OLD_MAC, CR_CURRENT_E, KEY_ID };
	public static final String[] ALL_USERS = { U_EMAIL_ID, U_NAME, U_PH_NO,
			U_XMPP_HOST, U_XMPP_PORT, U_XMPP_SERVER, U_XMPP_USER_NAME, KEY_ID,
			U_CR_ID };
	public static final String[] ALL_My_PROFILE = { MP_EMAIL_ID, MP_NAME,
			MP_PH_NO, MP_XMPP_HOST, MP_XMPP_PORT, MP_XMPP_SERVER,
			MP_XMPP_USER_NAME, MP_XMPP_PASSWORD, KEY_ID };

	/** Create table syntax */

	private static final String USER_CREATE = "create table " + TABLE_USER
			+ " (" + KEY_ID + " integer primary key autoincrement,  "
			+ U_EMAIL_ID + "  text not null, " + U_NAME + "  text not null, "
			+ U_PH_NO + " text UNIQUE not null, " + U_XMPP_PORT
			+ "integer not null, " + U_XMPP_HOST + " text not null, "
			+ U_XMPP_SERVER + " text not null, " + U_XMPP_USER_NAME
			+ " text not null, " + U_CR_ID + ", integer not null );";

	private static final String POSTS_CREATE = "create table " + TABLE_POSTS
			+ " ( " + KEY_ID + " integer primary key autoincrement, " + P_CR_ID
			+ " integer not null, " + P_TITLE + " text not null, " + P_MSG
			+ " text not null, CONSTRAINT unq UNIQUE (" + P_TITLE + ", "
			+ P_CR_ID + " ) );";

	private static final String COMMENTS_CREATE = "create table "
			+ TABLE_COMMENTS + " ( " + KEY_ID
			+ " integer primary key autoincrement, " + C_P_ID
			+ " integer not null, " + C_CR_ID + " integer not null, " + C_MSG
			+ " text not null );";

	private static final String CR_CREATE = "create table " + TABLE_CHATROOMS
			+ " ( " + KEY_ID + " integer primary key autoincrement, " + CR_NAME
			+ " text UNIQUE, " + CR_TIME + " text not null, " + CR_DESCRIPTION
			+ " text not null, " + CR_CURRENT_MAC + " integer not null, "
			+ CR_OLD_MAC + " integer not null, " + CR_CURRENT_E
			+ " integer not null );";

	private static final String MY_PROFILE_CREATE = "create table "
			+ TABLE_MY_PROFILE + " ( " + KEY_ID
			+ " integer primary key autoincrement, " + MP_EMAIL_ID
			+ " text not null, " + MP_NAME + " text not null, " + MP_PH_NO
			+ " text UNIQUE not null, " + MP_XMPP_PORT + " integer not null, "
			+ MP_XMPP_HOST + " text not null, " + MP_XMPP_SERVER
			+ " text not null, " + MP_XMPP_USER_NAME + " text not null, "
			+ MP_XMPP_PASSWORD + " text not null );";

	public ChatRoomsDBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void addUserData(User uData) {

		final SQLiteDatabase db = getWritableDatabase();
		try {

			ContentValues cVal = new ContentValues();

			cVal.put(U_EMAIL_ID, uData.getEmail_id());
			cVal.put(U_NAME, uData.getName());
			cVal.put(U_PH_NO, uData.getPh_no());
			cVal.put(U_XMPP_HOST, uData.getXmpp_host());
			cVal.put(U_XMPP_PORT, uData.getXmpp_port());
			cVal.put(U_XMPP_SERVER, uData.getXmpp_server());
			cVal.put(U_XMPP_USER_NAME, uData.getXmpp_user_name());
			cVal.put(U_CR_ID, uData.getCr_id());
			if (uData.getId() != -1) {
				cVal.put(KEY_ID, uData.getId());
				db.update(TABLE_USER, cVal, KEY_ID + "=?",
						new String[] { Long.toString(uData.getId()) });
			} else {
				long id = db.insert(TABLE_USER, null, cVal);
				uData.setId(id);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getClass().getName(), e);
		} finally {
			db.close(); // Closing database connection
		}

	}

	public void addMyProfileData(MyProfile mp) {
		final SQLiteDatabase db = getWritableDatabase();
		try {

			ContentValues cVal = new ContentValues();

			cVal.put(MP_EMAIL_ID, mp.getEmail_id());
			cVal.put(MP_NAME, mp.getName());
			cVal.put(MP_PH_NO, mp.getPh_no());
			cVal.put(MP_XMPP_HOST, mp.getXmpp_host());
			cVal.put(MP_XMPP_PORT, mp.getXmpp_port());
			cVal.put(MP_XMPP_SERVER, mp.getXmpp_server());
			cVal.put(MP_XMPP_USER_NAME, mp.getXmpp_user_name());
			cVal.put(MP_XMPP_PASSWORD, mp.getXmpp_password());
			cVal.put(KEY_ID, 1l);

			db.delete(TABLE_MY_PROFILE, null, null);
			long id = db.insert(TABLE_MY_PROFILE, null, cVal);
			mp.setId(id);

		} catch (Exception e) {
			Log.e(TAG, e.getClass().getName(), e);
		} finally {
			db.close(); // Closing database connection
		}
	}

	public void addChatRoomData(ChatRoom cr) {
		final SQLiteDatabase db = getWritableDatabase();
		try {

			ContentValues cVal = new ContentValues();

			cVal.put(CR_CURRENT_MAC, cr.getCurrent_mac());
			cVal.put(CR_CURRENT_E, cr.getCurrent_e());
			cVal.put(CR_OLD_MAC, cr.getOld_mac());
			cVal.put(CR_NAME, cr.getCR_name());
			cVal.put(CR_TIME, cr.getTime());
			cVal.put(CR_DESCRIPTION, cr.getDescription());
			if (cr.getId() != -1) {
				cVal.put(KEY_ID, cr.getId());
				db.update(TABLE_CHATROOMS, cVal, KEY_ID + "=?",
						new String[] { Long.toString(cr.getId()) });
			} else {
				long id = db.insert(TABLE_CHATROOMS, null, cVal);
				cr.setId(id);
			}

		} catch (Exception e) {
			Log.e(TAG, e.getClass().getName(), e);
		} finally {
			db.close(); // Closing database connection
		}
	}

	public Cursor getChatRoomsCursor() {
		final SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_CHATROOMS, ALL_CR, null, null, null,
				null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		db.close();
		return cursor;
	}

	public void addPostsData(Post pt) {
		final SQLiteDatabase db = getWritableDatabase();
		try {
			ContentValues cVal = new ContentValues();

			cVal.put(P_CR_ID, pt.getCR_id());
			cVal.put(P_MSG, pt.getMessage());
			cVal.put(P_TITLE, pt.getTitle());
			if (pt.getId() != -1) {
				cVal.put(KEY_ID, pt.getId());
				db.update(TABLE_POSTS, cVal, KEY_ID + "=?",
						new String[] { Long.toString(pt.getId()) });
			} else {
				long id = db.insert(TABLE_POSTS, null, cVal);
				pt.setId(id);
			}

		} catch (Exception e) {
			Log.e(TAG, e.getClass().getName(), e);
		} finally {
			db.close(); // Closing database connection
		}
	}

	@SuppressWarnings("unused")
	private String getTime() {

		Calendar c = Calendar.getInstance();
		String time = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + " "
				+ c.get(Calendar.HOUR_OF_DAY) + "." + c.get(Calendar.MINUTE);
		return time;
	}

	public Cursor getPostsCursor(long crid) {
		final SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_POSTS, ALL_POSTS, P_CR_ID + "=?",
				new String[] { Long.toString(crid) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		db.close();
		return cursor;
	}

	public Post[] getPostData(long crid) {
		Cursor cursor = getPostsCursor(crid);
		int count;
		Post[] postsArray = null;
		if (cursor != null) {
			count = cursor.getCount();
			postsArray = new Post[count];
			for (int i = 0; i < count; i++) {
				postsArray[i] = new Post(cursor.getLong(cursor
						.getColumnIndex(P_CR_ID)), cursor.getString(cursor
						.getColumnIndex(P_TITLE)), cursor.getString(cursor
						.getColumnIndex(P_MSG)));
			}
		}
		return postsArray;
	}

	public void addCommentData(Comment c) {
		final SQLiteDatabase db = getWritableDatabase();
		Log.d(TAG, "writable DB");

		try {
			ContentValues cVal = new ContentValues();

			cVal.put(C_CR_ID, c.getCR_id());
			cVal.put(C_MSG, c.getMessage());
			cVal.put(C_P_ID, c.getP_id());
			if (c.getId() != -1) {
				cVal.put(KEY_ID, c.getId());
				db.update(TABLE_COMMENTS, cVal, KEY_ID + "=?",
						new String[] { Long.toString(c.getId()) });
			} else {
				long id = db.insert(TABLE_COMMENTS, null, cVal);
				c.setId(id);
			}

		} catch (Exception e) {
			Log.e(TAG, e.getClass().getName(), e);
		} finally {
			db.close(); // Closing database connection
		}
	}

	public Cursor getCommentsCursor(long crid, long postid) {
		final SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_COMMENTS, ALL_COMMENTS, C_CR_ID
				+ "=? AND " + C_P_ID + "=?", new String[] {
				Long.toString(crid), Long.toString(postid) }, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			Log.d(TAG, "" + cursor.getCount());
		}
		db.close();
		return cursor;
	}
	
	public Comment[] getCommentsData(long crid,long postid) {
		Cursor cursor = getCommentsCursor(crid,postid);
		int count;
		Comment[] commentsArray = null; 
		if (cursor != null) {
			count = cursor.getCount();
			commentsArray= new Comment[count];
			for(int i=0;i<count;i++)
			{
				commentsArray[i]=new Comment(cursor.getLong(cursor.getColumnIndex(C_P_ID)), cursor.getLong(cursor.getColumnIndex(C_CR_ID)),cursor.getString(cursor.getColumnIndex(C_MSG)));
				
			}
		}		

		return commentsArray;
	}

	public Cursor getUsersCursor(long crid) {
		final SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_USER, ALL_USERS, U_CR_ID + "=?",
				new String[] { Long.toString(crid) }, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		db.close();
		return cursor;
	}

	public User[] getUsersData(long crid) {
		Cursor cursor = getUsersCursor(crid);
		int count;
		User[] usersArray = null;
		if (cursor != null) {
			count = cursor.getCount();
			usersArray = new User[count];
			for (int i = 0; i < count; i++) {
				usersArray[i] = new User(cursor.getString(cursor
						.getColumnIndex(U_EMAIL_ID)), cursor.getString(cursor
						.getColumnIndex(U_NAME)), cursor.getString(cursor
						.getColumnIndex(U_PH_NO)), cursor.getString(cursor
						.getColumnIndex(U_XMPP_HOST)), cursor.getString(cursor
						.getColumnIndex(U_XMPP_SERVER)), cursor.getInt(cursor
						.getColumnIndex(U_XMPP_PORT)), cursor.getString(cursor
						.getColumnIndex(U_XMPP_USER_NAME)),
						cursor.getLong(cursor.getColumnIndex(U_CR_ID)));

			}
		}

		return usersArray;
	}

	public MyProfile getMyProfiledata() {
		final SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_MY_PROFILE, ALL_My_PROFILE, null, null,
				null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		if (cursor.getCount() == 0)
			return null;
		MyProfile myProfile = new MyProfile(cursor.getString(cursor
				.getColumnIndex(MP_EMAIL_ID)), cursor.getString(cursor
				.getColumnIndex(MP_NAME)), cursor.getString(cursor
				.getColumnIndex(MP_PH_NO)), cursor.getString(cursor
				.getColumnIndex(MP_XMPP_HOST)), cursor.getString(cursor
				.getColumnIndex(MP_XMPP_SERVER)), cursor.getInt(cursor
				.getColumnIndex(MP_XMPP_PORT)), cursor.getString(cursor
				.getColumnIndex(MP_XMPP_USER_NAME)), cursor.getString(cursor
				.getColumnIndex(MP_XMPP_PASSWORD)));

		db.close();
		return myProfile;
	}

	public void onCreate(SQLiteDatabase db) {
		try {
			Log.d(TAG, CR_CREATE);
			db.execSQL(CR_CREATE);

			Log.d(TAG, USER_CREATE);
			db.execSQL(USER_CREATE);

			Log.d(TAG, POSTS_CREATE);
			db.execSQL(POSTS_CREATE);

			Log.d(TAG, COMMENTS_CREATE);
			db.execSQL(COMMENTS_CREATE);

			Log.d(TAG, MY_PROFILE_CREATE);
			db.execSQL(MY_PROFILE_CREATE);

		} catch (Exception e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		for (String table : ALL_TABLES) {
			db.execSQL("DROP TABLE IF EXISTS " + table);
		}
		onCreate(db);
	}

}