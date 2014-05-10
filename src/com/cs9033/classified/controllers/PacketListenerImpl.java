package com.cs9033.classified.controllers;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.util.Log;

public class PacketListenerImpl implements PacketListener {
	public static final String TAG = "PacketListenerImpl";

	@Override
	public void processPacket(Packet packet) {
		Message message = (Message) packet;
		message.setLanguage(Message.getDefaultLanguage());
		String from = message.getFrom();
		String body = message.getBody();
		if (body != null) {
			// Intent broadcastIntent = new
			// Intent("com.cs9033.classified.controllers.MessagePollService"
			// );
			// broadcastIntent.setAction(MessagePollService.CHAT_RECEIVED_ACTION);
			// broadcastIntent.putExtra("chat", body);
			// broadcastIntent.putExtra("from", from);
			// context.startService(broadcastIntent);
			Log.d(TAG, from + ":" + body);

			// Toast.makeText(MainActivity.this, from + ":" + body,
			// Toast.LENGTH_SHORT).show();

			// Check if from user is listed in users list

			// if so
			// get users current mac key
			// SecureMessage.verifyMAC(current_mac_key, encryptedMessage)
			// SecureMessage.decrypt(current_e_key, encryptedMessage);
			// Split Message
			// insert text into table
			// update keys
			// processMessage(from, body);
		}
	}

}
