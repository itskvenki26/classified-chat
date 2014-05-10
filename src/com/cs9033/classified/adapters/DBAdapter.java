package com.cs9033.classified.adapters;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter.DataBaseHelper;

import Models.ChatRooms;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper {
	public static final boolean DEBUG = true;	
	private static final String TAG = "DBAdapter";
	
	/** Table names */
	public static final String TABLE_CHATROOMS="Table Chat Room";
	public static final String TABLE_CR_USER="Table Chat_Room_User";
	public static final String TABLE_USER="Table User";
	public static final String TABLE_CHANNEL="Table Channel";
	public static final String TABLE_POSTS="Table Posts";
	public static final String TABLE_COMMENTS="Table Comments";
	public static final String TABLE_MY_PROFILE="Table profile of the user";
	public static final String TABLE_MY_PROFILE_CHANNEL="Table list of channels";
	
	private static final String[] ALL_TABLES = { TABLE_USER, TABLE_POSTS,
		TABLE_COMMENTS,TABLE_CHATROOMS,TABLE_CR_USER,TABLE_CHANNEL, TABLE_CHANNEL,TABLE_MY_PROFILE_CHANNEL};
	
	public static final String KEY_ID = "_id";
	
    public static final String CR_NAME="Chat_Room_Name";    	
	public static final String CR_TIME="Chat_Room_time";
	public static final String CR_DESCRIPTION="Chat_Room_text";    	
	public static final String CR_CURRENT_MAC="Chat Room Current MAC";
	public static final String CR_OLD_MAC="Chat Room Old MAC"; 
	public static final String CR_CURRENT_E="Chat Room Current ENC Key";
	
	public static final String CRU_CRID="current user crid";
	public static final String CRU_CUR_MAK = "current user current MAK";
	public static final String CRU_EKEY = "current user current EKEY";
	public static final String CRU_USER_ID = "User Id";
	
	public static final String U_EMAIL_ID = "user_email";
	public static final String U_NAME = "user_name";
	public static final String U_PH_NO = "user_phone_num";
	public static final String U_PREF_CHANNEL_ID ="ID of preferred channel";
	
	public static final String UCH_U_ID = "User channel user id";
	public static final String UCH_TYPE="User Channel Type";
	public static final String UCH_HOST = "User channel Host";
	public static final String UCH_SERVICE = "User channel service";
	public static final String UCH_USR_NAME = "User channel user name";
    //public static final String UCH_PWD = "User channel password";
	public static final String UCH_PH_NUM="User phone number";
	public static final String UCH_PORT ="User Port number";
	public static final String UCH_PRF_CHANNEL="User preferred channel(boolean)";
	
	public static final String P_CR_ID ="chat room it belongs to";
	public static final String P_TITLE= "title";
	//public static final String P_TIME = "time";
	public static final String P_MSG = "message";
	
	public static final String C_P_ID= "post id comment belongs to"; 
  	//public static final String C_CR_ID= "chat room comment belongs to";
  	//public static final String C_CREATOR = "comment_creator";
  	//public static final String C_TIME = "comment_time";
  	public static final String C_MSG = "comment_message";
  	
	public static final String MP_U_ID = "User channel user id";
	public static final String MP_TYPE="User Channel Type";
	public static final String MP_HOST = "User channel Host";
	public static final String MP_SERVICE = "User channel service";
	public static final String MP_USR_NAME = "User channel user name";
    //public static final String UCH_PWD = "User channel password";
	public static final String MP_PH_NUM="User phone number";
	public static final String MP_PORT ="User Port number";
	public static final String MP_PRF_CHANNEL="User preferred channel(boolean)";
	public static final String MP_NAME="User full name";
	
	public static final String MPC_U_ID = "User channel user id";
	public static final String MPC_TYPE="User Channel Type";
	public static final String MPC_HOST = "User channel Host";
	public static final String MPC_SERVICE = "User channel service";
	public static final String MPC_USR_NAME = "User channel user name";
    //public static final String UCH_PWD = "User channel password";
	public static final String MPC_PH_NUM="User phone number";
	public static final String MPC_PORT ="User Port number";
	public static final String MPC_PRF_CHANNEL="User preferred channel(boolean)";
	

	public static final String DATABASE_NAME = "classified";

	static final String T_CHAR_ROOM = "CHAT_ROOM";
	static final String T_USER = "USER";
	static final String T_CHAT_ROOM_USER = "CHAT_ROOM_USER";
	static final String T_USER_CHANNELS = "USER_CHANNELS";
	static final String T_CHAT_ROOM_POST = "CHAT_ROOM_POST";
	static final String T_CHAT_ROOM_POST_COMMENT = "CHAT_ROOM_POST_COMMENT";
	static final String T_MY_PROFILE = "MY_PROFILE";
	static final String T_MY_PROFILE_CHANNELS = "MY_PROFILE_CHANNELS";

	static final String C_CR_ID = "_ID";
	static final String C_CR_NAME = "NAME";
	static final String C_CR_DESC = "DESC";
	static final String C_CR_CURRENT_MAC = "CURRENT_MAC";
	static final String C_CR_CURRENT_E = "CURRENT_E";

	static final String C_U_NAME = "NAME";
	static final String C_U_PH_NUM = "PH_NUM";
	static final String C_U_PREF_CHANNEL_ID = "PREF_CHANNEL_ID";

	static final String C_CRU_CURRENT_E = "CURRENT_E";

	public static final int DATABASE_VERSION = 1;

	
	private DBAdapter DBAdapter = null;
	
	
	
	/******  Create Table Syntax ******/
	
	private static final String CR_CREATE = "create table " + TABLE_CHATROOMS
			+ "(" + KEY_ID + "integer primary key autoincrement, " + CR_NAME
			+ "text not null" + CR_TIME + "text not null"
			+ CR_DESCRIPTION + "text not null" + CR_CURRENT_MAC
			+ "integer not null" + CR_OLD_MAC + "integer not null"
			+ CR_CURRENT_E + " integer not null);";
	
	
	

	public DBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		if (DEBUG)
			Log.d(TAG, "new DB create");
		try {
			db.execSQL(CR_CREATE);
			
		} catch (Exception exception) {
			if (DEBUG)
				Log.i(TAG, "Exception onCreate() exception");
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		if (DEBUG)
			Log.w(TAG, "Upgrading database from version" + oldVersion
					+ "to" + newVersion + "...");

		for (String table : ALL_TABLES) {
			db.execSQL("DROP TABLE IF EXISTS " + table);
		}
		onCreate(db);

	}
	
	
	

	
	

}
