package com.cs9033.classified.controllers;

import java.util.ArrayList;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.cs9033.classified.models.MyProfile;
import com.cs9033.classified.models.User;

public class Messager {

	private static final String TAG = "Messager";

	private static String SENT = "SMS_SENT";
	private static String DELIVERED = "SMS_DELIVERED";
	private static int MAX_SMS_MESSAGE_LENGTH = 160;

	// To return list of messages waiting to be sent
	// private void getQueuedMessagesForSend() {
	//
	// }
	//
	// private void getType() {
	//
	// }

	// void sendSMSMessage() {
	// if Message Type is SMS, send SMS

	// if Message type is chat, get chat parameters and send as chat

	// get chat channel

	// get current user login for the channel
	// get current user password for channel
	// get encrypted message from DB(message Object)
	// get parameters from settings for channel
	// setup chatter

	// }

	public static void sendSMS(Context context, String phoneNumber,
			String message) {
		Log.d(TAG, "Sending SMS");
		PendingIntent piSent = PendingIntent.getBroadcast(context, 0,
				new Intent(SENT), 0);
		PendingIntent piDelivered = PendingIntent.getBroadcast(context, 0,
				new Intent(DELIVERED), 0);
		SmsManager smsManager = SmsManager.getDefault();

		int length = message.length();
		if (length > MAX_SMS_MESSAGE_LENGTH) {
			ArrayList<String> messagelist = smsManager.divideMessage(message);
			smsManager.sendMultipartTextMessage(phoneNumber, null, messagelist,
					null, null);
		} else
			smsManager.sendTextMessage(phoneNumber, null, message, piSent,
					piDelivered);
	}

	public static void sendChatMessage(String host, int port, String server,
			String message, String to, MyProfile myProfile) {
		(new Thread(new Chatter(host, port, server, to, message, myProfile)))
				.start();
	}

	public static void sendChatMessage(User user, String message,
			MyProfile myProfile) {
		sendChatMessage(user.getXmpp_host(), user.getXmpp_port(),
				user.getXmpp_server(), message, user.getXmpp_user_name(),
				myProfile);
	}

	public static void sendChatMessage(User[] users, String message,
			MyProfile myProfile) {
		if (users != null) {
			for (User u : users) {
				sendChatMessage(u, message, myProfile);
			}
		}
	}

	private static class Chatter implements Runnable {
		private static final String TAG = "Chatter";
		String host;
		int port;
		String server;
		String to;
		String message;
		MyProfile myProfile;

		public Chatter(String host, int port, String server, String to,
				String message, MyProfile myProfile) {

			this.host = host;
			this.port = 5222;
			this.server = server;
			this.to = to;
			this.message = message;
			this.myProfile = myProfile;
		}

		@Override
		public void run() {
			ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
					host, port, server);
			// connectionConfiguration.setTruststoreType("jks");
			// connectionConfiguration.setSASLAuthenticationEnabled(true);

			XMPPConnection connection = new XMPPConnection(
					connectionConfiguration);

			try {
				SASLAuthentication.supportSASLMechanism("PLAIN", 0);
				connection.connect();

				Log.d(TAG, "Connected");
				connection.login(myProfile.getXmpp_user_name(),
						myProfile.getXmpp_password());
				Log.d(TAG, "Logged in");
				Message message = new Message();
				message.setBody(this.message);
				message.setTo(to);
				message.setType(Message.Type.chat);
				Log.d(TAG, "Prepared Message");
				connection.sendPacket(message);
				Log.d(TAG, "Sent Packet");
			} catch (XMPPException e) {
				Log.e(TAG, e.getClass().getName(), e);
				Toast.makeText(null, "Error Sending Message",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
}