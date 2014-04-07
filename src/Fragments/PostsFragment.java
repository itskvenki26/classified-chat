package Fragments;

import android.app.Activity;
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
import com.cs9033.classified.adapters.SessionDBAdapter;

public class PostsFragment extends Fragment implements LoaderCallbacks<Cursor> {

	@SuppressWarnings("unused")
	private static final String TAG = "PostsFragment";

	private static final int POST_LOADER = 0;
	private static final int[] POST_ROW_VIEWS = new int[] {
			R.id.post_creator_text_view, R.id.post_message_text_view,
			R.id.post_time_text_view };

	private Activity activity;
	private SimpleCursorAdapter simpleCursorAdapter;

	public PostsFragment() {
		Log.d(TAG, "Constructor");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");

		View rootView = inflater.inflate(R.layout.fragment_posts, container,
				false);
		Log.d(TAG, "onCreateView: inflated view");
		simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.post_row, null, SessionDBAdapter.ALL_POSTS,
				POST_ROW_VIEWS, Adapter.NO_SELECTION);
		Log.d(TAG, "onCreateView: Created SimpleCursorAdapter");
		Activity a = getActivity();
		if (a == null) {
			Log.d(TAG, "a is null");
		}
		ListView LV = (ListView) rootView.findViewById(R.id.post_list_view);

		if (LV == null) {
			Log.d(TAG, "LV is null");
		}
		((ListView) rootView.findViewById(R.id.post_list_view))
				.setAdapter(simpleCursorAdapter);
		Log.d(TAG, "onCreateView: Attached adapter");

		LoaderManager m = getLoaderManager();
		if (m == null) {
			Log.d(TAG, "m is null");

		}

		getLoaderManager().initLoader(POST_LOADER, null, this);

		Log.d(TAG, "onCreateView: Started Loader");
		return rootView;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new PostLoader(activity);
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

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	// /SImple Loader class
	private static class PostLoader extends CursorLoader {

		public PostLoader(Context context) {
			super(context);
		}

		@Override
		public Cursor loadInBackground() {
			return SessionDBAdapter.getAllPostsData(getContext());
		}
	}
}
