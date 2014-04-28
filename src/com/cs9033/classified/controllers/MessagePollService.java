package com.cs9033.classified.controllers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

public class MessagePollService extends IntentService {
	private static final String TAG = "MessagePollService";
	public static final String SMS_RECEIVED_ACTION = "SMS_RECEIVED_ACTION";

	public MessagePollService() {
		super(null);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Service Created");
	}

	public MessagePollService(String name) {
		super(name);
		Log.d(TAG, "Service Polling");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent: Service Handling intent");
		Bundle extras = intent.getExtras();
		Log.d(TAG, "onHandleIntent: gotExtars");
		String action = intent.getAction();
		Log.d(TAG, "onHandleIntent: gotAction");
		if (action == SMS_RECEIVED_ACTION) {
			Log.d(TAG, "onHandleIntent: SMS_RECEIVED action");
			String sms = extras.getString("sms");
			Log.d(TAG, "onHandleIntent: got sms " + sms);

			String[] dsms = sms.split("!");
			if (dsms == null || dsms.length == 0) {
				Log.d(TAG, "Intent Handeled, adding <blank>" + sms);
			}

			if (dsms.length >= 3) {
				Log.d(TAG, "onHandleIntent: split into 3");
				String c = dsms[0];
				String type = dsms[1];
				String msg = dsms[2];
				if (c.equals("c")) {
					Log.d(TAG, "onHandleIntent: is classified sms");
					ChatRoomsDBAdapter sessionDBAdapter = new ChatRoomsDBAdapter(
							this);
					if (type.equals("post")) {
						Log.d(TAG, "onHandleIntent: type=post");
						sessionDBAdapter.addPostsData(msg);

					}
					if (type.equals("comment")) {
						Log.d(TAG, "onHandleIntent: type=comments");
						sessionDBAdapter.addCommentsData(msg, null);

					}
				}
			}
		}
	}
}
