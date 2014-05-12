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
import android.widget.TextView;
import android.widget.Toast;

import com.cs9033.classified.R;
import com.cs9033.classified.controllers.SendMessage;
import com.cs9033.classified.crypto.SecureMessage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddUserActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "AddUserActivity";
	public static final String PHASE1KEY = "PHASE1KEY";
	public static final String PHASE2KEY = "PHASE2KEY";

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

	private void gotoPhase2(String result) {

		getFragmentManager().beginTransaction()
				.replace(R.id.container, new ScanQRPhase2Fragment()).commit();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ScanQRPhase1Fragment extends Fragment implements
			OnClickListener {
		private static final String TAG = "ScanQRPhase1Fragment";

		public ScanQRPhase1Fragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_scan_qr1,
					container, false);

			((Button) rootView
					.findViewById(R.id.scan_qr1_initiate_add_user_button))
					.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			Log.d(TAG, "back to Add User Activity");
			IntentResult scanResult = IntentIntegrator.parseActivityResult(
					requestCode, resultCode, data);
			try {
				if (scanResult != null) {
					String contents = scanResult.getContents();
					String result = new String(Hex.decodeHex(contents
							.toCharArray()));
					SharedPreferences sharedPreferences = getActivity()
							.getSharedPreferences(ADD_USER,
									Context.MODE_PRIVATE);
					if (sharedPreferences != null) {
						sharedPreferences.edit().clear()
								.putString(PHASE1KEY, contents).commit();
						Log.d(TAG, "Shared Preferences is not null: "
								+ sharedPreferences.getAll().toString());
						((AddUserActivity) getActivity()).gotoPhase2(result);

					}
				}
			} catch (DecoderException e) {
				Log.e(TAG, e.getClass().getName(), e);
			}
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
		private static final String TAG = "ScanQRPhase2Fragment";

		String key1;
		String name;
		String userName;
		String host;
		String server;
		int port;
		String email;
		String ph_num;
		JSONObject json;
		String key2;

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_scan_qr2,
					container, false);

			SharedPreferences sharedPreferences = getActivity()
					.getSharedPreferences(ADD_USER, Context.MODE_PRIVATE);
			if (sharedPreferences != null) {
				String phase1Key = sharedPreferences.getString(PHASE1KEY, null);
				try {
					json = new JSONObject(new String(Hex.decodeHex(phase1Key
							.toCharArray())));

					Log.d(TAG, json.toString());

					key1 = json.getString(JoinChatRoomUserActivity.KEY1);
					name = json.getString(JoinChatRoomUserActivity.NAME);
					ph_num = json
							.getString(JoinChatRoomUserActivity.PHONE_NUMBER);
					server = json.getString(JoinChatRoomUserActivity.SERVER);

					userName = json
							.getString(JoinChatRoomUserActivity.USER_NAME);
					host = json.getString(JoinChatRoomUserActivity.HOST);
					port = json.getInt(JoinChatRoomUserActivity.PORT);

					((TextView) rootView
							.findViewById(R.id.fragment_scan_qr2_key_text_view))
							.setText(key1);
					((TextView) rootView
							.findViewById(R.id.fragment_scan_qr2_name_text_view))
							.setText(name);
					((TextView) rootView
							.findViewById(R.id.fragment_scan_qr2_ph_num_text_view))
							.setText(ph_num);
					((TextView) rootView
							.findViewById(R.id.fragment_scan_qr2_server_text_view))
							.setText(server);
					((TextView) rootView
							.findViewById(R.id.fragment_scan_qr2_user_name_text_view))
							.setText(json
									.getString(JoinChatRoomUserActivity.USER_NAME));
					((TextView) rootView
							.findViewById(R.id.fragment_scan_qr2_host_text_view))
							.setText(host);
					((TextView) rootView
							.findViewById(R.id.fragment_scan_qr2_port_text_view))
							.setText(Integer.toString(port));
				} catch (JSONException | DecoderException e) {
					Log.e(TAG, e.getClass().getName(), e);
				}
			}
			((Button) rootView.findViewById(R.id.fragment_scan_qr2_next_button))
					.setOnClickListener(this);
			((Button) rootView
					.findViewById(R.id.fragment_scan_qr2_share_secret_button))
					.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();

			switch (id) {
			case R.id.fragment_scan_qr2_share_secret_button:

				Log.d(TAG, "Creating Key2");
				String xChange2 = SecureMessage.getNewEKey();
				Log.d(TAG, "Send XMPP Message here");

				Intent intent = new Intent(getActivity(), SendMessage.class);
				intent.putExtra(PHASE2KEY, xChange2);
				intent.putExtra(JoinChatRoomUserActivity.HOST, host);
				intent.putExtra(JoinChatRoomUserActivity.PORT, port);
				intent.putExtra(JoinChatRoomUserActivity.SERVER, server);
				intent.putExtra(JoinChatRoomUserActivity.USER_NAME, userName);
				intent.setAction(SendMessage.IKE_ACTION);
				getActivity().startService(intent);
				key2 = xChange2;

				break;
			case R.id.fragment_scan_qr2_next_button:
				IntentIntegrator integrator = new IntentIntegrator(this);

				integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);

				break;
			}

		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			Log.d(TAG, "back to Add User Activity");
			IntentResult scanResult = IntentIntegrator.parseActivityResult(
					requestCode, resultCode, data);
			try {
				if (scanResult != null) {
					Log.d(TAG, "Scan Results is not null");
					String contents = scanResult.getContents();
					Log.d(TAG, contents);
					if (contents == key2) {
						Toast.makeText(getActivity(), "Verified",
								Toast.LENGTH_SHORT).show();

					}
				}
			} catch (Exception e) {
				Log.e(TAG, e.getClass().getName(), e);
			}
		}
	}

}
