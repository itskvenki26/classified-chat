package com.cs9033.classified.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.models.ChatRoom;
import com.cs9033.classified.models.Comment;
import com.cs9033.classified.models.MyProfile;
import com.cs9033.classified.models.Post;
import com.cs9033.classified.models.User;

public class SecureMessage {
	public static final String TAG = "SecureMessage";

	public static final String CHAT_ROOM = "CHAT_ROOM";
	public static final String POST = "POST";
	public static final String COMMENT = "COMMENT";
	public static final String POST_ARRAY = "POST";
	public static final String USER_ARRAY = "USER_ARRAY";
	public static final String USER = "USER";
	public static final String TYPE = "TYPE";
	public static final String VALUE = "VALUE";
	public static final String NEXT_MAC_KEY = "NEXT_MAC_KEY";
	public static final String CURRENT_MAC_KEY = "CURRENT_MAC_KEY";
	public static final String OLD_MAC_KEY = "OLD_MAC_KEY";
	public static final String NEXT_E_KEY = "NEXT_E_KEY";
	public static final String CRYPT = "CRYPT";
	public static final String MAC_OF_CRYPT = "MAC_OF_CRYPT";
	public static final String FROM = "FROM";

	Context context;
	ChatRoomsDBAdapter adapter;
	ChatRoom chatRoom;
	String old_mac_key;
	String current_mac_key;
	String current_e_key;
	String next_mac_key;
	String next_e_key;
	MyProfile myProfile;

	String messageString;
	String message;

	public SecureMessage(Context context) {
		super();
		this.context = context;
		adapter = new ChatRoomsDBAdapter(context);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void init(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		old_mac_key = chatRoom.getOld_mac();
		current_e_key = chatRoom.getCurrent_e();
		current_mac_key = chatRoom.getCurrent_mac();
		next_mac_key = getNewMacKey();
		next_e_key = getNewEKey();
	}

	public String getAddChatRoomMessage(String Key) {
		long crid = chatRoom.getId();
		User[] users = adapter.getUsersData(crid);
		Post[] posts = adapter.getPostData(crid);

		try {
			JSONObject json = new JSONObject();

			json.put(TYPE, CHAT_ROOM);

			JSONObject jsonChatRoom = chatRoom.toJSON();

			JSONArray jsonUsers = new JSONArray();
			JSONArray jsonPosts = new JSONArray();

			jsonUsers.put(myProfile.toUserJSON());
			for (User u : users) {
				jsonUsers.put(u.toJSON());
			}
			for (Post p : posts) {
				jsonPosts.put(p.toJSON());
			}

			jsonChatRoom.put(USER_ARRAY, jsonUsers).put(POST_ARRAY, jsonPosts);

			json.put(VALUE, jsonChatRoom);
			// .put(FROM, myProfile.getPh_no());
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Key.getBytes(),
					"AES"));
			// encrypt json message
			return new String(Hex.encodeHex(cipher.doFinal(json.toString()
					.getBytes())));
		} catch (JSONException | IllegalBlockSizeException
				| BadPaddingException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	public String getAddPostMessage(Post post) {

		try {
			JSONObject json = new JSONObject();

			json.put(TYPE, POST).put(CHAT_ROOM, chatRoom.getCR_name())
					.put(VALUE, post.toJSON());
			// .put(FROM, myProfile.getPh_no());

			return encrypt(json);
		} catch (JSONException | IllegalBlockSizeException
				| BadPaddingException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	public String getAddUserMessage(User user) {
		try {
			JSONObject json = new JSONObject();

			json.put(TYPE, USER).put(CHAT_ROOM, chatRoom.getCR_name())
					.put(VALUE, user.toJSON());
			// .put(FROM, myProfile.getPh_no());

			return encrypt(json);
		} catch (JSONException | IllegalBlockSizeException
				| BadPaddingException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}

		return null;
	}

	public String getAddCommentMessage(Post post, Comment comment) {

		try {
			JSONObject json = new JSONObject();

			json.put(TYPE, COMMENT).put(CHAT_ROOM, chatRoom.getCR_name())
					.put(POST, post.getTitle()).put(VALUE, post.toJSON());
			// .put(FROM, myProfile.getPh_no());

			return encrypt(json);
		} catch (JSONException | IllegalBlockSizeException
				| BadPaddingException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
		return null;
	}

	public static void processMessage(String message,User user) {
		// verify if message is right
		try {
			JSONObject jsonCrypt = new JSONObject(message);
			Log.d(TAG, jsonCrypt.toString());
			if (jsonCrypt.has(CRYPT) && jsonCrypt.has(MAC_OF_CRYPT)
					&& jsonCrypt.has(OLD_MAC_KEY)) {
				String crypt = jsonCrypt.getString(CRYPT);
				String mac_of_crypt = jsonCrypt.getString(MAC_OF_CRYPT);
				
//				verifyMAC(user.get, encryptedMessage)
				
				
			}

		} catch (JSONException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
	}

	@SuppressLint("TrulyRandom")
	public String encrypt(JSONObject jsonCrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, JSONException {
		// initKeys();
		byte[] current_e_key_bytes;
		Log.d(TAG, "current_e_key:" + current_e_key);
		try {
			current_e_key_bytes = Hex.decodeHex(current_e_key.toCharArray());

			next_mac_key = getNewMacKey();
			next_e_key = getNewEKey();

			jsonCrypt.accumulate("next_mac_key", next_mac_key);
			jsonCrypt.accumulate("next_e_key", next_e_key);
			Log.d(TAG, "Sending Message:" + jsonCrypt.toString());
			// Get current_e_key
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(
					current_e_key_bytes, "AES"));
			// encrypt json message
			byte[] crypt = cipher.doFinal(jsonCrypt.toString().getBytes());
			String crypt_string = new String(Hex.encodeHex(crypt));
			// new String(crypt, "UTF8");

			// Now create Mac digest of crypt
			Mac m = Mac.getInstance("HmacSHA1");
			m.init(new SecretKeySpec(Hex.decodeHex(current_mac_key
					.toCharArray()), "HmacSHA1"));
			m.update(crypt);
			byte[] mac_of_crypt = m.doFinal();
			String mac_of_crypt_string = new String(Hex.encodeHex(mac_of_crypt));
			// new String(mac_of_crypt, "UTF8");

			JSONObject jsonMessage = new JSONObject();
			jsonMessage.accumulate(CRYPT, crypt_string);
			jsonMessage.accumulate(MAC_OF_CRYPT, mac_of_crypt_string);
			jsonMessage.accumulate(OLD_MAC_KEY, old_mac_key);

			return jsonMessage.toString();
		} catch (DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
		return null;
	}

	public static String decrypt(String current_e_key, String encryptedMessage) {
		Log.d(TAG, "Inside Decrypt");
		JSONObject jsonMessage;
		try {
			jsonMessage = new JSONObject(encryptedMessage);
			Log.d(TAG, "Created jsonMessage: " + jsonMessage.toString());
			byte[] crypt = Hex.decodeHex(jsonMessage.getString("crypt")
					.toCharArray());
			Log.d(TAG, "Crypt is " + jsonMessage.getString("crypt"));
			Log.d(TAG, "converted to bytes");
			// byte[] mac_of_crypt = (byte[]) jsonMessage.get("mac_of_crypt");

			byte[] current_e_key_bytes = Hex.decodeHex(current_e_key
					.toCharArray());
			SecretKeySpec skeySpec = new SecretKeySpec(current_e_key_bytes,
					"AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			Log.d(TAG, "Cipher ready");
			byte[] decrypted = cipher.doFinal(crypt);
			Log.d(TAG, "Decrypted success");
			return new String(decrypted);
		} catch (JSONException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException
				| DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
		return null;
	}

	public static boolean verifyMAC(String current_mac_key,
			String encryptedMessage) {
		try {
			JSONObject jsonMessage = new JSONObject(encryptedMessage);
			byte[] crypt = (byte[]) jsonMessage.getString("crypt").getBytes();
			byte[] mac_of_crypt = (byte[]) jsonMessage.get("mac_of_crypt");
			Mac m = Mac.getInstance("HmacSHA1");
			m.init(new SecretKeySpec(current_mac_key.getBytes("UTF8"),
					"HmacSHA1"));
			m.update(crypt);
			byte[] mac_of_crypt_check = m.doFinal();

			return mac_of_crypt_check.equals(mac_of_crypt);
		} catch (NoSuchAlgorithmException | InvalidKeyException | JSONException
				| UnsupportedEncodingException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
		return false;

	}

	@SuppressLint("TrulyRandom")
	public static String getNewEKey() {
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKey key = kgen.generateKey();
			return new String(Hex.encodeHex(key.getEncoded()));
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
		return null;
	}

	public static String getNewMacKey() {
		SecureRandom sr = new SecureRandom();
		byte[] keyBytes = new byte[20];
		sr.nextBytes(keyBytes);
		SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA1");
		return new String(Hex.encodeHex(key.getEncoded()));
	}

}
