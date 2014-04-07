package com.cs9033.classified;

import Fragments.PostsFragment;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PostsActivity extends Activity {

	private static final String TAG = "PostsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate");

		setContentView(R.layout.activity_posts);
		Log.d(TAG, "onCreate: inflated view");

		if (savedInstanceState == null) {
			Log.d(TAG, "onCreate: checked instance state");
			getFragmentManager().beginTransaction()
					.add(R.id.activity_posts, new PostsFragment()).commit();
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

}
