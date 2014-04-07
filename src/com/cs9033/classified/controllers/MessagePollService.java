package com.cs9033.classified.controllers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cs9033.classified.SessionDBAdapter;

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
		Log.d(TAG, "Service Handling intent");
		Bundle extras = intent.getExtras();
		String action = intent.getAction();
		if (action == SMS_RECEIVED_ACTION) {
			Log.d(TAG, "Intent Handeled, adding " + extras.getString("sms"));
			SessionDBAdapter.addPostsData(extras.getString("sms"));
		}
	}
}
