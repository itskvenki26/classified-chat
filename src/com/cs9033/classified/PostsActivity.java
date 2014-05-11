package com.cs9033.classified;

import com.cs9033.classified.ChatRoomDetailActivity.ShowPostsFragment;
import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

import Models.Comments;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PostsActivity extends Activity {

	private static final String TAG = "PostsActivity";
	long crID;
	String crName;
	long pID;
	String pName;
	ShowCommentsFragment showCommentsFragment;
	AddCommentsFragment addCommentsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate");

		setContentView(R.layout.activity_posts);
		Log.d(TAG, "onCreate: inflated view");

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();

			String chatRoomName = null;
			if (extras != null) {
				chatRoomName = extras.getString("ChatRoomName", null);
			}
			Log.d(TAG, "Chatroom Name = " + chatRoomName);
			if (chatRoomName != null) {
				crName = chatRoomName;
				crID = extras.getLong("CRID");
				pID = extras.getLong("PID");
				pName = extras.getString("PName");
				setTitle(pName + ":" + getTitle());
				showCommentsFragment = new ShowCommentsFragment();
				addCommentsFragment = new AddCommentsFragment();
				getFragmentManager()
						.beginTransaction()
						.add(R.id.activity_posts_show_comments_scroll_view,
								showCommentsFragment)
						.add(R.id.activity_posts_add_comments_scroll_view,
								addCommentsFragment).commit();
			} else {
				Toast.makeText(this, "Chat Room does not exist",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		Log.d(TAG, "onCreate: return");
		// Messager.sendSMS(this, "+13476561714", "Test Message");

	}

	private void refresh() {
		if (showCommentsFragment != null) {
			showCommentsFragment.refresh();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class ShowCommentsFragment extends Fragment {
		PostsActivity par;
		ChatRoomsDBAdapter cdb;

		public ShowCommentsFragment() {
		}

		public void refresh() {
			if (cdb != null) {
				cdb.notifyAll();

			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_show_comments_view, container, false);
			// Load adapter with posts of current chat room

			String[] fro = new String[] { ChatRoomsDBAdapter.C_MSG };

			int to[] = new int[] { android.R.id.text1 };
			Cursor c = cdb.getCommentsCursor(par.crID, par.pID);
			SimpleCursorAdapter sca = new SimpleCursorAdapter(par,
					android.R.layout.simple_list_item_1, c, fro, to);
			ListView ll = (ListView) rootView
					.findViewById(R.id.comment_list_view);
			ll.setAdapter(sca);
			// ll.setOnItemClickListener(this);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			par = (PostsActivity) activity;
			cdb = new ChatRoomsDBAdapter(par);
		}
	}

	public static class AddCommentsFragment extends Fragment implements
			OnClickListener {
		ImageButton button;
		PostsActivity parent;
		ChatRoomsDBAdapter cdb = new ChatRoomsDBAdapter(parent);

		public AddCommentsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_add_comment_view, container, false);
			button = (ImageButton) rootView
					.findViewById(R.id.add_comment_button);
			button.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {

			parent = (PostsActivity) activity;

		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			Comments c = new Comments();

			switch (id) {
			case R.id.add_comment_button:
				// Check the comment and add it to comments table
				EditText t1 = (EditText) v
						.findViewById(R.id.add_comment_edit_text);
				String msg = (String) t1.getText().toString();
				c.setCR_id(parent.crID);
				c.setP_id(parent.pID);
				c.setMessage(msg);
				cdb.addCommentsData(c);
				// startActivity(in);
				parent.refresh();
				break;
			}
		}
	}
}
