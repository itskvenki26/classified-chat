package com.cs9033.classified.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Models.Channel;
import Models.ChatRoom_Profile;
import Models.ChatRooms;
import Models.Comments;
import Models.CurrentUser;
import Models.MessageQ;
import Models.MyChannel;
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
	public static final String U_CHANNELS="list of user channels";

	public static final String P_CREATOR = "creator";
	public static final String P_CR_ID ="chat room it belongs to";
	public static final String P_TITLE= "title";
	public static final String P_TIME = "time";
	public static final String P_MSG = "message";
    
	public static final String C_P_ID= "post id comment belongs to"; 
	public static final String C_CR_ID= "chat room comment belongs to";
	public static final String C_CREATOR = "comment_creator";
	public static final String C_TIME = "comment_time";
	public static final String C_MSG = "comment_message";	
	
	public static final String CR_NAME="Chat_Room_Name";
	public static final String CR_PASSWORD="Chat_Room_Password";
	public static final String CR_CREATOR="Chat_Room_Creator";
	public static final String CR_TIME="Chat_Room_time";
	public static final String CR_TEXT="Chat_Room_text";
	public static final String CR_USER_ID="Chat Room User id";
	public static final String CR_AUTHKEY_OLD="Authentication key old";
	public static final String CR_AUTHKEY_NEW="Authentication key new"; 
	public static final String CR_ENCRYPTION_KEY="Encryption key";
    public static final String CR_SIGNING_KEY="Signing Key";
    
    public static final String CRP_CRID = "Chat Room Id";
	public static final String CRP_MAC_CUR = "Current MAC key";
	public static final String CRP_MAC_OLD = "Old MAC key";
	
	public static final String CH_U_ID= "user id of the channel";
	public static final String CH_TYPE ="Channel type";
	public static final String CH_PH_NUM ="phone number";
	public static final String CH_TIME ="Channel establish time";
	public static final String CH_PREF ="Preffered Channel";
	
	public static final String MCH_U_ID="My channel user id";
	public static final String MCH_HOST="My channel Host";
	public static final String MCH_SERVICE="My channel service";
	public static final String MCH_USR_NAME="My channel user name";
	public static final String MCH_PWD="My channel password";
	
	public static final String M_U_ID = "user id message belongs to";
	public static final String M_CR_ID = "Chat Room message belongs to"; 
	public static final String M_CH_ID ="Channel in which message need to send";
	public static final String M_ENC_MSG= "Encrypted Message";
	public static final String M_TIME = "Message Time";

	/******************** Database Name ************/
	public static final String DATABASE_NAME = "DB_sqllite";

	/**** Database Version (Increase one if want to also upgrade your database) ****/
	public static final int DATABASE_VERSION = 3;// started at 1

	/** Table names */
	public static final String TABLE_USER = "tbl_user";
	public static final String TABLE_POSTS = "tbl_posts";
	public static final String TABLE_COMMENTS = "tbl_comments";
	public static final String TABLE_CHAT_ROOMS="tbl_chatrooms";
	public static final String TABLE_CHAT_ROOMS_PROFILE ="tbl_chatroom_profile";
	public static final String TABLE_CHANNELS ="tbl_channels";
	public static final String TABLE_MESSAGEQ= "tbl_message";
	public static final String TABLE_MYCHANNELS="tbl_mychannels";

	/*** Set all table with comma seperated like USER_TABLE,ABC_TABLE ***/
	private static final String[] ALL_TABLES = { TABLE_USER, TABLE_POSTS,
			TABLE_COMMENTS };
	public static final String[] ALL_POSTS = { P_CREATOR,P_CR_ID,P_TITLE, P_MSG, P_TIME, KEY_ID };
	// public static final String[] ALL_USERS = { "_id", P_CREATOR, P_MSG,
	// P_TIME };
	public static final String[] ALL_COMMENTS = { C_P_ID,C_CR_ID,C_CREATOR, C_MSG, C_TIME,
			 KEY_ID };
	public static final String[] ALL_CR={CR_USER_ID,CR_AUTHKEY_OLD,CR_AUTHKEY_NEW,CR_ENCRYPTION_KEY,CR_SIGNING_KEY,
		CR_NAME,CR_PASSWORD,CR_CREATOR,CR_TIME,CR_TEXT,KEY_ID};
	public static final String[] ALL_CRP={CRP_CRID,CRP_MAC_CUR,CRP_MAC_OLD,KEY_ID};
	public static final String[] ALL_CHANNELS= {CH_U_ID,CH_TYPE,CH_PH_NUM,CH_TIME,CH_PREF,KEY_ID };
	public static final String[] ALL_MESSAGES= {M_U_ID,M_CR_ID,M_CH_ID,M_ENC_MSG,M_TIME,KEY_ID};
	public static final String[] ALL_MYCHANNELs={MCH_HOST,MCH_SERVICE,MCH_USR_NAME,MCH_PWD,KEY_ID};
	/** Create table syntax */

	private static final String USER_CREATE = "create table " + TABLE_USER
			+ " (" + KEY_ID + " integer primary key autoincrement,  "
			+ U_EMAIL_ID + "  text not null, " + U_NAME + "  text not null, "
			+ U_PH_NO + "  text not null,"+ U_CHANNELS+"List not null);";

	private static final String POSTS_CREATE = "create table " + TABLE_POSTS
			+ " (" + KEY_ID + " integer primary key autoincrement,  "
			+ P_CREATOR + "  text not null, " + P_CR_ID + "  integer not null, " 
			+ P_TIME + "  text not null, "	+ P_MSG + "  text not null);";

	private static final String COMMENTS_CREATE = "create table "
			+ TABLE_COMMENTS + " (" + KEY_ID
			+ " integer primary key autoincrement,  " +C_P_ID+" integer not null,  "
			+C_CR_ID+" integer not null,  "+ C_CREATOR
			+ "  text not null, " + C_TIME + "  text not null, " + C_MSG
			+ "  text not null);";
	
	private static final String CR_CREATE= "create table "
			+ TABLE_CHAT_ROOMS + "(" + KEY_ID
			+ "integer primary key autoincrement, "+CR_USER_ID +"integer not null"
			+CR_AUTHKEY_OLD +"integer not null"+CR_AUTHKEY_NEW +"integer not null"
			+CR_ENCRYPTION_KEY +"integer not null"+CR_SIGNING_KEY +"integer not null"
			+CR_NAME + " text not null, "+CR_PASSWORD +" text not null, " 
            + CR_CREATOR +" text not null, "+ CR_TIME +" text not null, "
            + CR_TEXT+" text not null);";
	
	private static final String CHANNELS_CREATE= "create table "
			+ TABLE_CHANNELS + "(" + KEY_ID
			+ "integer primary key autoincrement, "+ CH_U_ID
            + "integer not null, "+CH_TYPE +" text not null, " 
            + CH_PH_NUM +" text not null, "+ CH_TIME +" text not null, "
            + CH_PREF+" text not null);";
	
	private static final String MESSAGEQ_CREATE= "create table "
			+ TABLE_MESSAGEQ + "(" + KEY_ID
			+ "integer primary key autoincrement, "+ M_U_ID
            + "integer not null, "+M_CR_ID +" integer not null, " 
            + M_CH_ID +" text not null, "+ M_ENC_MSG +" text not null, "
            + M_TIME+" text not null);";
	
	private static final String MYCHANNELS_CREATE= "create table "
			+ TABLE_MYCHANNELS + "(" + KEY_ID
			+ "integer primary key autoincrement, "+ MCH_HOST
            + "integer not null, "+MCH_SERVICE +" text not null, " 
            + MCH_USR_NAME +" text not null, "+ MCH_PWD +" text not null);";
	private static final String CRP_CREATE= "create table "
			+ TABLE_CHAT_ROOMS_PROFILE + "(" + KEY_ID
			+ "integer primary key autoincrement, "+ CRP_CRID
            + "integer not null, "+CRP_MAC_CUR +" text not null, " 
            + CRP_MAC_OLD +" text not null);";
	
	// private static final String DEVICE_CREATE =
	// "create table tbl_device(_id integer primary key autoincrement, device_name text not null,device_email text not null,device_regid text not null,device_imei text not null);";

	/**** Used to open database in syncronized way ****/
	private DataBaseHelper DBHelper = null;

	private Context context;

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
	private static class DataBaseHelper extends SQLiteOpenHelper {
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
				db.execSQL(CHANNELS_CREATE);
				db.execSQL(POSTS_CREATE);
				db.execSQL(COMMENTS_CREATE);
				db.execSQL(MESSAGEQ_CREATE);
				db.execSQL(MYCHANNELS_CREATE);
				db.execSQL(CRP_CREATE);
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
			//ArrayList<Channel> Chlist =uData.getChlist();
			
			ContentValues cVal = new ContentValues();
			cVal.put(U_EMAIL_ID, email_id);
			cVal.put(U_NAME, name);
			cVal.put(U_PH_NO, ph_no);
			//for(Channel c:Chlist)
			//{
			//cVal.put(U_CHANNELS, Chlist);
			//}
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
	
	
	public void addMessage(MessageQ msg) {
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

			//Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}
	
	public MessageQ getMessageQData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHAT_ROOMS, new String[] { KEY_ID, M_U_ID,M_CR_ID,
				M_CH_ID,M_ENC_MSG,M_TIME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MessageQ data = new MessageQ(Integer.parseInt(cursor.getString(0)),
				cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),cursor.getString(4),cursor.getString(5));
		// return contact
		return data;
	}
	
	public void addMyChannels(MyChannel ch) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();

			String host = sqlEscapeString(ch.getHost());
			String service = sqlEscapeString(ch.getService());
			String usrname = sqlEscapeString(ch.getusername());
			String pwd = sqlEscapeString(ch.getpwd());
			//String pref = sqlEscapeString(ch.getpreffered_Ch());
			
			ContentValues cVal = new ContentValues();
			//cVal.put(CH_U_ID, uid);
			cVal.put(MCH_HOST, host);
			cVal.put(MCH_SERVICE, service);			
			cVal.put(MCH_USR_NAME, usrname);
			cVal.put(MCH_PWD, pwd);
			db.insert(TABLE_MYCHANNELS, null, cVal);
			db.close(); // Closing database connection

			//Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}
	
	public MyChannel getMyChannelsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_MYCHANNELS, new String[] { KEY_ID, MCH_HOST,MCH_SERVICE,MCH_USR_NAME,MCH_PWD }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MyChannel data = new MyChannel(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
		// return contact
		return data;
	}
	
	public void addChannels(Channel ch) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();

			int uid = sqlEscapeString(ch.getU_id());
			String type = sqlEscapeString(ch.getC_Type());
			String phnum = sqlEscapeString(ch.getph_Num());
			String time = sqlEscapeString(ch.getTIME());
			String pref = sqlEscapeString(ch.getpreffered_Ch());
			
			ContentValues cVal = new ContentValues();
			cVal.put(CH_U_ID, uid);
			cVal.put(CH_TYPE, type);
			cVal.put(CH_PH_NUM, phnum);			
			cVal.put(CH_TIME, time);
			cVal.put(CH_PREF, pref);
			db.insert(TABLE_CHANNELS, null, cVal);
			db.close(); // Closing database connection

			//Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}
	
	

	public Channel getChannelsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHANNELS, new String[] { KEY_ID, CH_U_ID,CH_TYPE,
				CH_PH_NUM,CH_TIME,CH_PREF }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Channel data = new Channel(Integer.parseInt(cursor.getString(0)),
				cursor.getInt(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5));
		// return contact
		return data;
	}
	
	public void addChatRoomProfileData(ChatRoom_Profile crp) {
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

			//Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}
	
	public ChatRoom_Profile getChatRoomsProfileData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHAT_ROOMS_PROFILE, new String[] { KEY_ID, CRP_CRID,CRP_MAC_CUR,
				CRP_MAC_OLD }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ChatRoom_Profile data = new ChatRoom_Profile(Integer.parseInt(cursor.getString(0)),cursor.getInt(1),cursor.getString(2),cursor.getString(3));
		// return contact
		return data;
	}
	
	public void addChatRoomsData(ChatRooms cr) {
		try {
			if (DBHelper == null) {
				init();
				Log.d(TAG, "init with context");
			}
			final SQLiteDatabase db = open();
            int usrid = sqlEscapeString(cr.getUserID());
            int authold = sqlEscapeString(cr.getauthkey_old());
            int authnew = sqlEscapeString(cr.getauthkey_new());
            int enckey = sqlEscapeString(cr.getenc_key());
            int signkey = sqlEscapeString(cr.getSigningKey());
			String CRName = sqlEscapeString(cr.getCR_name());
			String Pwd = sqlEscapeString(cr.getPassword());
			String creator = sqlEscapeString(cr.getCREATOR());
			String time = sqlEscapeString(cr.getTIME());
			String txt = sqlEscapeString(cr.getText());
			
			ContentValues cVal = new ContentValues();
			cVal.put(CR_USER_ID, usrid);
			cVal.put(CR_AUTHKEY_OLD, authold);
			cVal.put(CR_AUTHKEY_NEW, authnew);
			cVal.put(CR_ENCRYPTION_KEY, enckey);
			cVal.put(CR_SIGNING_KEY, signkey);
			cVal.put(CR_NAME, CRName);
			cVal.put(CR_PASSWORD, Pwd);
			cVal.put(CR_CREATOR, creator);			
			cVal.put(CR_TIME, time);
			cVal.put(CR_TEXT, txt);
			db.insert(TABLE_CHAT_ROOMS, null, cVal);
			db.close(); // Closing database connection

			//Log.i("Database", "Added Post " + cr.get);
		} catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
	}
	
	public ChatRooms getChatRoomsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_CHAT_ROOMS, new String[] { KEY_ID, CR_USER_ID,CR_AUTHKEY_OLD,
				CR_AUTHKEY_NEW,CR_ENCRYPTION_KEY,CR_SIGNING_KEY,CR_NAME,CR_PASSWORD,
				CR_CREATOR,CR_TIME,CR_TEXT }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ChatRooms data = new ChatRooms(Integer.parseInt(cursor.getString(0)),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),
				cursor.getInt(5),cursor.getString(6), cursor.getString(7), cursor.getString(8),cursor.getString(9),cursor.getString(10));
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
            
			int crid = sqlEscapeString(pt.getCR_id());
			String title=sqlEscapeString(pt.getTitle());
			String creator = sqlEscapeString(pt.getCREATOR());
			String time = sqlEscapeString(pt.getTIME());
			String msg = sqlEscapeString(pt.getMessage());

			ContentValues cVal = new ContentValues();
			cVal.put(P_CR_ID, crid);
			cVal.put(P_TITLE, title);
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
		//Posts post = new Posts(0, CurrentUser.getName(), time, message,title);
		//addPostsData(post);

	}

	public Posts getPostsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_POSTS, new String[] { KEY_ID, P_CREATOR,
				P_TIME, P_MSG }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Posts data = new Posts(Integer.parseInt(cursor.getString(0)),
				cursor.getInt(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5));
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
		Log.d(TAG, "addCommentsData ct");
		final SQLiteDatabase db = open();
		Log.d(TAG, "addCommentsData ct: got db");
        
		int crid=sqlEscapeString(ct.getCR_id());
		String creator = sqlEscapeString(ct.getCREATOR());
		String time = getTime();
		String msg = sqlEscapeString(ct.getMessage());
		int post_id = sqlEscapeString(ct.getP_id());
		Log.d(TAG, "addCommentsData ct: set comment values");

		ContentValues cVal = new ContentValues();
		cVal.put(C_CR_ID, crid);
		cVal.put(C_P_ID, post_id);
		cVal.put(C_CREATOR, creator);
		cVal.put(C_TIME, time);
		cVal.put(C_MSG, msg);
		//cVal.put(C_P_ID, post_id);
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
		//Comments ct = new Comments(0, CurrentUser.getName(), null, message,
				//postId);
		Log.d(TAG, "addCommentsData: created comment object");
		//addCommentsData(ct);
	}

	public Comments getCommentsData(int id) {
		final SQLiteDatabase db = open();

		Cursor cursor = db.query(TABLE_COMMENTS, new String[] { KEY_ID, C_P_ID,C_CR_ID,C_CREATOR, 
				C_MSG, C_TIME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Comments data = new Comments(Integer.parseInt(cursor.getString(0)),
				cursor.getInt(1), cursor.getInt(2), cursor.getString(3),
				cursor.getString(4),cursor.getString(5));
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
	
	private int sqlEscapeString(int u_id) {
		// TODO Auto-generated method stub
		int aReturn=0;
		
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