package com.cs9033.classified.controllers;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.create.AddUserActivity;
import com.cs9033.classified.create.JoinChatRoomUserActivity;
import com.cs9033.classified.models.MyProfile;

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

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Service Created");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Intent Received");
		ChatRoomsDBAdapter adapter = new ChatRoomsDBAdapter(this);

		MyProfile myProfile = adapter.getMyProfiledata();

		ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
				myProfile.getXmpp_host(), myProfile.getXmpp_port(),
				myProfile.getXmpp_server());

		XMPPConnection connection = new XMPPConnection(connectionConfiguration);

		PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
		PacketCollector collector = connection.createPacketCollector(filter);

		Packet packet = collector.pollResult();

		if (packet instanceof Message) {
			Message message = (Message) packet;
			if (message != null && message.getBody() != null) {
				String body = message.getBody();
				Toast.makeText(this, body, Toast.LENGTH_SHORT).show();
				try {
					String msg = new String(Hex.decodeHex(body.toCharArray()));
					JSONObject json = new JSONObject(msg);
					if (json.has("TYPE")) {
						String type = json.getString("TYPE");

						switch (type) {
						case SendMessage.IKE_ACTION:
							SharedPreferences sharedPreferences = getSharedPreferences(
									JoinChatRoomUserActivity.JOIN_CHAT,
									Context.MODE_PRIVATE);
							sharedPreferences
									.edit()
									.putString(
											JoinChatRoomUserActivity.KEY2,
											json.getString(AddUserActivity.PHASE2KEY))
									.commit();
							break;

						default:
							break;
						}
					}

				} catch (DecoderException | JSONException e) {
					Log.e(TAG, e.getClass().getName(), e);
					return;
				}
			}
		}

		// try {
		// SASLAuthentication.supportSASLMechanism("PLAIN", 0);
		// connection.connect();
		//
		// Log.d(TAG, "Connected");
		// connection.login(myProfile.getXmpp_user_name(),
		// myProfile.getXmpp_password());
		// Log.d(TAG, "Logged in");
		// Message
		// } catch (XMPPException e) {
		// Log.e(TAG, e.getClass().getName(), e);
		// Toast.makeText(null, "Error Sending Message", Toast.LENGTH_SHORT)
		// .show();
		// }
	}
}
