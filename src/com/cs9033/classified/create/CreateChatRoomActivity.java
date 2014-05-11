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
import com.cs9033.classified.crypto.SecureMessage;
import com.cs9033.classified.models.ChatRoom;

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

				// create_chat_room_name
				// create_chat_room_desc

				ChatRoom chatRoom = new ChatRoom();
				EditText name = (EditText) findViewById(R.id.create_chat_room_name);
				EditText desc = (EditText) findViewById(R.id.create_chat_room_desc);

				ChatRoomsDBAdapter adapter = new ChatRoomsDBAdapter(this);
				chatRoom.setCR_name(name.getText().toString());
				chatRoom.setDescription(desc.getText().toString());
				chatRoom.setCurrent_e(SecureMessage.getNewEKey());
				chatRoom.setCurrent_mac(SecureMessage.getNewMacKey());
				chatRoom.setOld_mac(SecureMessage.getNewMacKey());
				adapter.addChatRoomData(chatRoom);

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
