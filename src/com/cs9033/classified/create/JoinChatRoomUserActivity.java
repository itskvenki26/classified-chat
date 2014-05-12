package com.cs9033.classified.create;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
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
import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.controllers.MessagePollService;
import com.cs9033.classified.crypto.SecureMessage;
import com.cs9033.classified.models.MyProfile;
import com.google.zxing.integration.android.IntentIntegrator;

public class JoinChatRoomUserActivity extends Activity {
	private static final String TAG = "JoinChatRoomUserActivity";
	public static final String JOIN_CHAT = "JOIN_CHAT";
	public static final String HOST = "H";
	public static final String PORT = "PT";
	public static final String EMAIL = "E";
	public static final String USER_NAME = "UN";
	public static final String SERVER = "S";
	public static final String PHONE_NUMBER = "P";
	public static final String NAME = "N";

	// public static final String KEY1 = "KEY1";
	// public static final String KEY2 = "KEY2";

	long crID;
	String crName;
	String key2;
	String key1;
	String key3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_chat_room_user);

		if (savedInstanceState == null) {

			getFragmentManager().beginTransaction()
					.add(R.id.container, new ShowQRPhase1Fragment()).commit();
		}
		MessagePollService.setServiceAlarm(this, true);

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
		super.onDestroy();
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
			OnClickListener, OnSharedPreferenceChangeListener {
		private static final String TAG = "ShowQRPhase1Fragment";
		JoinChatRoomUserActivity parent;

		public ShowQRPhase1Fragment() {

		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			parent = (JoinChatRoomUserActivity) activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_qr1,
					container, false);
			((Button) rootView
					.findViewById(R.id.show_qr1_initiate_join_cr_button))
					.setOnClickListener(this);
			Log.d(TAG, "Created View");

			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.show_qr1_initiate_join_cr_button:

				JSONObject json = new JSONObject();
				try {
					ChatRoomsDBAdapter dbAdapter = new ChatRoomsDBAdapter(
							getActivity());
					MyProfile myProfile = dbAdapter.getMyProfiledata();
					if (myProfile != null) {
						String xChange1 = SecureMessage.getNewEKey();
						parent.key1 = xChange1;
						json.accumulate(AddUserActivity.PHASE1KEY, xChange1);
						json.accumulate(PHONE_NUMBER, myProfile.getPh_no());
						json.accumulate(HOST, myProfile.getXmpp_host());
						json.accumulate(SERVER, myProfile.getXmpp_server());
						json.accumulate(PORT, myProfile.getXmpp_port());
						json.accumulate(USER_NAME,
								myProfile.getXmpp_user_name());
						json.accumulate(NAME, myProfile.getName());
						json.accumulate(EMAIL, myProfile.getEmail_id());
						xChange1 = new String(Hex.encodeHex(json.toString()
								.getBytes()));
						SharedPreferences sharedPreferences = getActivity()
								.getSharedPreferences(JOIN_CHAT,
										Context.MODE_PRIVATE);

						boolean edit = sharedPreferences.edit()
								.putString(AddUserActivity.PHASE1KEY, xChange1)
								.commit();
						if (edit) {
							IntentIntegrator integrator = new IntentIntegrator(
									this);
							integrator.shareText(xChange1);

							sharedPreferences
									.registerOnSharedPreferenceChangeListener(this);
							Intent intent = new Intent(getActivity(),
									MessagePollService.class);
							intent.setAction(MessagePollService.BLOCK_WAIT);
							getActivity().startService(intent);

							// ((Button) getView().findViewById(
							// R.id.show_qr1_next_button))
							// .setEnabled(true);
						}
					} else {
						Intent i = new Intent(getActivity(),
								UpdateProfileActivity.class);
						getActivity().startActivity(i);
						Toast.makeText(getActivity(),
								"Please update your profile first",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					Log.e(TAG, e.getClass().getName(), e);
				}

				break;
			default:
				break;
			}
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			switch (key) {
			case AddUserActivity.PHASE2KEY:
				String key2 = sharedPreferences.getString(
						AddUserActivity.PHASE2KEY, null);
				parent.key2 = key2;
				Log.d(TAG, "KEY2 is :" + key2);
				sharedPreferences
						.unregisterOnSharedPreferenceChangeListener(this);
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
			super.onAttach(activity);
			parent = (JoinChatRoomUserActivity) activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_qr2,
					container, false);
			((Button) rootView.findViewById(R.id.show_qr2_verify_key_button))
					.setOnClickListener(this);
			// ((Button) rootView.findViewById(R.id.show_qr2_next))
			// .setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.show_qr2_verify_key_button:
				try {
					String xChange2 = parent.key2;
					String xChange3 = parent.key3 = SecureMessage.getNewEKey();
					JSONObject json = new JSONObject();
					json.put(AddUserActivity.PHASE2KEY, xChange2);
					json.put(AddUserActivity.PHASE3KEY, xChange3);
					Log.d(TAG, "Key2 is " + xChange2);
					Log.d(TAG, "Key3 is " + xChange3);
					if (xChange2 != null) {
						IntentIntegrator integrator = new IntentIntegrator(this);
						integrator.shareText(json.toString());
					}
					SharedPreferences s = getActivity().getSharedPreferences(
							JOIN_CHAT, Context.MODE_PRIVATE);

					s.edit().putString(AddUserActivity.PHASE3KEY, xChange3)
							.commit();
				} catch (JSONException e) {
					Log.e(TAG, e.getClass().getName(), e);
				}

				break;

			default:
				break;
			}
		}

		// @Override
		// public void onSharedPreferenceChanged(
		// SharedPreferences sharedPreferences, String key) {
		// switch (key) {
		// case AddUserActivity.PHASE3KEY:
		// SecureMessage s = new SecureMessage(getActivity());
		// String xChange3 = sharedPreferences.getString(
		// AddUserActivity.PHASE3KEY, null);
		// Log.d(TAG, "Message is : " + xChange3);
		// if (xChange3 != null) {
		// try {
		// s.processMessage(xChange3, null, parent.key3);
		// } catch (JSONException e) {
		// Log.e(TAG, e.getClass().getName(), e);
		// }
		// }
		// break;
		// }
		// }
	}

	public static class ShowReceivedChatRoom extends Fragment implements
			OnClickListener {
		public ShowReceivedChatRoom() {
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_show_received_cr, container, false);
			((Button) rootView
					.findViewById(R.id.show_received_chat_room_create_chat_room_button))
					.setOnClickListener(this);
			((Button) rootView
					.findViewById(R.id.show_received_chat_room_refresh_button))
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

							JSONArray postsJSON = json.getJSONArray("Post");
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
