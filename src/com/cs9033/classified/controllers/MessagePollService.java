package com.cs9033.classified.controllers;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.create.AddUserActivity;
import com.cs9033.classified.create.JoinChatRoomUserActivity;
import com.cs9033.classified.models.MyProfile;

//implements FileTransferListener

public class MessagePollService extends IntentService {
	private static final String TAG = "MessagePollService";
	public static final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED_ACTION";
	public static final String CHAT_RECEIVED_ACTION = "CHAT_RECEIVED_ACTION";
	public static final String BLOCK_WAIT = "BLOCK_WAIT";
	public static final String LONG_POLL = "LONG_POLL";
	public static final int POLL_INTERVAL = 60 * 1000;

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
		String action = intent.getAction();
		ChatRoomsDBAdapter adapter = new ChatRoomsDBAdapter(this);

		MyProfile myProfile = adapter.getMyProfiledata();

		ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
				myProfile.getXmpp_host(), myProfile.getXmpp_port(),
				myProfile.getXmpp_server());

		XMPPConnection connection = new XMPPConnection(connectionConfiguration);
		try {
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.connect();

			Log.d(TAG, "Connected");
			connection.login(myProfile.getXmpp_user_name(),
					myProfile.getXmpp_password());
			Log.d(TAG, "Logged in");
		} catch (XMPPException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
		PacketCollector collector = connection.createPacketCollector(filter);
		Packet packet = null;

		if (action.equals(LONG_POLL)) {
			packet = collector.pollResult();

		} else {
			packet = collector.nextResult();
		}
		if (packet != null) {
			Log.d(TAG, "Packet Found");
		} else {
			return;
		}
		if (packet instanceof Message) {
			Message message = (Message) packet;
			if (message != null && message.getBody() != null) {
				String body = message.getBody();

				Log.d(TAG, "Body is " + body);
				try {
					String msg = new String(Hex.decodeHex(body.toCharArray()));
					JSONObject json = new JSONObject(msg);
					Log.d(TAG, json.toString());
					if (json.has("TYPE")) {
						String type = json.getString("TYPE");

						switch (type) {
						case SendMessage.IKE_ACTION_PHASE1:
							SharedPreferences sharedPreferences = getSharedPreferences(
									JoinChatRoomUserActivity.JOIN_CHAT,
									Context.MODE_PRIVATE);
							sharedPreferences
									.edit()
									.putString(
											AddUserActivity.PHASE2KEY,
											json.getString(AddUserActivity.PHASE2KEY))
									.commit();
							break;

						case SendMessage.IKE_ACTION_PHASE2:

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

	}

	public static void setServiceAlarm(Context context, boolean isON) {
		Intent i = new Intent(context, MessagePollService.class);
		i.setAction(LONG_POLL);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		if (isON) {
			Log.d(TAG, "Setting alarm to " + POLL_INTERVAL + "ms");
			alarmManager.setRepeating(AlarmManager.RTC, 0, POLL_INTERVAL, pi);
			Log.d(TAG, "Finished Set Alarm");
		} else {
			Log.d(TAG, "Clear Alarm");
			alarmManager.cancel(pi);
			pi.cancel();
		}
	}
}
