package com.cs9033.classified.controllers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class MessagePollService extends IntentService {

	public static final String ACTION_SAVE_MESSAGE = "ACTION_SAVE_MESSAGE";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public MessagePollService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle extras = intent.getExtras();
		String action = intent.getAction();
		if (action == ACTION_SAVE_MESSAGE) {
			
		}
	}
}
