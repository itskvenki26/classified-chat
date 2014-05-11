package com.cs9033.classified.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Models.Channel;
import Models.ChatRoom_Profile;
import Models.ChatRoom_User;
import Models.ChatRooms;
import Models.Comments;
import Models.CurrentUser;
import Models.MessageQ;
import Models.MyProfile;
import Models.MyProfile_Channel;
import Models.User_Channel;
import Models.Posts;
import Models.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatRoomsDBAdapter {

	/***** if debug is set true then it will show all Logcat message ****/
	public static final boolean DEBUG = true;
	/******************** Logcat TAG ************/
	private static final String TAG = "DBAdapter";

	/******************** Table Fields ************/
	public static final String KEY_ID = "_id";


	public static final String U_EMAIL_ID = "user_email";
  	public static final String U_NAME = "user_name";
  	public static final String U_PH_NO = "user_phone_num";
  	public static final String U_PREF_CHANNEL_ID ="ID of preferred channel";
	
	public static final String CRU_CRID="current user chat room id";
	public static final String CRU_CUR_MAK = "current user current MAK";
	public static final String CRU_EKEY = "current user current EKEY";
	public static final String CRU_USER_ID = "User Id";

	//public static final String P_CREATOR = "creator";
	public static final String P_CR_ID = "chat room it belongs to";
	public static final String P_TITLE = "title";
	//public static final String P_TIME = "time";
	public static final String P_MSG = "message";

	public static final String C_P_ID = "post id comment belongs to";
	public static final String C_CR_ID = "chat room comment belongs to";
	//public static final String C_CREATOR = "comment_creator";
	//public static final String C_TIME = "comment_time";
	public static final String C_MSG = "comment_message";
	
	public static final String CR_NAME="Chat_Room_Name";    	
	public static final String CR_TIME="Chat_Room_time";
	public static final String CR_DESCRIPTION="Chat_Room_text";    	
	public static final String CR_CURRENT_MAC="Chat Room Current MAC";
	public static final String CR_OLD_MAC="Chat Room Old MAC"; 
	public static final String CR_CURRENT_E="Chat Room Current ENC Key";

	public static final String CRP_CRID = "Chat Room Id";
	public static final String CRP_MAC_CUR = "Current MAC key";
	public static final String CRP_MAC_OLD = "Old MAC key";

	public static final String CH_U_ID = "user id of the channel";
	public static final String CH_TYPE = "Channel type";
	public static final String CH_PH_NUM = "phone number";
	public static final String CH_TIME = "Channel establish time";
	public static final String CH_PREF = "Preffered Channel";

	public static final String MCH_U_ID = "My channel user id";
	public static final String MCH_HOST = "My channel Host";
	public static final String MCH_SERVICE = "My channel service";
	public static final String MCH_USR_NAME = "My channel user name";
	public static final String MCH_PWD = "My channel password";

//	public static final String M_U_ID = "user id message belongs to";
//	public static final String M_CR_ID = "Chat Room message belongs to";
//	public static final String M_CH_ID = "Channel in which message need to send";
//	public static final String M_ENC_MSG = "Encrypted Message";
//	public static final String M_TIME = "Message Time";
	
	public static final String UCH_U_ID = "User channel user id";
	public static final String UCH_TYPE="User Channel Type";
	public static final String UCH_HOST = "User channel Host";
	public static final String UCH_SERVICE = "User channel service";
	public static final String UCH_USR_NAME = "User channel user name";
//	public static final String UCH_PWD = "User channel password";
	public static final String UCH_PH_NUM="User phone number";
	public static final String UCH_PORT ="User Port number";
	public static final String UCH_PRF_CHANNEL="User preferred channel(boolean)";
	
	public static final String MP_U_ID = "User channel user id";
	public static final String MP_TYPE="User Channel Type";
	public static final String MP_HOST = "User channel Host";
	public static final String MP_SERVICE = "User channel service";
	public static final String MP_USR_NAME = "User channel user name";
//	public static final String UCH_PWD = "User channel password";
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

	/******************** Database Name ************/
	public static final String DATABASE_NAME = "DB_sqllite";

	/**** Database Version (Increase one if want to also upgrade your database) ****/
	public static final int DATABASE_VERSION = 3;// started at 1

	/** Table names */

	
	public static final String TABLE_CHATROOMS="Table Chat Room";
	public static final String TABLE_CR_USER="Table Chat_Room_User";
	public static final String TABLE_USER="Table User";
	public static final String TABLE_USER_CHANNEL="Table Channel";
	public static final String TABLE_POSTS="Table Posts";
	public static final String TABLE_COMMENTS="Table Comments";
	public static final String TABLE_MY_PROFILE="Table profile of the user";
	public static final String TABLE_MY_PROFILE_CHANNEL="Table list of channels";
	//public static final String TABLE_USER_CHANNEL="";

	/*** Set all table with comma separated like USER_TABLE,ABC_TABLE ***/
	private static final String[] ALL_TABLES = { TABLE_USER, TABLE_POSTS,
			TABLE_COMMENTS };
	public static final String[] ALL_POSTS = { P_CR_ID, P_TITLE,
			P_MSG, KEY_ID };
	// public static final String[] ALL_USERS = { "_id", P_CREATOR, P_MSG,
	// P_TIME };

	public static final String[] ALL_COMMENTS = { C_P_ID, C_CR_ID, 
			C_MSG, KEY_ID };
	public static final String[] ALL_CR = { CR_NAME, CR_TIME,
		CR_DESCRIPTION, CR_CURRENT_MAC, CR_OLD_MAC, CR_CURRENT_E, KEY_ID };
	public static final String[] ALL_CRP = { CRP_CRID, CRP_MAC_CUR,
			CRP_MAC_OLD, KEY_ID };
	public static final String[] ALL_CHANNELS = { CH_U_ID, CH_TYPE, CH_PH_NUM,
			CH_TIME, CH_PREF, KEY_ID };
//	public static final String[] ALL_MESSAGES = { M_U_ID, M_CR_ID, M_CH_ID,
//			M_ENC_MSG, M_TIME, KEY_ID };
	public static final String[] ALL_MYCHANNELS = { MCH_HOST, MCH_SERVICE,
			MCH_USR_NAME, MCH_PWD, KEY_ID };
	public static final String[] ALL_USERS = { U_EMAIL_ID, U_NAME, U_PH_NO,
			KEY_ID };
	// public static final String[] ALL_CU

	/** Create table syntax */

	private static final String CR_USER_CREATE = "create table "
			+ TABLE_CR_USER + " (" + KEY_ID
			+ " integer primary key autoincrement,  " + CRU_CRID
			+ " integer not null,  " + CRU_CUR_MAK + "  integer not null, "
			+ CRU_EKEY + "  integer not null, " + CRU_USER_ID + "  integer not null);";

	private static final String USER_CREATE = "create table " + TABLE_USER
			+ " (" + KEY_ID + " integer primary key autoincrement,  "
			+ U_EMAIL_ID + "  text not null, " + U_NAME + "  text not null, "
			+ U_PH_NO + " text UNIQUE," + U_PREF_CHANNEL_ID + "integer not null);";

	private static final String POSTS_CREATE = "create table " + TABLE_POSTS
			+ " (" + KEY_ID + " integer primary key autoincrement,  "
			+ P_CR_ID
			+ "  integer UNIQUE, " +P_TITLE+ "text UNIQUE, " + P_MSG
			+ "  text not null);";

	private static final String COMMENTS_CREATE = "create table "
			+ TABLE_COMMENTS + " (" + KEY_ID
			+ " integer primary key autoincrement,  " + C_P_ID
			+ " integer not null,  " + C_CR_ID + " integer not null,  "
			+ C_MSG + "  text not null);";

	private static final String CR_CREATE = "create table " + TABLE_CHATROOMS
			+ "(" + KEY_ID + "integer primary key autoincrement, " + CR_NAME
			+ "text UNIQUE" + CR_TIME + "text not null"
			+ CR_DESCRIPTION + "text not null" + CR_CURRENT_MAC
			+ "integer not null" + CR_OLD_MAC + "integer not null"
			+ CR_CURRENT_E + " integer not null);";

	private static final String USER_CHANNELS_CREATE = "create table "
			+ TABLE_USER_CHANNEL + "(" + KEY_ID
			+ "integer primary key autoincrement, " + UCH_U_ID
			+ "integer not null, " + UCH_TYPE + " text not null, "+ UCH_PH_NUM
			+ " text not null," + UCH_HOST
			+ " text not null, " + UCH_SERVICE + " text not null, " + UCH_PORT
			+ " integer not null,"+ UCH_USR_NAME
			+ " text not null,"+ UCH_PRF_CHANNEL
			+ " integer not null);";
	
	private static final String MY_PROFILE_CREATE = "create table "
			+ TABLE_MY_PROFILE + "(" + KEY_ID
			+ "integer primary key autoincrement, " + MP_U_ID
			+ "integer not null, " + MP_TYPE + " text not null, "+ MP_PH_NUM
			+ " text not null," + MP_HOST
			+ " text not null, " + MP_SERVICE + " text not null, " + MP_PORT
			+ " integer not null,"+ MP_USR_NAME
			+ " text not null,"+ MP_PRF_CHANNEL
			+ " integer not null,"+ MP_NAME
			+ " text not null);";
	
	private static final String MY_PROFILE_CHANNEL_CREATE = "create table "
			+ TABLE_MY_PROFILE_CHANNEL + "(" + KEY_ID
			+ "integer primary key autoincrement, " + MPC_U_ID
			+ "integer not null, " + MPC_TYPE + " text not null, "+ MPC_PH_NUM
			+ " text not null," + MPC_HOST
			+ " text not null, " + MPC_SERVICE + " text not null, " + MPC_PORT
			+ " integer not null,"+ MPC_USR_NAME
			+ " text not null,"+ MPC_PRF_CHANNEL
			+ " integer not null);";

//	private static final String MESSAGEQ_CREATE = "create table "
//			+ TABLE_MESSAGEQ + "(" + KEY_ID
//			+ "integer primary key autoincrement, " + M_U_ID
//			+ "integer not null, " + M_CR_ID + " integer not null, " + M_CH_ID
//			+ " text not null, " + M_ENC_MSG + " text not null, " + M_TIME
//			+ " text not null);";
//
//	private static final String MYCHANNELS_CREATE = "create table "
//			+ TABLE_MYCHANNELS + "(" + KEY_ID
//			+ "integer primary key autoincrement, " + MCH_HOST
//			+ "integer not null, " + MCH_SERVICE + " text not null, "
//			+ MCH_USR_NAME + " text not null, " + MCH_PWD + " text not null);";
//	private static final String CRP_CREATE = "create table "
//			+ TABLE_CHAT_ROOMS_PROFILE + "(" + KEY_ID
//			+ "integer primary key autoincrement, " + CRP_CRID
//			+ "integer not null, " + CRP_MAC_CUR + " text not null, "
//			+ CRP_MAC_OLD + " text not null);";

	// private static final String DEVICE_CREATE =
	// "create table tbl_device(_id integer primary key auto-increment, device_name text not null,device_email text not null,device_regid text not null,device_imei text not null);";

	/**** Used to open database in synchronized way ****/
	private DataBaseHelper DBHelper = null;

	private Context context;

	public String[] getColsAllUsers() {
		return ALL_USERS;
	}

	public String[] getColsAllChatRooms() {
		return ALL_CR;
	}

	public String[] getColsAllPosts() {
		return ALL_POSTS;
	}

	public String[] getColsAllComments() {
		return ALL_COMMENTS;
	}

	public String[] getColsAllChannels() {
		return ALL_CHANNELS;
	}

	@SuppressWarnings("unused")
	private ChatRoomsDBAdapter() {
	}

	public ChatRoomsDBAdapter(Context context) {
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
	public static class DataBaseHelper extends SQLiteOpenHelper {
		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (DEBUG)
				Log.d(TAG, "new DB create");
			try {
				db.execSQL(CR_CREATE);
				db.execSQL(USER_CREATE);
				db.execSQL(USER_CHANNELS_CREATE);
				db.execSQL(POSTS_CREATE);
				db.execSQL(COMMENTS_CREATE);
				db.execSQL(MY_PROFILE_CREATE);
				db.execSQL(MY_PROFILE_CHANNEL_CREATE);
				//db.execSQL(MESSAGEQ_CREATE);
				//db.execSQL(MYCHANNELS_CREATE);
				//db.execSQL(CRP_CREATE);
				db.execSQL(CR_USER_CREATE);
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

	/**** Open database for insert,update,delete in synchronized manner ****/
	private synchronized SQLiteDatabase open() throws SQLException {
		if (DBHelper == null) {
			Log.d(TAG, "open: DBHelper is null");
		}
		return DBHelper.getWritableDatabase();
	}
	
	


	// Adding new user

	public void addChatRoomUserData(ChatRoom_User cru) {
		try {
			final SQLiteDatabase db = open();
			long crid = cru.getcrid();
			long usrid =cru.getusrid();
			long curmak = cru.getCur_Mak();
			long cuerkey = cru.getCur_Ekey();
			
			// ArrayList<Channel> Chlist =uData.getChlist();

			ContentValues cVal = new ContentValues();
			cVal.put(CRU_CRID, crid);
			cVal.put(CRU_USER_ID, usrid);			
			cVal.put(CRU_CUR_MAK, curmak);
			cVal.put(CRU_EKEY, cuerkey);
			// for(Channel c:Chlist)
			// {
			// cVal.put(U_CHANNELS, Chlist);
			// }
			db.insert(TABLE_CR_USER, null, cVal);
			db.close(); // Closing database connection
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	

	public ChatRoom_User getChatRoomUserData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CR_USER, new String[] { KEY_ID,
				CRU_CRID, CRU_USER_ID, CRU_CUR_MAK, CRU_EKEY }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ChatRoom_User data = new ChatRoom_User(
				(cursor.getInt(0)), cursor.getInt(1), cursor.getInt(2),
				cursor.getInt(3), cursor.getInt(4));
		// return contact
		return data;
	}

	public void addUserData(User uData) {
		try {
			final SQLiteDatabase db = open();

			String email_id = sqlEscapeString(uData.getEMAIL());
			String name = sqlEscapeString(uData.getName());
			String ph_no = sqlEscapeString(uData.getPH_NO());
			int pref_channel_id=sqlEscapeString(uData.getpref_channel_id());
			// ArrayList<Channel> Chlist =uData.getChlist();

			ContentValues cVal = new ContentValues();
			cVal.put(U_EMAIL_ID, email_id);
			cVal.put(U_NAME, name);
			cVal.put(U_PH_NO, ph_no);
			cVal.put(U_PREF_CHANNEL_ID, pref_channel_id);
			// for(Channel c:Chlist)
			// {
			// cVal.put(U_CHANNELS, Chlist);
			// }
			db.insert(TABLE_USER, null, cVal);
			db.close(); // Closing database connection
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	// Getting single user data
	public User getUserData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID, U_EMAIL_ID,
				U_NAME, U_PH_NO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		User data = new User((cursor.getInt(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4));
		// return contact
		return data;
	}

//	public void getAllUsers() {
//		User usr = new User();
//
//		final SQLiteDatabase db = open();
//
//		String from[] = getColsAllUsers();
//		int to[] = new int[] {};
//
//		Cursor c = db
//				.query(TABLE_USER, ALL_USERS, null, null, null, null, null);

		// SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
		// R.layout.fragment_users, c, from, to, 0);
		// setListAdapter(sca);

	//}

	/*public void addMessage(MessageQ msg) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();

			int uid = sqlEscapeString(msg.getU_id());
			int crid = sqlEscapeString(msg.getCR_id());
			int chid = sqlEscapeString(msg.getCH_id());
			String emsg = sqlEscapeString(msg.getEncypt_Message());
			String time = sqlEscapeString(msg.getTime());

			ContentValues cVal = new ContentValues();
			cVal.put(M_U_ID, uid);
			cVal.put(M_CR_ID, crid);
			cVal.put(M_CH_ID, chid);
			cVal.put(M_ENC_MSG, emsg);
			cVal.put(M_TIME, time);
			db.insert(TABLE_MESSAGEQ, null, cVal);
			db.close(); // Closing database connection

			// Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public MessageQ getMessageQData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHAT_ROOMS, new String[] { KEY_ID,
				M_U_ID, M_CR_ID, M_CH_ID, M_ENC_MSG, M_TIME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MessageQ data = new MessageQ(Integer.parseInt(cursor.getString(0)),
				cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
				cursor.getString(4), cursor.getString(5));
		// return contact
		return data;
	}*/

	public void addUser_Channels(User_Channel uch) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();
            long usrid=uch.getusrid(); 
            String type=sqlEscapeString(uch.gettype());
            String phnum=sqlEscapeString(uch.getphnum());
			String host = sqlEscapeString(uch.getHost());
			String service = sqlEscapeString(uch.getService());
			String usrname = sqlEscapeString(uch.getusername());
			long port=uch.getport();
			int pref_channel_boolean=sqlEscapeString(uch.getpref_channel_boolean());
			// String pref = sqlEscapeString(ch.getpreffered_Ch());

			ContentValues cVal = new ContentValues();
			// cVal.put(CH_U_ID, uid);
			cVal.put(UCH_U_ID, usrid);
			cVal.put(UCH_TYPE, type);
			cVal.put(UCH_PH_NUM, phnum);
			cVal.put(UCH_HOST, host);
			cVal.put(UCH_SERVICE, service);
			cVal.put(UCH_PORT, port);
			cVal.put(UCH_USR_NAME, usrname);
			cVal.put(UCH_PRF_CHANNEL, pref_channel_boolean);
			db.insert(TABLE_USER_CHANNEL, null, cVal);
			db.close(); // Closing database connection

			// Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public User_Channel getUser_ChannelsData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_USER_CHANNEL, new String[] { KEY_ID,
				UCH_U_ID, UCH_TYPE, UCH_PH_NUM, UCH_HOST,UCH_SERVICE,UCH_PORT,UCH_USR_NAME,UCH_PRF_CHANNEL}, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		User_Channel data = new User_Channel((cursor.getInt(0)),
				cursor.getInt(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8));
		// return contact
		return data;
	}
	
	public void addMyProfile(MyProfile mp) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();
            long usrid=mp.getusrid(); 
            String type=sqlEscapeString(mp.gettype());
            String phnum=sqlEscapeString(mp.getphnum());
			String host = sqlEscapeString(mp.getHost());
			String service = sqlEscapeString(mp.getService());
			String usrname = sqlEscapeString(mp.getusername());
			long port=mp.getport();
			int pref_channel_boolean=sqlEscapeString(mp.getpref_channel_boolean());
			String name = sqlEscapeString(mp.getname());

			ContentValues cVal = new ContentValues();
			// cVal.put(CH_U_ID, uid);
			cVal.put(MP_U_ID, usrid);
			cVal.put(MP_TYPE, type);
			cVal.put(MP_PH_NUM, phnum);
			cVal.put(MP_HOST, host);
			cVal.put(MP_SERVICE, service);
			cVal.put(MP_PORT, port);
			cVal.put(MP_USR_NAME, usrname);
			cVal.put(MP_PRF_CHANNEL, pref_channel_boolean);
			cVal.put(MP_NAME, name);
			db.insert(TABLE_MY_PROFILE, null, cVal);
			db.close(); // Closing database connection

			// Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public MyProfile getMyprofileData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_MY_PROFILE, new String[] { KEY_ID,
				MP_U_ID, MP_TYPE, MP_PH_NUM, MP_HOST,MP_SERVICE,MP_PORT,MP_USR_NAME,MP_PRF_CHANNEL,MP_NAME}, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MyProfile data = new MyProfile((cursor.getInt(0)),
				cursor.getInt(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9));
		// return contact
		return data;
	}

	
	public void addMyProfileChannel(MyProfile_Channel mpc) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();
            long usrid=mpc.getusrid(); 
            String type=sqlEscapeString(mpc.gettype());
            String phnum=sqlEscapeString(mpc.getphnum());
			String host = sqlEscapeString(mpc.getHost());
			String service = sqlEscapeString(mpc.getService());
			String usrname = sqlEscapeString(mpc.getusername());
			long port=mpc.getport();
			int pref_channel_boolean=sqlEscapeString(mpc.getpref_channel_boolean());
			//String name = sqlEscapeString(mp.getname());

			ContentValues cVal = new ContentValues();
			// cVal.put(CH_U_ID, uid);
			cVal.put(MPC_U_ID, usrid);
			cVal.put(MPC_TYPE, type);
			cVal.put(MPC_PH_NUM, phnum);
			cVal.put(MPC_HOST, host);
			cVal.put(MPC_SERVICE, service);
			cVal.put(MPC_PORT, port);
			cVal.put(MPC_USR_NAME, usrname);
			cVal.put(MPC_PRF_CHANNEL, pref_channel_boolean);
			//cVal.put(MP_NAME, name);
			db.insert(TABLE_MY_PROFILE_CHANNEL, null, cVal);
			db.close(); // Closing database connection

			// Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public MyProfile_Channel getMyprofile_ChannelData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_MY_PROFILE_CHANNEL, new String[] { KEY_ID,
				MPC_U_ID, MPC_TYPE, MPC_PH_NUM, MPC_HOST,MPC_SERVICE,MPC_PORT,MPC_USR_NAME,MPC_PRF_CHANNEL}, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MyProfile_Channel data = new MyProfile_Channel((cursor.getInt(0)),
				cursor.getInt(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8));
		// return contact
		return data;
	}

	

	/*public void addChatRoomProfileData(ChatRoom_Profile crp) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();
			int crid = sqlEscapeString(crp.getcrid());
			String macold = sqlEscapeString(crp.getMAC_Old());
			String maccur = sqlEscapeString(crp.getMAC_Cur());

			ContentValues cVal = new ContentValues();
			cVal.put(CRP_CRID, crid);
			cVal.put(CRP_MAC_CUR, maccur);
			cVal.put(CRP_MAC_OLD, macold);

			db.insert(TABLE_CHAT_ROOMS_PROFILE, null, cVal);
			db.close(); // Closing database connection

			// Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public ChatRoom_Profile getChatRoomsProfileData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHAT_ROOMS_PROFILE, new String[] {
				KEY_ID, CRP_CRID, CRP_MAC_CUR, CRP_MAC_OLD }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ChatRoom_Profile data = new ChatRoom_Profile(Integer.parseInt(cursor
				.getString(0)), cursor.getInt(1), cursor.getString(2),
				cursor.getString(3));
		// return contact
		return data;
	}*/

	public void addChatRoomsData(ChatRooms cr) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();
			long curmac = cr.getcurrent_mac();
			long cur_e = cr.getcurrent_e();
			long oldmac = cr.getold_mac();			
			String CRName = sqlEscapeString(cr.getCR_name());			
			String time = sqlEscapeString(cr.getTIME());
			String txt = sqlEscapeString(cr.getdescription());

			ContentValues cVal = new ContentValues();
			cVal.put(CR_CURRENT_MAC, curmac);
			cVal.put(CR_CURRENT_E, cur_e);
			cVal.put(CR_OLD_MAC, oldmac);
			cVal.put(CR_NAME, CRName);
			cVal.put(CR_TIME, time);
			cVal.put(CR_DESCRIPTION, txt);
			db.insert(TABLE_CHATROOMS, null, cVal);
			db.close(); // Closing database connection

			// Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}

	public ChatRooms getChatRoomsData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHATROOMS, new String[] { KEY_ID,
				CR_CURRENT_MAC, CR_CURRENT_E, CR_OLD_MAC, CR_NAME,
				CR_TIME, CR_DESCRIPTION }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ChatRooms data = new ChatRooms((cursor.getInt(0)),
				cursor.getInt(1), cursor.getInt(2),
				 cursor.getInt(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return contact
		return data;
	}
	
	public Cursor getChatRoomsCursor() {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHATROOMS, new String[] { KEY_ID,
				CR_CURRENT_MAC, CR_CURRENT_E, CR_OLD_MAC, CR_NAME,
				CR_TIME, CR_DESCRIPTION },null, null,
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

//		ChatRooms data = new ChatRooms((cursor.getInt(0)),
//				cursor.getInt(1), cursor.getInt(2),
//				 cursor.getInt(3),
//				cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return contact
		return cursor;
	}

	public void addPostsData(Posts pt) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();

			long crid =pt.getCR_id();
			String title = sqlEscapeString(pt.getTitle());
			//String creator = sqlEscapeString(pt.getCREATOR());
			//String time = sqlEscapeString(pt.getTIME());
			String msg = sqlEscapeString(pt.getMessage());

			ContentValues cVal = new ContentValues();
			cVal.put(P_CR_ID, crid);
			cVal.put(P_TITLE, title);
			//cVal.put(P_CREATOR, creator);
			//cVal.put(P_TIME, time);
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

		// String time = getTime();
		// Posts post = new Posts(0, CurrentUser.getName(), time,
		// message,title);
		// addPostsData(post);

	}

	public Posts getPostsData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_POSTS, new String[] { KEY_ID, P_TITLE, P_MSG }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Posts data = new Posts((cursor.getInt(0)),
				cursor.getInt(1), cursor.getString(2), cursor.getString(3)
				);
		// return contact
		return data;
	}
	
	public Cursor getPostsCursor(long crid) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_POSTS, new String[] { KEY_ID, P_TITLE, P_MSG }, P_CR_ID + "=?",
				new String[] { String.valueOf(crid) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

//		Posts data = new Posts((cursor.getInt(0)),
//				cursor.getInt(1), cursor.getString(2), cursor.getString(3)
//				);
		// return contact
		return cursor;
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
		Log.d(TAG, "addCommentsData ct");
		final SQLiteDatabase db = open();
		Log.d(TAG, "addCommentsData ct: got db");

		long crid = ct.getCR_id();
		//String creator = sqlEscapeString(ct.getCREATOR());
		//String time = getTime();
		String msg = sqlEscapeString(ct.getMessage());
		long post_id = ct.getP_id();
		Log.d(TAG, "addCommentsData ct: set comment values");

		ContentValues cVal = new ContentValues();
		cVal.put(C_CR_ID, crid);
		cVal.put(C_P_ID, post_id);
		//cVal.put(C_CREATOR, creator);
		//cVal.put(C_TIME, time);
		cVal.put(C_MSG, msg);
		// cVal.put(C_P_ID, post_id);
		db.insert(TABLE_COMMENTS, null, cVal);
		Log.d(TAG, "addCommentsData ct: inserted comment");
		db.close(); // Closing database connection
		Log.d(TAG, "addCommentsData ct: closed db");
		// catch (Throwable t) {
		// Log.i("Database", "Exception caught: " + t.getMessage(), t);
		// }
	}

	public void addCommentsData(String message, String postId) {
		Log.d(TAG, "addCommentsData");
		// Comments ct = new Comments(0, CurrentUser.getName(), null, message,
		// postId);
		Log.d(TAG, "addCommentsData: created comment object");
		// addCommentsData(ct);
	}

	public Comments getCommentsData(long id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_COMMENTS, new String[] { KEY_ID, C_P_ID,
				C_CR_ID, C_MSG }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Comments data = new Comments((cursor.getInt(0)),
				cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
		// return contact
		return data;
	}
	
	public Cursor getCommentsCursor(long crid, long postid) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_COMMENTS, new String[] { KEY_ID, C_P_ID,
				C_CR_ID, C_MSG }, C_CR_ID + "=?"+C_P_ID + "=?",new String[] { String.valueOf(crid),String.valueOf(postid) },
				null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

//		Comments data = new Comments((cursor.getInt(0)),
//				cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
		// return contact
		return cursor;
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
	public List<User> getAllUserData() {
		List<User> contactList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USER + " ORDER BY "
				+ KEY_ID + " desc";

		final SQLiteDatabase db = open();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User data = new User();
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
	public List<User> getDistinctUser() {
		List<User> contactList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  distinct(user_email_id),user_name  FROM "
				+ TABLE_USER + "ORDER BY " + KEY_ID + " desc";

		final SQLiteDatabase db = open();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User data = new User();

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

	private int sqlEscapeString(int u_id) {
		int aReturn = 0;

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