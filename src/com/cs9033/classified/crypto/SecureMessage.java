package com.cs9033.classified.crypto;

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

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

public class SecureMessage {
	public static final String TAG = "SecureMessage";
	String old_mac_key;
	String current_mac_key;
	String current_e_key;
	String next_mac_key;
	String next_e_key;

	byte[] message;

	@SuppressLint("TrulyRandom")
	public SecureMessage(String message) {
		// SecureRandom sr = new SecureRandom();
		// this.next_mac_key = new byte[20];
		// sr.nextBytes(this.next_mac_key);
		this.message = message.getBytes();
	}

	private void initKeys() {

	}

	@SuppressLint("TrulyRandom")
	public String encrypt() throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, JSONException {
		initKeys();
		byte[] current_e_key_bytes = current_e_key.getBytes();// Replace with
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

		// Get current_e_key
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(current_e_key_bytes,
				"AES"));
		// encrypt json message
		byte[] crypt = cipher.doFinal(message);

		// Now create Mac digest of crypt
		Mac m = Mac.getInstance("HmacSHA1");
		m.init(new SecretKeySpec(current_mac_key.getBytes(), "HmacSHA1"));
		m.update(crypt);
		byte[] mac_of_crypt = m.doFinal();

		JSONObject jsonMessage = new JSONObject();
		jsonMessage.accumulate("crypt", crypt);
		jsonMessage.accumulate("mac_of_crypt", mac_of_crypt);
		jsonMessage.accumulate("old_mac_key", old_mac_key);

		return jsonMessage.toString();
	}

	public static String decrypt(byte[] current_e_key, String encryptedMessage)
			throws Exception {
		JSONObject jsonMessage = new JSONObject(encryptedMessage);
		byte[] crypt = (byte[]) jsonMessage.get("crypt");
		// byte[] mac_of_crypt = (byte[]) jsonMessage.get("mac_of_crypt");

		SecretKeySpec skeySpec = new SecretKeySpec(current_e_key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(crypt);
		return new String(decrypted);
	}

	public static boolean verifyMAC(String current_mac_key,
			String encryptedMessage) {
		try {
			JSONObject jsonMessage = new JSONObject(encryptedMessage);
			byte[] crypt = (byte[]) jsonMessage.get("crypt");
			byte[] mac_of_crypt = (byte[]) jsonMessage.get("mac_of_crypt");
			Mac m = Mac.getInstance("HmacSHA1");
			m.init(new SecretKeySpec(current_mac_key.getBytes(), "HmacSHA1"));
			m.update(crypt);
			byte[] mac_of_crypt_check = m.doFinal();

			return mac_of_crypt_check.equals(mac_of_crypt);
		} catch (NoSuchAlgorithmException | InvalidKeyException | JSONException e) {
			Log.e(TAG, e.toString(), e);
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
			return new String(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "NoSuchAlgorithmException", e);
		}
		return null;
	}

	public static String getNewMacKey() {
		SecureRandom sr = new SecureRandom();
		byte[] keyBytes = new byte[20];
		sr.nextBytes(keyBytes);
		SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA1");
		return new String(key.getEncoded());
	}

}
