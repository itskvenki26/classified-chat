package com.cs9033.classified.controllers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cs9033.classified.crypto.SecureMessage;

public class SendMessage extends IntentService {
	public static final String TAG = "SendMessage";

	public SendMessage(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// get Message and session
		Bundle bundle = intent.getExtras();
		String message = bundle.getString("message");
		String chaatroom_id = bundle.getString("chatroom_id");

		// Create SecureMessage Object
		SecureMessage secureMessage = new SecureMessage(message);

		// Generate Mac
		// String mac_new = secureMessage.getMacKey();
		try {
			// Encrypt Message
			String ciphertext = secureMessage.encrypt();
			// Get Preferred Channels
			// For each channel, send the encrypted text
			// Update mac and EKeys
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | JSONException e) {
			Log.e(TAG, e.toString(), e);
		}

	}
}
