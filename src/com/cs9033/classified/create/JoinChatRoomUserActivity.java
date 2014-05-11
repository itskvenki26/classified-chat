package com.cs9033.classified.create;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs9033.classified.R;
import com.cs9033.classified.crypto.SecureMessage;
import com.google.zxing.integration.android.IntentIntegrator;

public class JoinChatRoomUserActivity extends Activity {
	private static final String TAG = "JoinChatRoomUserActivity";
	public static final String JOIN_CHAT = "JOIN_CHAT";
	long crID;
	String crName;
	private String key2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_chat_room_user);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();

			String chatRoomName = "Test";
			if (extras != null) {
				chatRoomName = extras.getString("ChatRoomName", null);
			}
			Log.d(TAG, "Chatroom Name = " + chatRoomName);
			if (chatRoomName != null) {
				crName = chatRoomName;
				crID = extras.getLong("CRID");
				setTitle(chatRoomName + ":" + getTitle());
				getFragmentManager().beginTransaction()
						.add(R.id.container, new ShowQRPhase1Fragment())
						.commit();
			} else {
				Toast.makeText(this, "Chat Room does not exist",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join_chat_room_user, menu);
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

	public String getKey2() {
		return this.key2;
	}

	public void gotoPhase3() {
		getFragmentManager().beginTransaction()
				.replace(R.id.container, new ShowReceivedChatRoom()).commit();
	}

	public void gotoPhase2() {
		getFragmentManager().beginTransaction()
				.replace(R.id.container, new ShowQRPhase2Fragment()).commit();
	}

	@Override
	protected void onDestroy() {
		SharedPreferences sharedPreferences = getSharedPreferences(JOIN_CHAT,
				Context.MODE_PRIVATE);
		if (sharedPreferences != null) {
			sharedPreferences.edit().clear().commit();
		}

	}

	/**
	 * @author Prasad TV
	 * 
	 *         Show initial Secret with contact details
	 */
	public static class ShowQRPhase1Fragment extends Fragment implements
			OnClickListener {
		private static final String TAG = "ShowQRPhase1Fragment";
		JoinChatRoomUserActivity parent;

		public ShowQRPhase1Fragment() {

		}

		@Override
		public void onAttach(Activity activity) {
			parent = (JoinChatRoomUserActivity) activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_qr1,
					container, false);
			((Button) rootView).findViewById(
					R.id.show_qr1_initiate_join_cr_button).setOnClickListener(
					this);
			((Button) rootView).findViewById(R.id.show_qr1_next_button)
					.setOnClickListener(this);
			Log.d(TAG, "Created View");

			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.show_qr1_initiate_join_cr_button:
				String key1 = SecureMessage.getNewEKey();
				JSONObject json = new JSONObject();
				try {
					json.accumulate("KEY1", key1);
					json.accumulate("PH", "<Phone Number>");
					json.accumulate("HOST", "host");
					json.accumulate("SERVER", "server");
					json.accumulate("PORT", "port");
					key1 = new String(Hex.encodeHex(json.toString().getBytes()));
				} catch (JSONException e) {
					Log.e(TAG, e.getClass().getName(), e);
				}

				SharedPreferences sharedPreferences = getActivity()
						.getSharedPreferences(JOIN_CHAT, Context.MODE_PRIVATE);
				boolean edit = sharedPreferences.edit().putString("KEY1", key1)
						.commit();
				if (edit) {
					IntentIntegrator integrator = new IntentIntegrator(this);
					integrator.shareText(key1);
					((Button) getView().findViewById(R.id.show_qr1_next_button))
							.setEnabled(true);
				}
				break;
			case R.id.show_qr1_next_button:
				parent.gotoPhase2();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 
	 * @author Prasad TV
	 * 
	 *         Show Second Secret
	 * 
	 */
	public static class ShowQRPhase2Fragment extends Fragment implements
			OnClickListener {
		private static final String TAG = "ShowQRPhase2Fragment";

		JoinChatRoomUserActivity parent;

		public ShowQRPhase2Fragment() {

		}

		@Override
		public void onAttach(Activity activity) {
			parent = (JoinChatRoomUserActivity) activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_qr2,
					container, false);
			((Button) rootView).findViewById(R.id.show_qr2_verify_key_button)
					.setOnClickListener(this);
			((Button) rootView).findViewById(R.id.show_qr2_next)
					.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.show_qr2_verify_key_button:
				try {
					SharedPreferences sharedPreferences = getActivity()
							.getSharedPreferences(JOIN_CHAT,
									Context.MODE_PRIVATE);
					String key1 = sharedPreferences.getString("KEY1", null);
					String key2 = sharedPreferences.getString("KEY2", null);
					if (key1 != null && key2 != null) {
						byte[] key1bytes = Hex.decodeHex(key1.toCharArray());
						// String key2 = parent.getKey2();
						byte[] key2bytes = Hex.decodeHex(key2.toCharArray());
						SecretKeySpec skeySpec = new SecretKeySpec(key1bytes,
								"AES");

						Cipher cipher = Cipher.getInstance("AES");
						cipher.init(Cipher.DECRYPT_MODE, skeySpec);
						byte[] decryptedbytes = cipher.doFinal(key2bytes);
						String decrypted = new String(decryptedbytes);

						IntentIntegrator integrator = new IntentIntegrator(this);
						integrator.shareText(decrypted);
						((Button) getView().findViewById(R.id.show_qr2_next))
								.setEnabled(true);
					}
				} catch (DecoderException | InvalidKeyException
						| NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException e) {
					Log.e(TAG, e.getClass().getName(), e);
				}
				break;

			case R.id.show_qr2_next:
				parent.gotoPhase3();
				break;

			default:
				break;
			}
		}

	}

	public static class ShowReceivedChatRoom extends Fragment implements
			OnClickListener {
		public ShowReceivedChatRoom() {
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_show_received_cr, container, false);
			((Button) rootView).findViewById(
					R.id.show_received_chat_room_create_chat_room_button)
					.setOnClickListener(this);
			((Button) rootView).findViewById(
					R.id.show_received_chat_room_refresh_button)
					.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.show_received_chat_room_refresh_button:
				SharedPreferences sharedPreferences = getActivity()
						.getSharedPreferences(JOIN_CHAT, Context.MODE_PRIVATE);
				if (sharedPreferences != null) {
					String chatRoom = sharedPreferences.getString(
							"ChatRoomJson", null);
					if (chatRoom != null) {
						try {
							View view = getView();
							JSONObject json = new JSONObject(chatRoom);
							((TextView) view
									.findViewById(R.id.show_received_chat_room_name))
									.setText(json.getString("CRName"));
							LinearLayout postsLinearLayout = (LinearLayout) view
									.findViewById(R.id.show_received_chat_room_posts);
							LinearLayout usersLinearLayout = (LinearLayout) view
									.findViewById(R.id.show_received_chat_room_users);

							JSONArray postsJSON = json.getJSONArray("Posts");
							JSONArray usersJSON = json.getJSONArray("Users");

							int postsLen = postsJSON.length();

							for (int i = 0; i < postsLen; i++) {
								JSONObject json2 = postsJSON.getJSONObject(i);
								TextView child = new TextView(null);
								child.setText(json2.getString("PTitle"));
								postsLinearLayout.addView(child);
							}
							int usersLen = postsJSON.length();

							for (int i = 0; i < usersLen; i++) {
								JSONObject json2 = usersJSON.getJSONObject(i);
								TextView child = new TextView(null);
								child.setText(json2.getString("UName"));
								usersLinearLayout.addView(child);
							}
							((Button) getView()
									.findViewById(
											R.id.show_received_chat_room_create_chat_room_button))
									.setEnabled(true);

						} catch (JSONException e) {
							Log.e(TAG, e.getClass().getName(), e);
						}
					}
				}
				break;

			case R.id.show_received_chat_room_create_chat_room_button:
				// Create Chat room here
				break;

			default:
				break;
			}
		}
	}

}
