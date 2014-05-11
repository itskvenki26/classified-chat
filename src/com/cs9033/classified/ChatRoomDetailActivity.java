package com.cs9033.classified;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cs9033.classified.create.JoinChatRoomUserActivity;
import com.cs9033.classified.create.CreatePostActivity;

public class ChatRoomDetailActivity extends Activity {
	private static final String TAG = "ChatRoomDetailActivity";
	long crID;
	String crName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_room_detail);

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
						.add(R.id.container, new ShowPostsFragment()).commit();
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
		getMenuInflater().inflate(R.menu.chat_room_detail, menu);
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

		case R.id.chatroom_detail_menu_add_post:
			// Go to Add Posts view
			intent = (new Intent(this, CreatePostActivity.class)).putExtra(
					"CRID", crID).putExtra("CRName", crName);
			break;
		case R.id.chatroom_detail_menu_add_user:
			// Go to Add User view
			intent = (new Intent(this, JoinChatRoomUserActivity.class)).putExtra("CRID",
					crID).putExtra("CRName", crName);
			break;
		case R.id.chatroom_detail_menu_view_users:
			// List all users for current chat room
			intent = (new Intent(this, UsersActivity.class)).putExtra("CRID",
					crID).putExtra("CRName", crName);
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
	public static class ShowPostsFragment extends Fragment {

		public ShowPostsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_chat_room_detail, container, false);
			// Load adapter with posts of current chat room

			return rootView;
		}
	}

}
