package com.cs9033.classified.models;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

public class User {
	private static final String TAG = "User";
	// private variables
	long id = -1l;
	String email_id;
	String name;
	String ph_no;
	String xmpp_host;
	String xmpp_server;
	int xmpp_port;
	String xmpp_user_name;
	long cr_id;
	String current_mac;
	String current_e;
	// int pref_channel_id;

	// ArrayList<Channel> Chlist = new ArrayList<Channel>();

	public static final String U_EMAIL_ID = "user_email";
	public static final String U_NAME = "user_name";
	public static final String U_PH_NO = "user_phone_num";
	public static final String U_XMPP_HOST = "user_xmpp_host";
	public static final String U_XMPP_SERVER = "user_xmpp_server";
	public static final String U_XMPP_PORT = "user_xmpp_port";
	public static final String U_XMPP_USER_NAME = "user_xmpp_user_name";
	public static final String U_CR_ID = "user_cr_id";
	public static final String U_CURRENT_MAC="user_current_mac";
	public static final String U_CURRENT_E="user_current_Enc_key";
	// public static final String U_CHANNELS="list of user channels";
	

	// Empty constructor
	public User() {

	}

	// constructor
		
	public User(String email_id, String name, String ph_no, String xmpp_host,
			String xmpp_server, int xmpp_port, String xmpp_user_name,
			long cr_id, String current_mac, String current_e) {
		super();
		this.email_id = email_id;
		this.name = name;
		this.ph_no = ph_no;
		this.xmpp_host = xmpp_host;
		this.xmpp_server = xmpp_server;
		this.xmpp_port = xmpp_port;
		this.xmpp_user_name = xmpp_user_name;
		this.cr_id = cr_id;
		this.current_mac = current_mac;
		this.current_e = current_e;
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

	

	public long getCr_id() {
		return cr_id;
	}

	public void setCr_id(long cr_id) {
		this.cr_id = cr_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPh_no() {
		return ph_no;
	}

	public void setPh_no(String ph_no) {
		this.ph_no = ph_no;
	}

	public String getXmpp_host() {
		return xmpp_host;
	}

	public void setXmpp_host(String xmpp_host) {
		this.xmpp_host = xmpp_host;
	}

	public String getXmpp_server() {
		return xmpp_server;
	}

	public void setXmpp_server(String xmpp_server) {
		this.xmpp_server = xmpp_server;
	}

	public int getXmpp_port() {
		return xmpp_port;
	}

	public void setXmpp_port(int xmpp_port) {
		this.xmpp_port = xmpp_port;
	}

	public String getXmpp_user_name() {
		return xmpp_user_name;
	}

	public void setXmpp_user_name(String xmpp_user_name) {
		this.xmpp_user_name = xmpp_user_name;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		json.put(U_EMAIL_ID, email_id);
		json.put(U_NAME, name);
		json.put(U_PH_NO, ph_no);
		json.put(U_XMPP_HOST, xmpp_host);
		json.put(U_XMPP_PORT, xmpp_port);
		json.put(U_XMPP_SERVER, xmpp_server);
		json.put(U_XMPP_USER_NAME, xmpp_user_name);
		json.put(U_CURRENT_MAC, current_mac);
		json.put(U_CURRENT_E, current_e);
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

	public static User fromHexString(String hex) {

		try {
			JSONObject json = new JSONObject(new String(Hex.decodeHex(hex
					.toCharArray())));
			return new User(json.getString(U_EMAIL_ID), json.getString(U_NAME),
					json.getString(U_PH_NO), json.getString(U_XMPP_HOST),
					json.getString(U_XMPP_SERVER), json.getInt(U_XMPP_PORT),
					json.getString(U_XMPP_USER_NAME), json.getLong(U_CR_ID),json.getString(U_CURRENT_MAC),json.getString(U_CURRENT_E));

		} catch (JSONException | DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	boolean saveToDB(Context context) {

		ChatRoomsDBAdapter db = new ChatRoomsDBAdapter(context);
		db.addUserData(this);
		return false;

	}

}