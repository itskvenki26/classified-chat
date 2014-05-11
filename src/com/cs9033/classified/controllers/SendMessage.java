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

	public SendMessage() {
		// TODO Auto-generated constructor stub
		super(null);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// get Message and session
		// Bundle bundle = intent.getExtras();
		// String message = bundle.getString("message");
		// String chaatroom_id = bundle.getString("chatroom_id");

		// Create SecureMessage Object
		// Get ClearTextMessage from Inent
		Log.d(TAG, "Started Service");
		Bundle extras = intent.getExtras();

		String message = extras.getString("message");
		Log.d(TAG, "Message is " + message);
		SecureMessage secureMessage = new SecureMessage(this, message);

		// Generate Mac
		String mac_new = secureMessage.getNext_mac_key();
		Log.d(TAG, "mac_new is " + mac_new);
		String e_new = secureMessage.getNext_e_key();
		Log.d(TAG, "e_new is " + e_new);
		String mac_current = secureMessage.getCurrent_mac_key();
		Log.d(TAG, "mac_current is " + mac_current);
		String e_current = secureMessage.getCurrent_e_key();
		Log.d(TAG, "e_current is " + e_current);
		try {
			// Encrypt Message
			String ciphertext = secureMessage.encrypt();
			Log.d(TAG, "ciphertext is  " + ciphertext);
			// Get Preferred Channels
			// For each channel, send the encrypted text
			// Update mac and EKeys
			// Messager.sendSMS(this, "+19177176181", ciphertext);
			MessagePollService.start();
			Messager.sendChatMessage(null, 0, null, ciphertext, null);

			// DemoDBAdapter db = new DemoDBAdapter(this);

			// old_mac = current_mac
			// current_key = new_key
			// current_mac = new_mac

			// String clearText = SecureMessage.decrypt(e_current, ciphertext);
			// Log.d(TAG, "Decrypted");
			// Log.d(TAG, "Clear Text:" + clearText);
			// db.updateKeys(mac_new, e_new, mac_current);

		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | JSONException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

	}
}
