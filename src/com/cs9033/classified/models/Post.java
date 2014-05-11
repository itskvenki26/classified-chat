package com.cs9033.classified.models;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

public class Post {
	private static final String TAG = "Post";
	public static final String P_CR_ID = "post_cr_id";
	public static final String P_TITLE = "post_title";
	public static final String P_MSG = "post_message";
	long id = -1l;
	long CR_id;
	String message;
	String title;

	// Empty constructor
	public Post() {

	}

	// constructor
	public Post(int id, long CR_id, String title, String message) {
		this.id = id;
		this.CR_id = CR_id;
		this.title = title;
		this.message = message;

	}

	public Post(long cR_id, String message, String title) {
		super();
		CR_id = cR_id;
		this.message = message;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCR_id() {
		return CR_id;
	}

	public void setCR_id(long cR_id) {
		CR_id = cR_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(P_CR_ID, CR_id);
		json.put(P_TITLE, title);
		json.put(P_MSG, message);
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

	public static Post fromHexString(String hex) {

		try {
			JSONObject json = new JSONObject(new String(Hex.decodeHex(hex
					.toCharArray())));
			return new Post(json.getLong(P_CR_ID), json.getString(P_MSG),
					json.getString(P_TITLE));

		} catch (JSONException | DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	boolean saveToDB(Context context) {

		ChatRoomsDBAdapter db = new ChatRoomsDBAdapter(context);
		db.addPostsData(this);
		return false;

	}
}