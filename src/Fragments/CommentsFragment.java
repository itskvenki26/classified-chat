package Fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.cs9033.classified.R;
import com.cs9033.classified.adapters.ChatRoomsDBAdapter;

public class CommentsFragment extends Fragment implements
		LoaderCallbacks<Cursor> {
	private static final String TAG = "CommentsFragment";
	private static final int COMMENT_LOADER = 0;
	private SimpleCursorAdapter simpleCursorAdapter;

	private static final int[] COMMENT_ROW_VIEWS = new int[] {
			R.id.comment_creator_text_view, R.id.comment_message_text_view,
			R.id.comment_time_text_view };

	public CommentsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_comments_view,
				container, false);

		Log.d(TAG, "onCreateView: inflated view");
		simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.comment_row, null, ChatRoomsDBAdapter.ALL_COMMENTS,
				COMMENT_ROW_VIEWS, Adapter.NO_SELECTION);
		Log.d(TAG, "onCreateView: Created SimpleCursorAdapter");
		// Activity a = getActivity();
		// if (a == null) {
		// Log.d(TAG, "a is null");
		// }

		ListView LV = (ListView) rootView.findViewById(R.id.comment_list_view);

		if (LV == null) {
			Log.d(TAG, "LV is null");
		}
		((ListView) rootView.findViewById(R.id.comment_list_view))
				.setAdapter(simpleCursorAdapter);
		Log.d(TAG, "onCreateView: Attached adapter");

		LoaderManager m = getLoaderManager();
		if (m == null) {
			Log.d(TAG, "m is null");

		}

		getLoaderManager().initLoader(COMMENT_LOADER, null, this);

		Log.d(TAG, "onCreateView: Started Loader");

		return rootView;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CommentsLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		simpleCursorAdapter.swapCursor(data);
		simpleCursorAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		simpleCursorAdapter.swapCursor(null);

	}

	private static class CommentsLoader extends CursorLoader {

		public CommentsLoader(Context context) {
			super(context);
		}

		@Override
		public Cursor loadInBackground() {
			ChatRoomsDBAdapter sessionDBAdapter = new ChatRoomsDBAdapter(
					getContext());
			return sessionDBAdapter.getAllCommentsData();
		}
	}
}
