package com.cs9033.classified.create;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs9033.classified.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddUserActivity extends Activity {
	private static final String TAG = "AddUserActivity";

	public static final String ADD_USER = "ADD_USER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ScanQRPhase1Fragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);
		try {
			if (scanResult != null) {
				String contents = scanResult.getContents();
				String result = new String(
						Hex.decodeHex(contents.toCharArray()));
				SharedPreferences sharedPreferences = getSharedPreferences(
						ADD_USER, Context.MODE_PRIVATE);
				if (sharedPreferences != null) {
					if (sharedPreferences.contains("KEY1") == false) {
						sharedPreferences.edit().putString("KEY1", result)
								.commit();
						gotoPhase2();
					} else {
						sharedPreferences.edit().putString("KEY2", result)
								.commit();
					}
				}
			}
		} catch (DecoderException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
	}

	private void gotoPhase2() {
		SharedPreferences sharedPreferences = getSharedPreferences(ADD_USER,
				Context.MODE_PRIVATE);
		if (sharedPreferences != null && sharedPreferences.contains("KEY1")) {
			String key1 = sharedPreferences.getString("KEY1", null);
			if (key1 != null) {
				try {
					JSONObject json = new JSONObject(key1);
					key1 = json.getString("KEY1");
					String PH = json.getString("PH");
					String host = json.getString("HOST");
					String servet = json.getString("SERVER");
					int port = json.getInt("PORT");

					// use to send messages in Phase 3

					gotoPhase3();
				} catch (JSONException e) {
					Log.e(TAG, e.getClass().getName(), e);
				}

			}
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.container, new ScanQRPhase2Fragment()).commit();
	}

	private void gotoPhase3() {
		// Collect Chatroom, Post, User details and send via XMPP
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ScanQRPhase1Fragment extends Fragment implements
			OnClickListener {

		public ScanQRPhase1Fragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_scan_qr1,
					container, false);

			((Button) rootView).findViewById(
					R.id.scan_qr1_initiate_add_user_button).setOnClickListener(
					this);

			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.scan_qr1_initiate_add_user_button:
				IntentIntegrator integrator = new IntentIntegrator(this);
				integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
				break;

			default:
				break;
			}

		}
	}

	public static class ScanQRPhase2Fragment extends Fragment implements
			OnClickListener {

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_scan_qr1,
					container, false);

			// ((Button) rootView).findViewById(
			// R.id.scan_qr1_initiate_add_user_button).setOnClickListener(
			// this);
			// ((Button) rootView).findViewById(R.id.scan_qr1_next_button)
			// .setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {

		}
	}

}
