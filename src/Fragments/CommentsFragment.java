package Fragments;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import com.cs9033.classified.R;
import com.cs9033.classified.adapters.SessionDBAdapter;

public class CommentsFragment extends Fragment implements
		LoaderCallbacks<Cursor> {
	private SimpleCursorAdapter simpleCursorAdapter;

	public CommentsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_comments_view,
				container, false);
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
			// Get All Comments Data
			// return SessionDBAdapter.getAllPostsData(getContext());
			return null;
		}
	}
}
