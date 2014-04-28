package com.cs9033.classified.controllers;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

//implements FileTransferListener

public class MessagePollService extends IntentService {
	private static final String TAG = "MessagePollService";
	public static final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED_ACTION";
	public static final String CHAT_RECEIVED_ACTION = "CHAT_RECEIVED_ACTION";
	private boolean running = false;
	XMPPConnection connection;
	FileTransferManager manager;

	public MessagePollService() {
		super(null);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		init();
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

	void init() {

		// Get list of hosts from My Profile table
		// For each host check chat messages with code template
		if (!running) {
			ConnectionConfiguration connConfig = new ConnectionConfiguration(
					"HOST", 0/* "PORT" */, "SERVICE");
			connection = new XMPPConnection(connConfig);
			try {
				// Connect to the server
				connection.connect();
			} catch (XMPPException ex) {
				Log.e("Chat", "Connection Failed");
				connection = null;
				// Unable to connect to server
			}

			try {
				connection.login("USER_ID", "PASSWORD");
			} catch (XMPPException e) {
				Log.e("Chat", "Login Failed");
			}

			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			connection.addPacketListener(new PacketListenerImpl(), filter);
			// manager = new FileTransferManager(connection);
			// manager.addFileTransferListener(this);
			running = true;
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.d(TAG, "onHandleIntent: Service Handling intent");
		Bundle extras = intent.getExtras();
		Log.d(TAG, "onHandleIntent: gotExtars");
		String action = intent.getAction();
		Log.d(TAG, "onHandleIntent: gotAction");
		if (action == MESSAGE_RECEIVED_ACTION) {
			Log.d(TAG, "onHandleIntent: SMS_RECEIVED action");
			String message = extras.getString("sms");
			String number = extras.getString("number");
			// Log.d(TAG, "onHandleIntent: got sms " + message);
			processMessage(number, message);
		}

	}

	synchronized void processMessage(String from, String messageBody) {
		// Check from (is user exists in user tables)
		// decrypt Message
		// Check Session check if session ID is OK
		// Check source
		// Check message
		// Encrypt with LTP
		// Push message to DB

	}

	// @Override
	// public void fileTransferRequest(FileTransferRequest request) {
	//
	// IncomingFileTransfer transfer = request.accept();
	// File mf = Environment.getExternalStorageDirectory();// Try to store on
	// // local storage
	// File file = new File(mf.getAbsoluteFile() + "/DCIM/Camera/"
	// + transfer.getFileName());
	// try {
	// transfer.recieveFile(file);
	// while (!transfer.isDone()) {
	// try {
	// Thread.sleep(1000L);
	// } catch (Exception e) {
	// Log.e("", e.getMessage());
	// }
	// if (transfer.getStatus().equals(Status.error)) {
	// Log.e("ERROR!!! ", transfer.getError() + "");
	// }
	// if (transfer.getException() != null) {
	// transfer.getException().printStackTrace();
	// }
	// }
	// } catch (Exception e) {
	// Log.e("", e.getMessage());
	// }
	// }
}
