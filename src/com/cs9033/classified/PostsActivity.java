package com.cs9033.classified;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.Toast;

import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.models.Comment;

public class PostsActivity extends Activity {

	private static final String TAG = "PostsActivity";
	private static final String SHOW_COMMENTS_FRAGMENT = "showCommentsFragment";
	private static final String ADD_COMMENTS_FRAGMENT = "addCommentsFragment";
	long crID;
	String crName;
	long pID;
	String pName;

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
				chatRoomName = extras.getString("CRName", null);

				Log.d(TAG, "Chatroom Name = " + chatRoomName);
				if (chatRoomName != null) {
					crName = chatRoomName;
					crID = extras.getLong("CRID");
					pID = extras.getLong("PID");
					pName = extras.getString("PName");
					setTitle(pName + ":" + getTitle());
					getFragmentManager()
							.beginTransaction()
							.add(R.id.activity_posts_show_comments_scroll_view,
									new ShowCommentsFragment(),
									SHOW_COMMENTS_FRAGMENT)
							.add(R.id.activity_posts_add_comments_scroll_view,
									new AddCommentsFragment(),
									ADD_COMMENTS_FRAGMENT).commit();
				} else {
					Toast.makeText(this, "Post does not exist",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		}
		Log.d(TAG, "onCreate: return");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.posts, menu);
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
		PostsActivity parent;

		public ShowCommentsFragment() {
		}

		SimpleCursorAdapter sca;
		ListView ll;

		@SuppressWarnings("deprecation")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_show_comments_view, container, false);
			// Load adapter with posts of current chat room

			ChatRoomsDBAdapter cdb = new ChatRoomsDBAdapter(getActivity());
			String[] fro = new String[] { ChatRoomsDBAdapter.C_MSG };
			int to[] = new int[] { android.R.id.text1 };
			Log.d(TAG, "CRID = " + parent.crID + " PID = " + parent.pID);
			Cursor c = cdb.getCommentsCursor(parent.crID, parent.pID);
			sca = new SimpleCursorAdapter(parent,
					android.R.layout.simple_list_item_1, c, fro, to);
			ll = (ListView) rootView.findViewById(R.id.show_comment_list_view);
			ll.setAdapter(sca);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			parent = (PostsActivity) activity;
		}

		@SuppressWarnings("deprecation")
		public void refresh() {
			Log.d(TAG, "refresh");
			ChatRoomsDBAdapter cdb = new ChatRoomsDBAdapter(getActivity());
			String[] fro = new String[] { ChatRoomsDBAdapter.C_MSG };
			int to[] = new int[] { android.R.id.text1 };
			Log.d(TAG, "CRID = " + parent.crID + " PID = " + parent.pID);
			Cursor c = cdb.getCommentsCursor(parent.crID, parent.pID);
			SimpleCursorAdapter sca = new SimpleCursorAdapter(parent,
					android.R.layout.simple_list_item_1, c, fro, to);

			ll.setAdapter(sca);
			ll.setSelection(sca.getCount() - 1);
		}
	}

	public static class AddCommentsFragment extends Fragment implements
			OnClickListener {
		ImageButton button;
		PostsActivity parent;

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
			super.onAttach(activity);
			parent = (PostsActivity) activity;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();

			switch (id) {
			case R.id.add_comment_button:
				// Check the comment and add it to comments table
				ChatRoomsDBAdapter cdb = new ChatRoomsDBAdapter(getActivity());
				Comment c = new Comment();
				EditText t1 = (EditText) getActivity().findViewById(
						R.id.add_comment_edit_text);
				Log.d(TAG, "Edit text");
				String msg = (String) t1.getText().toString();
				Log.d(TAG, "msg" + msg);
				c.setCR_id(parent.crID);
				c.setP_id(parent.pID);
				c.setMessage(msg);
				Log.d(TAG, c.toJSON().toString());
				cdb.addCommentData(c);
				Log.d(TAG, Long.toString(c.getId()));
				parent.refersh();
				break;
			}
		}
	}

	public void refersh() {
		ShowCommentsFragment f = (ShowCommentsFragment) getFragmentManager()
				.findFragmentByTag(SHOW_COMMENTS_FRAGMENT);
		f.refresh();
	}

}
