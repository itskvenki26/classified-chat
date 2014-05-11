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
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class SecureMessage {
	public static final String TAG = "SecureMessage";

	Context context;
	String old_mac_key;
	String current_mac_key;
	String current_e_key;
	String next_mac_key;
	String next_e_key;

	String messageString;
	String message;

	@SuppressLint("TrulyRandom")
	public SecureMessage(Context context, String message) {
		// SecureRandom sr = new SecureRandom();
		// this.next_mac_key = new byte[20];
		// sr.nextBytes(this.next_mac_key);
		// this.context = context;
		messageString = message;
		this.message = message;
		initKeys();
	}

	private void initKeys() {
		next_mac_key = getNewMacKey();
		next_e_key = getNewEKey();
		// DemoDBAdapter db = new DemoDBAdapter(context);
		// String[] keys = db.getKeys();

		// current_mac_key = keys[0];
		// current_e_key = keys[1];
		//
		// old_mac_key = keys[2];

	}

	public String getOld_mac_key() {
		return old_mac_key;
	}

	public String getCurrent_mac_key() {
		return current_mac_key;
	}

	public String getCurrent_e_key() {
		return current_e_key;
	}

	public String getNext_mac_key() {
		return next_mac_key;
	}

	public String getNext_e_key() {
		return next_e_key;
	}

	public String getMessage() {
		return message;
	}

	@SuppressLint("TrulyRandom")
	public String encrypt() throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, JSONException {
		// initKeys();
		byte[] current_e_key_bytes;
		Log.d(TAG, "current_e_key:" + current_e_key);
		try {
			current_e_key_bytes = Hex.decodeHex(current_e_key.toCharArray());

			// Replace with
			// current user
			// key

			/*
			 * Prepare JSON { crypt: AES(current_e_key,{message,
			 * next_mac_key,next_e_key}), mac_code:mac(current_mac_key,crypt) }
			 */

			JSONObject jsonCrypt = new JSONObject();

			jsonCrypt.accumulate("message", new String(message));
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
			jsonMessage.accumulate("crypt", crypt_string);
			jsonMessage.accumulate("mac_of_crypt", mac_of_crypt_string);
			jsonMessage.accumulate("old_mac_key", old_mac_key);

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
