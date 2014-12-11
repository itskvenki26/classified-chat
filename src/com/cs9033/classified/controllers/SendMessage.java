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
import com.cs9033.classified.models.User;

public class SendMessage extends IntentService {

	public static final String ADD_CHAT_ROOM_ACTION = "ADD_CHAT_ROOM_ACTION";
	public static final String ADD_POST_ACTION = "ADD_POST_ACTION";
	public static final String ADD_COMMENT_ACTION = "ADD_COMMENT_ACTION";
	public static final String IKE = "IKE";
	public static final String MESSAGE = "MESSAGE";

	public static final String TAG = "SendMessage";
	public static final String HOST = "HOST";
	public static final String PORT = "PORT";
	public static final String SERVER = "SERVER";
	public static final String USER_NAME = "USER_NAME";
	public static final String IKE_PHASE3 = "IKE_PHASE3";

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
		case IKE:
			try {
				Bundle extras = intent.getExtras();
				String xChange2 = extras.getString(AddUserActivity.PHASE2KEY);

				JSONObject json = new JSONObject();
				json.put("TYPE", IKE).put(AddUserActivity.PHASE2KEY, xChange2);

				String host = extras.getString(JoinChatRoomUserActivity.HOST);
				int port = extras.getInt(JoinChatRoomUserActivity.PORT);
				String server = extras
						.getString(JoinChatRoomUserActivity.SERVER);
				String to = extras
						.getString(JoinChatRoomUserActivity.USER_NAME);
				Log.d(TAG, json.toString());
				String message = new String(Hex.encodeHex(json.toString()
						.getBytes()));
				Messager.sendChatMessage(host, port, server, message, to,
						myProfile);
			} catch (JSONException e) {
				Log.e(TAG, e.getClass().getName(), e);
			}
			break;
		case ADD_CHAT_ROOM_ACTION:
			Log.d(TAG, "case: " + ADD_CHAT_ROOM_ACTION);
			Bundle extras = intent.getExtras();
			String host = extras.getString(HOST);
			int port = extras.getInt(PORT);
			String server = extras.getString(SERVER);
			String to = extras.getString(USER_NAME);
			String message = extras.getString(MESSAGE);
			String hex = new String(Hex.encodeHex(message.getBytes()));
			Messager.sendChatMessage(host, port, server, hex, to, myProfile);
			break;
		case ADD_POST_ACTION:
			Bundle extraPost = intent.getExtras();
			long CRID = extraPost.getLong("CRID");
			String CRName = extraPost.getString("CRName");
			String postMsg = new String(Hex.encodeHex(extraPost.getString(
					MESSAGE).getBytes()));
			ChatRoomsDBAdapter postAdapter = new ChatRoomsDBAdapter(this);
			User[] postUsers = postAdapter.getUsersData(CRID);

			for (User u : postUsers) {
				Messager.sendChatMessage(u.getXmpp_host(), u.getXmpp_port(),
						u.getXmpp_server(), postMsg, u.getXmpp_user_name(),
						myProfile);
			}
			break;
		}
	}
}
