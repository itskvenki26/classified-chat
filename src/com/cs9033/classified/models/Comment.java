package com.cs9033.classified.models;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

public class Comment {
	private static final String TAG = "Comment";

	long id = -1l;
	long P_id;
	long CR_id;
	String message;

	public static final String C_P_ID = "comment_p_id";
	public static final String C_CR_ID = "comment_cr_id";
	public static final String C_MSG = "comment_message";

	// Empty constructor
	public Comment() {

	}

	public Comment(long p_id, long cR_id, String message) {
		super();
		P_id = p_id;
		CR_id = cR_id;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getP_id() {
		return P_id;
	}

	public void setP_id(long p_id) {
		P_id = p_id;
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

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put(C_P_ID, P_id);
			json.put(C_CR_ID, CR_id);
			json.put(C_MSG, message);
		} catch (JSONException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return json;
	}

	public String toHexString() {
		return new String(Hex.encodeHex(toJSON().toString().getBytes()));
	}

	public static Comment fromHexString(String hex) {

		try {
			JSONObject json = new JSONObject(new String(Hex.decodeHex(hex
					.toCharArray())));
			return new Comment(json.getLong(C_P_ID), json.getLong(C_CR_ID),
					json.getString(C_MSG));

		} catch (JSONException | DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	boolean saveToDB(Context context) {

		ChatRoomsDBAdapter db = new ChatRoomsDBAdapter(context);
		db.addCommentData(this);
		return false;

	}

}