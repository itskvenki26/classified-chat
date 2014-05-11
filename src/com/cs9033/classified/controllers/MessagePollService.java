package com.cs9033.classified.controllers;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.crypto.SecureMessage;

//implements FileTransferListener

public class MessagePollService extends IntentService {
	private static final String TAG = "MessagePollService";
	public static final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED_ACTION";
	public static final String CHAT_RECEIVED_ACTION = "CHAT_RECEIVED_ACTION";
	private static boolean running = false;
	static XMPPConnection connection;
	FileTransferManager manager;

	public MessagePollService() {
		super(null);
	}

	public static void start() {
		running = false;
		init();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		// init();
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

	static void init() {

		// Get list of hosts from My Profile table
		// For each host check chat messages with code template
		if (!running) {

			// -------------------------------------------------------------
			ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
					"talk.google.com", 5222, "nyu.edu");
			// connectionConfiguration.setTruststoreType("jks");
			// connectionConfiguration.setSASLAuthenticationEnabled(true);

			connection = new XMPPConnection(connectionConfiguration);

			try {
				Log.d(TAG, "Test started");
				SASLAuthentication.supportSASLMechanism("PLAIN", 0);
				connection.connect();
				Log.d(TAG, "Connected");
				connection.login("ptv205@nyu.edu", "vwwmmljnoootxxkt");
				Log.d(TAG, "Logged in");
				XMPPConnection.DEBUG_ENABLED = true;

				PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
				PacketListenerImpl listenerImpl = new PacketListenerImpl();
				connection.addPacketListener(listenerImpl, filter);
//				listenerImpl.
				Log.d(TAG, "Added Packet Listener");
				// manager = new FileTransferManager(connection);
				// manager.addFileTransferListener(this);
				running = true;

			} catch (Exception e) {
				Log.e(TAG, e.getClass().getName(), e);
			}
			// -------------------------------------------------------------
			// ConnectionConfiguration connConfig = new ConnectionConfiguration(
			// "talk.google.com", 5222/* "PORT" */, "nyu.edu");
			//
			// connConfig.setTruststoreType("jks");
			// connConfig.setSASLAuthenticationEnabled(true);
			//
			// connection = new XMPPConnection(connConfig);
			// try {
			// // Connect to the server
			// connection.connect();
			// Log.d(TAG, "Connected to nyu.edu");
			// } catch (XMPPException e) {
			// Log.e(TAG, e.getClass().getName(), e);
			// connection = null;
			// // Unable to connect to server
			// }
			//
			// try {
			// connection.login("prasad.tv@nyu.edu", "iwbo7-3-1988");
			// Log.d(TAG, "Logged in to nyu.edu");
			// } catch (XMPPException e) {
			// Log.e(TAG, e.getClass().getName(), e);
			// }
			//
			// PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			// connection.addPacketListener(new PacketListenerImpl(), filter);
			// Log.d(TAG, "Added Packet Listener");
			// // manager = new FileTransferManager(connection);
			// // manager.addFileTransferListener(this);
			// running = true;
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.d(TAG, "onHandleIntent: Service Handling intent");
		Bundle extras = intent.getExtras();
		Log.d(TAG, "onHandleIntent: gotExtras");
		String action = intent.getAction();
		Log.d(TAG, "onHandleIntent: gotAction");
		if (action == MESSAGE_RECEIVED_ACTION) {
			Log.d(TAG, "onHandleIntent: SMS_RECEIVED action");
			String message = extras.getString("sms");
			String number = extras.getString("number");
			// Log.d(TAG, "onHandleIntent: got sms " + message);
			processMessage(number, message);
		} else if (action == CHAT_RECEIVED_ACTION) {
			Log.d(TAG, "onHandleIntent: CHAT_RECEIVED action");
			String message = extras.getString("body");
			String from = extras.getString("from");
			// Log.d(TAG, "onHandleIntent: got sms " + message);
			processMessage(from, message);
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

		ChatRoomsDBAdapter db = new ChatRoomsDBAdapter(this);
//		String[] keys = db.getXKeys();
//		String current_mac_key = keys[0];
//		String current_e_key = keys[1];

		try {
			// String jsonText = SecureMessage.decrypt(current_e_key,
			// messageBody);
			// Log.d(TAG, "Received json:" + jsonText);
			// JSONObject json = new JSONObject(jsonText);
			//
			// String next_mac_key = json.getString("next_mac_key");
			// String next_e_key = json.getString("next_e_key");
			// String post = json.getString("message");
			//
			// Log.d(TAG, "Clear Text" + post);
			//
			// db.updatePost(post);
			// db.updateXKeys(next_mac_key, next_e_key);

		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
		// insert message to demo table

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
