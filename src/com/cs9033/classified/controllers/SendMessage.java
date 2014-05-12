package com.cs9033.classified.controllers;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.create.AddUserActivity;
import com.cs9033.classified.create.JoinChatRoomUserActivity;
import com.cs9033.classified.models.MyProfile;

public class SendMessage extends IntentService {

	public static final String ADD_CHAT_ROOM_ACTION = "ADD_CHAT_ROOM_ACTION";
	public static final String ADD_POST_ACTION = "ADD_POST_ACTION";
	public static final String ADD_COMMENT_ACTION = "ADD_COMMENT_ACTION";
	public static final String IKE_ACTION_PHASE1 = "IKE_ACTION_PHASE1";
	public static final String IKE_ACTION_PHASE2 = "IKE_ACTION_PHASE2";
	public static final String IKE_ACTION_PHASE3 = "IKE_ACTION_PHASE3";

	public static final String TAG = "SendMessage";

	public SendMessage(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SendMessage() {
		// TODO Auto-generated constructor stub
		super(null);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getAction();
		ChatRoomsDBAdapter adapter = new ChatRoomsDBAdapter(this);

		MyProfile myProfile = adapter.getMyProfiledata();

		switch (action) {
		case IKE_ACTION_PHASE1:
			try {
				Bundle extras = intent.getExtras();
				String xChange2 = extras.getString(AddUserActivity.PHASE2KEY);

				JSONObject json = new JSONObject();
				json.put("TYPE", IKE_ACTION_PHASE1).put(
						AddUserActivity.PHASE2KEY, xChange2);

				String host = extras.getString(JoinChatRoomUserActivity.HOST);
				int port = extras.getInt(JoinChatRoomUserActivity.PORT);
				String server = extras
						.getString(JoinChatRoomUserActivity.SERVER);
				String to = extras
						.getString(JoinChatRoomUserActivity.USER_NAME);

				Log.d(TAG, host);
				Log.d(TAG, "" + port);
				Log.d(TAG, server);
				Log.d(TAG, to);
				Log.d(TAG, json.toString());
				String message = new String(Hex.encodeHex(json.toString()
						.getBytes()));
				Messager.sendChatMessage(host, port, server, message, to,
						myProfile);
			} catch (JSONException e) {
				Log.e(TAG, e.getClass().getName(), e);
			}
			break;
		case ADD_COMMENT_ACTION:
			break;
		case ADD_POST_ACTION:
			break;
		case ADD_CHAT_ROOM_ACTION:
			break;
		}

	}
}
