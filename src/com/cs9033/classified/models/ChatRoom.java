package com.cs9033.classified.models;

import java.util.Calendar;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

public class ChatRoom {
	private static final String TAG = "ChatRoom";

	long id = -1l;
	String current_mac;
	String current_e;
	String old_mac;
	String cr_name;
	String time;
	String description;

	public static final String CR_NAME = "Chat_Room_name";
	public static final String CR_TIME = "Chat_Room_time";
	public static final String CR_DESCRIPTION = "Chat_Room_text";
	public static final String CR_CURRENT_MAC = "Chat_Room_Current_MAC";
	public static final String CR_OLD_MAC = "Chat_Room_Old_MAC";
	public static final String CR_CURRENT_E = "Chat_Room_Current_ENC_Key";

	// Empty constructor
	public ChatRoom() {

	}

	public ChatRoom(String current_mac, String current_e, String old_mac,
			String cR_name, String description) {
		super();
		this.current_mac = current_mac;
		this.current_e = current_e;
		this.old_mac = old_mac;
		this.cr_name = cR_name;
		Calendar c = Calendar.getInstance();
		this.time = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + " "
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
		this.description = description;
	}

	public ChatRoom(String current_mac, String current_e, String old_mac,
			String cR_name, String time, String description) {
		super();
		this.current_mac = current_mac;
		this.current_e = current_e;
		this.old_mac = old_mac;
		this.cr_name = cR_name;
		this.time = time;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCurrent_mac() {
		return current_mac;
	}

	public void setCurrent_mac(String current_mac) {
		this.current_mac = current_mac;
	}

	public String getCurrent_e() {
		return current_e;
	}

	public void setCurrent_e(String current_e) {
		this.current_e = current_e;
	}

	public String getOld_mac() {
		return old_mac;
	}

	public void setOld_mac(String old_mac) {
		this.old_mac = old_mac;
	}

	public String getCR_name() {
		return cr_name;
	}

	public void setCR_name(String cR_name) {
		cr_name = cR_name;
	}

	public String getTime() {
		if (time == null) {
			Calendar c = Calendar.getInstance();
			time = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-"
					+ c.get(Calendar.DAY_OF_MONTH) + " "
					+ c.get(Calendar.HOUR_OF_DAY) + ":"
					+ c.get(Calendar.MINUTE);
		}
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JSONObject toJSON() throws JSONException {

		JSONObject json = new JSONObject();
		json.put(CR_NAME, cr_name);
		json.put(CR_TIME, time);
		json.put(CR_DESCRIPTION, description);
		json.put(CR_CURRENT_E, current_e);
		json.put(CR_CURRENT_MAC, current_mac);
		json.put(CR_OLD_MAC, old_mac);

		return json;
	}

	public String toHexString() {
		try {
			return new String(Hex.encodeHex(toJSON().toString().getBytes()));
		} catch (JSONException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
		return null;
	}

	public static ChatRoom fromHexString(String hex) {

		try {
			JSONObject json = new JSONObject(new String(Hex.decodeHex(hex
					.toCharArray())));
			return new ChatRoom(json.getString(CR_CURRENT_MAC),
					json.getString(CR_CURRENT_E), json.getString(CR_OLD_MAC),
					json.getString(CR_NAME), json.getString(CR_TIME),
					json.getString(CR_DESCRIPTION));

		} catch (JSONException | DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	boolean saveToDB(Context context) {

		ChatRoomsDBAdapter db = new ChatRoomsDBAdapter(context);
		db.addChatRoomData(this);
		return false;

	}

}