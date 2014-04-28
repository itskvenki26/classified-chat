package com.cs9033.classified.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// ---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		int contactId = -1;
		String number = "";

		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				number = msgs[i].getOriginatingAddress();
				str += msgs[i].getMessageBody().toString();
				str += "\n";
			}

			// Toast.makeText(context, address + ":" + str, Toast.LENGTH_LONG)
			// .show();
			if (contactId != -1) {
				showNotification(contactId, str);
			}

			Intent broadcastIntent = new Intent(context,
					MessagePollService.class);
			broadcastIntent
					.setAction(MessagePollService.MESSAGE_RECEIVED_ACTION);
			broadcastIntent.putExtra("sms", str);
			broadcastIntent.putExtra("number", number);
			context.startService(broadcastIntent);
			Log.d(TAG, "Sent sms:" + str);
		}

	}

	protected void showNotification(int contactId, String message) {
		// Display notification...
	}

}
