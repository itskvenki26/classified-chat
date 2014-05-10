package com.cs9033.classified.create;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

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
import android.widget.Toast;

import com.cs9033.classified.R;
import com.cs9033.classified.crypto.SecureMessage;
import com.google.zxing.integration.android.IntentIntegrator;

public class AddUserActivity extends Activity implements AddUserInterface {
	private static final String TAG = "AddUserActivity";
	long crID;
	String crName;
	private String key2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

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

	/**
	 * @author Prasad TV
	 * 
	 *         Show initial Secret with contact details
	 */
	public static class ShowQRPhase1Fragment extends Fragment implements
			OnClickListener {
		private static final String TAG = "ShowQRPhase1Fragment";
		AddUserInterface parent;

		public ShowQRPhase1Fragment() {

		}

		@Override
		public void onAttach(Activity activity) {
			parent = (AddUserInterface) activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_qr1,
					container, false);
			Button button = (Button) rootView
					.findViewById(R.id.initiate_add_user);
			button.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.initiate_add_user:
				String key1 = SecureMessage.getNewEKey();
				SharedPreferences sharedPreferences = getActivity()
						.getSharedPreferences("AddUser", Context.MODE_PRIVATE);
				boolean edit = sharedPreferences.edit().putString("KEY1", key1)
						.commit();
				if (edit) {
					IntentIntegrator integrator = new IntentIntegrator(this);
					integrator.shareText(key1);
				}
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

		AddUserInterface parent;

		public ShowQRPhase2Fragment() {

		}

		@Override
		public void onAttach(Activity activity) {
			parent = (AddUserInterface) activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_qr2,
					container, false);
			Button button = (Button) rootView
					.findViewById(R.id.verify_add_user_key);
			button.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.initiate_add_user:
				SharedPreferences sharedPreferences = getActivity()
						.getSharedPreferences("AddUser", Context.MODE_PRIVATE);

				String key1 = sharedPreferences.getString("KEY1", null);
				if (key1 != null) {

					try {
						byte[] key1bytes = Hex.decodeHex(key1.toCharArray());
						String key2 = parent.getKey2();
						byte[] key2bytes = Hex.decodeHex(key2.toCharArray());

					} catch (DecoderException e) {
						Log.e(TAG, e.getClass().getName(), e);
					}

					IntentIntegrator integrator = new IntentIntegrator(this);
					integrator.shareText(key1);
				}
				break;

			default:
				break;
			}
		}

	}

	public static class ShowReceivedChatRoom extends Fragment {
		public ShowReceivedChatRoom() {
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_show_received_CR, container, false);
			return rootView;
		}
	}

	@Override
	public String getKey2() {
		return this.key2;
	}

}
