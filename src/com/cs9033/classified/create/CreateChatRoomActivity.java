package com.cs9033.classified.create;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cs9033.classified.R;

public class CreateChatRoomActivity extends Activity {
	public static final String TAG = "CreateChatRoomActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_chat_room);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_chat_room, menu);
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
			case R.id.create_chatroom_menu_save_chatroom:
				Toast.makeText(this, "Chatroom Created successfully",
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
