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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cs9033.classified.controllers.MessagePollService;
import com.cs9033.classified.controllers.SendMessage;
import com.cs9033.classified.demo.DemoKeys;
import com.cs9033.classified.demo.DemoPost;
import com.cs9033.classified.demo.DemoXKeys;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		((Button) findViewById(R.id.go_to_posts_view)).setOnClickListener(this);
		((Button) findViewById(R.id.go_to_keys_view)).setOnClickListener(this);
		((Button) findViewById(R.id.go_to_xkeys_view)).setOnClickListener(this);
		// ((Button) findViewById(R.id.go_to_comments_view))
		// .setOnClickListener(this);
		(new Thread(new Runnable() {

			@Override
			public void run() {
				MessagePollService.start();
			}
		})).start();

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

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.go_to_posts_view:
			Log.d(TAG, "go_to_posts_view Button Clicked");
			intent = new Intent(this, DemoPost.class);
			break;
		case R.id.go_to_keys_view:
			Log.d(TAG, "go_to_keys_view Button Clicked");
			intent = new Intent(this, DemoKeys.class);
			// startActivity(intent);
			break;
		case R.id.go_to_xkeys_view:
			Log.d(TAG, "go_to_xkeys_view Button Clicked");
			intent = new Intent(this, DemoXKeys.class);
			break;
		case R.id.main_button1:
			Log.d(TAG, "Clicked");
			EditText txt = ((EditText) findViewById(R.id.main_edit_text));
			String msg = txt.getText().toString();

			intent = new Intent(this, SendMessage.class);
			intent.putExtra("message", msg);
			startService(intent);
			return;
		default:
			return;

		}
		startActivity(intent);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			((Button) rootView.findViewById(R.id.main_button1))
					.setOnClickListener((MainActivity) getActivity());

			return rootView;
		}

	}

}
