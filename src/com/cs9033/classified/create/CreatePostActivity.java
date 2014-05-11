package com.cs9033.classified.create;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.cs9033.classified.R;
import com.cs9033.classified.adapters.ChatRoomsDBAdapter;
import com.cs9033.classified.models.Post;

public class CreatePostActivity extends Activity {
	public static final String TAG = "CreatePostActivity";

	long crID = -1;
	String crName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_post);
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey("CRID")) {
			crID = extras.getLong("CRID");
			crName = extras.getString("CRName");
		} else {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_post, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		try {
			switch (id) {
			case R.id.create_post_menu_save_post:
				Post post = new Post();
				EditText name = (EditText) findViewById(R.id.create_post_name);
				EditText desc = (EditText) findViewById(R.id.create_post_desc);

				post.setMessage(desc.getText().toString());
				post.setTitle(name.getText().toString());
				post.setCR_id(crID);
				Log.d(TAG, post.toJSON().toString());
				ChatRoomsDBAdapter adapter = new ChatRoomsDBAdapter(this);
				adapter.addPostsData(post);

				Toast.makeText(this, "Post Added successfully",
						Toast.LENGTH_SHORT).show();
				break;
			}
		} catch (Exception e) {
			Log.e(TAG, e.getClass().getName(), e);

		} finally {
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

}
