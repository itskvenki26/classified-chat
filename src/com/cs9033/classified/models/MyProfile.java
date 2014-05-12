package com.cs9033.classified.models;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

public class MyProfile {
	private static final String TAG = "MyProfile";

	long id = -1l;
	String email_id;
	String name;
	String ph_no;
	String xmpp_host;
	String xmpp_server;
	int xmpp_port;
	String xmpp_user_name;
	String xmpp_password;

	public static final String MP_EMAIL_ID = "profile_email";
	public static final String MP_NAME = "profile_name";
	public static final String MP_PH_NO = "profile_phone_num";
	public static final String MP_XMPP_HOST = "profile_xmpp_host";
	public static final String MP_XMPP_SERVER = "profile_xmpp_server";
	public static final String MP_XMPP_PORT = "profile_xmpp_port";
	public static final String MP_XMPP_USER_NAME = "profile_xmpp_user_name";
	public static final String MP_XMPP_PASSWORD = "profile_xmpp_password";
	public static final String MP_CR_ID = "profile_cr_id";

	// Empty constructor
	public MyProfile() {

	}

	public MyProfile(String email_id, String name, String ph_no,
			String xmpp_host, String xmpp_server, int xmpp_port,
			String xmpp_user_name, String xmpp_password) {
		super();
		this.email_id = email_id;
		this.name = name;
		this.ph_no = ph_no;
		this.xmpp_host = xmpp_host;
		this.xmpp_server = xmpp_server;
		this.xmpp_port = xmpp_port;
		this.xmpp_user_name = xmpp_user_name;
		this.xmpp_password = xmpp_password;
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

	public String getXmpp_password() {
		return xmpp_password;
	}

	public void setXmpp_password(String xmpp_password) {
		this.xmpp_password = xmpp_password;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		json.put(MP_EMAIL_ID, email_id).put(MP_NAME, name).put(MP_PH_NO, ph_no)
				.put(MP_XMPP_HOST, xmpp_host).put(MP_XMPP_PORT, xmpp_port)
				.put(MP_XMPP_SERVER, xmpp_server)
				.put(MP_XMPP_USER_NAME, xmpp_user_name);

		return json;
	}

	public JSONObject toUserJSON() throws JSONException {

		Log.d(TAG, toJSON().toString());
		JSONObject json = new JSONObject();
		json.put(User.U_EMAIL_ID, email_id);
		json.put(User.U_NAME, name);
		json.put(User.U_PH_NO, ph_no);
		json.put(User.U_XMPP_HOST, xmpp_host);
		json.put(User.U_XMPP_PORT, xmpp_port);
		json.put(User.U_XMPP_SERVER, xmpp_server);
		json.put(User.U_XMPP_USER_NAME, xmpp_user_name);
		Log.d(TAG, json.toString());
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

	public static MyProfile fromHexString(String hex) {

		try {
			JSONObject json = new JSONObject(new String(Hex.decodeHex(hex
					.toCharArray())));
			return new MyProfile(json.getString(MP_EMAIL_ID),
					json.getString(MP_NAME), json.getString(MP_PH_NO),
					json.getString(MP_XMPP_HOST),
					json.getString(MP_XMPP_SERVER), json.getInt(MP_XMPP_PORT),
					json.getString(MP_XMPP_USER_NAME),
					json.getString(MP_XMPP_PASSWORD));

		} catch (JSONException | DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	public void saveToDB(Context context) {

		ChatRoomsDBAdapter db = new ChatRoomsDBAdapter(context);
		db.addMyProfileData(this);
	}

}