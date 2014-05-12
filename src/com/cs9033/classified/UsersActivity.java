package com.cs9033.classified;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.create.JoinChatRoomUserActivity;

public class UsersActivity extends Activity {
	private static final String TAG = "UsersActivity";
	long crID;
	String crName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			String chatRoomName = null;
			if (extras != null) {
				chatRoomName = extras.getString("CRName", null);
			}
			Log.d(TAG, "Chatroom Name = " + chatRoomName);
			if (chatRoomName != null) {
				crName = chatRoomName;
				crID = extras.getLong("CRID");
				setTitle(chatRoomName + ":" + getTitle());
				getFragmentManager().beginTransaction()
						.add(R.id.container, new ShowUsersFragment()).commit();
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
		getMenuInflater().inflate(R.menu.users, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Intent intent = null;
		switch (id) {
		case R.id.users_menu_add_user:
			intent = (new Intent(this, JoinChatRoomUserActivity.class))
					.putExtra("CRID", crID).putExtra("CRName", crName);
			break;

		default:
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ShowUsersFragment extends Fragment implements
			OnItemClickListener {

		UsersActivity parent;
		View rootView;

		public ShowUsersFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_users,
					container, false);
			this.rootView = rootView;
			renderView();
			return rootView;
		}

		@Override
		public void onResume() {
			super.onResume();
			renderView();
		}

		void renderView() {
			ListView LV = (ListView) rootView
					.findViewById(R.id.fragment_users_root);
			LV.removeAllViewsInLayout();
			ChatRoomsDBAdapter cdb = new ChatRoomsDBAdapter(parent);
			String[] fro = new String[] { ChatRoomsDBAdapter.U_NAME,
					ChatRoomsDBAdapter.U_PH_NO };

			int to[] = new int[] { android.R.id.text1, android.R.id.text2 };
			Cursor c = cdb.getUsersCursor(parent.crID);
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter sca = new SimpleCursorAdapter(parent,
					android.R.layout.simple_list_item_2, c, fro, to);
			LV.setAdapter(sca);
			LV.setOnItemClickListener(this);
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			parent = (UsersActivity) activity;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

		}
	}

}
