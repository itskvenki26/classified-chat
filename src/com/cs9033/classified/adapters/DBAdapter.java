package com.cs9033.classified.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper {

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

	public DBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
