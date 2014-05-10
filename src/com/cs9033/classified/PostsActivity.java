package com.cs9033.classified;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class PostsActivity extends Activity {

	private static final String TAG = "PostsActivity";

	ShowCommentsFragment showCommentsFragment;
	AddCommentsFragment addCommentsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate");

		setContentView(R.layout.activity_posts);
		Log.d(TAG, "onCreate: inflated view");

		if (savedInstanceState == null) {
			showCommentsFragment = new ShowCommentsFragment();
			addCommentsFragment = new AddCommentsFragment();
			getFragmentManager()
					.beginTransaction()
					.add(R.id.activity_posts_show_comments_scroll_view,
							showCommentsFragment)
					.add(R.id.activity_posts_add_comments_scroll_view,
							addCommentsFragment).commit();
			Log.d(TAG, "onCreate: added Fragment");
		}
		Log.d(TAG, "onCreate: return");
		// Messager.sendSMS(this, "+13476561714", "Test Message");

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

		public ShowCommentsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_show_comments_view, container, false);
			// Load adapter with posts of current chat room

			return rootView;
		}
	}

	public static class AddCommentsFragment extends Fragment implements
			OnClickListener {
		ImageButton button;

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
			// Load adapter with posts of current chat room

			return rootView;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();

			switch (id) {
			case R.id.add_comment_button:
				// Check the comment and add it to comments table
				break;
			}
		}
	}
}
