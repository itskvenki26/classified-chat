package com.cs9033.classified.create;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cs9033.classified.R;
import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.models.MyProfile;

public class UpdateProfileActivity extends Activity {
	private static final String TAG = "UpdateProfileActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_profile);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new UpdateProfileFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.update_profile_menu_save:

			String email_id = ((EditText) findViewById(R.id.update_profile_email_edit_text))
					.getText().toString();
			String name = ((EditText) findViewById(R.id.update_profile_name_edit_text))
					.getText().toString();
			String ph_no = ((EditText) findViewById(R.id.update_profile_ph_num_edit_text))
					.getText().toString();
			String xmpp_host = ((EditText) findViewById(R.id.update_profile_host_edit_text))
					.getText().toString();
			String xmpp_server = ((EditText) findViewById(R.id.update_profile_server_edit_text))
					.getText().toString();
			Log.d(TAG,
					((EditText) findViewById(R.id.update_profile_port_edit_text))
							.getText().toString());
			int xmpp_port = Integer
					.parseInt(((EditText) findViewById(R.id.update_profile_port_edit_text))
							.getText().toString());
			String xmpp_user_name = ((EditText) findViewById(R.id.update_profile_user_name_edit_text))
					.getText().toString();
			String xmpp_password = ((EditText) findViewById(R.id.update_profile_password_edit_text))
					.getText().toString();

			MyProfile myProfile = new MyProfile(email_id, name, ph_no,
					xmpp_host, xmpp_server, xmpp_port, xmpp_user_name,
					xmpp_password);
			myProfile.saveToDB(this);
			finish();
			Toast.makeText(this, "Saved Profile", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class UpdateProfileFragment extends Fragment {

		public UpdateProfileFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_update_profile,
					container, false);

			ChatRoomsDBAdapter adapter = new ChatRoomsDBAdapter(getActivity());

			MyProfile profile = adapter.getMyProfiledata();

			if (profile != null) {
				Log.d(TAG, "Checking update_profile_email_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_email_edit_text))
						.setText(profile.getEmail_id());
				Log.d(TAG, "Checking update_profile_name_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_name_edit_text))
						.setText(profile.getName());
				Log.d(TAG, "Checking update_profile_ph_num_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_ph_num_edit_text))
						.setText(profile.getPh_no());
				Log.d(TAG, "Checking update_profile_host_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_host_edit_text))
						.setText(profile.getXmpp_host());
				Log.d(TAG, "Checking update_profile_server_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_server_edit_text))
						.setText(profile.getXmpp_server());
				Log.d(TAG, "Checking update_profile_port_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_port_edit_text))
						.setText(Integer.toString(profile.getXmpp_port()));
				Log.d(TAG, "Checking update_profile_user_name_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_user_name_edit_text))
						.setText(profile.getXmpp_user_name());
				Log.d(TAG, "Checking update_profile_password_edit_text");
				((EditText) rootView
						.findViewById(R.id.update_profile_password_edit_text))
						.setText(profile.getXmpp_password());
			}

			return rootView;
		}
	}

}
