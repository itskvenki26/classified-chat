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
import android.widget.TextView;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.create.CreateChatRoomActivity;
import com.cs9033.classified.create.JoinChatRoomUserActivity;
import com.cs9033.classified.create.UpdateProfileActivity;

public class ClassifiedChatRoomsActivity extends Activity {

	private static final String TAG = "ClassifiedChatRoomsActivity";
	ShowChatRoomFragment showChatRoomFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classified_chat_rooms);

		showChatRoomFragment = new ShowChatRoomFragment();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, showChatRoomFragment).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.classified_chat_rooms, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Intent i = null;
		switch (id) {
		case R.id.classified_chat_rooms_menu_create_chatroom:
			// Open Create ChatRoom Activity
			i = new Intent(this, CreateChatRoomActivity.class);
			startActivity(i);
			break;
		case R.id.classified_chat_rooms_menu_join_chatroom:
			// Open Join ChatRoom activity
			i = new Intent(this, JoinChatRoomUserActivity.class);
			startActivity(i);
			break;
		case R.id.classified_chat_rooms_menu_my_profile:
			i = new Intent(this, UpdateProfileActivity.class);
			startActivity(i);

			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

	public static class ShowChatRoomFragment extends Fragment implements
			OnItemClickListener {
		ClassifiedChatRoomsActivity parent;

		public ShowChatRoomFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_classified_chat_rooms, container, false);

			// Load SQL cursor for all Chatrooms

			// Implement on click for cursor to load Chat Room Detail view

			ChatRoomsDBAdapter cdb = new ChatRoomsDBAdapter(parent);
			String[] fro = new String[] { ChatRoomsDBAdapter.CR_NAME,
					ChatRoomsDBAdapter.CR_DESCRIPTION };

			int to[] = new int[] { android.R.id.text1, android.R.id.text2 };
			Cursor c = cdb.getChatRoomsCursor();
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter sca = new SimpleCursorAdapter(parent,
					android.R.layout.simple_list_item_2, c, fro, to);
			ListView ll = (ListView) rootView
					.findViewById(R.id.classified_chat_room_list_view);
			ll.setAdapter(sca);
			ll.setOnItemClickListener(this);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			parent = (ClassifiedChatRoomsActivity) activity;

		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			long crid = id;
			Log.d(TAG, "CR: crid = " + crid);

			TextView t1 = (TextView) view.findViewById(android.R.id.text1);
			String crName = (String) t1.getText().toString();
			Intent in = new Intent(getActivity(), ChatRoomDetailActivity.class);
			in.putExtra("CRID", crid);
			in.putExtra("CRName", crName);
			startActivity(in);

		}

	}

}
