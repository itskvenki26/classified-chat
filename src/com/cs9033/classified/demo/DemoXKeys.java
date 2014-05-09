package com.cs9033.classified.demo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs9033.classified.R;
import com.cs9033.classified.adapters.DemoDBAdapter;

public class DemoXKeys extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_xkeys);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.demo_xkeys, menu);
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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_demo_xkeys,
					container, false);

			DemoDBAdapter db = new DemoDBAdapter(getActivity());

			String[] keys = db.getKeys();
			TextView demo_xkeys_current_mac = (TextView) rootView
					.findViewById(R.id.demo_xkeys_current_mac);
			TextView demo_xkeys_current_e = (TextView) rootView
					.findViewById(R.id.demo_xkeys_current_e);

			demo_xkeys_current_mac.setText("Current MAC:" + keys[0]);
			demo_xkeys_current_e.setText("Current ME:" + keys[1]);

			return rootView;
		}
	}

}
