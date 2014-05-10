package com.cs9033.classified;

import com.cs9033.classified.create.CreateChatRoomActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ClassifiedChatRoomsActivity extends Activity {

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

		switch (id) {
		case R.id.classified_chat_rooms_menu_create_chatroom:
			// Open Create ChatRoom Activity
			Intent i = new Intent(this, CreateChatRoomActivity.class);
			startActivity(i);
			break;
		case R.id.classified_chat_rooms_menu_join_chatroom:
			// Open Join ChatRoom activity
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

	public static class ShowChatRoomFragment extends Fragment {

		public ShowChatRoomFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_classified_chat_rooms, container, false);

			// Load SQL cursor for all Chatrooms

			// Implement on click for cursor to load Chat Room Detail view

			return rootView;
		}

	}

}
