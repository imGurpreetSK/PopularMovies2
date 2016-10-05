package gurpreetsk.me.popularmovies1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gurpreetsk.me.popularmovies1.data.MoviesContract;
import gurpreetsk.me.popularmovies1.data.TableStructure;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String[] PROJECTION_COLUMNS = {
            TableStructure.COLUMN_ID,
            TableStructure.COLUMN_TITLE
    };
    static final int COLUMN_ID = 0;
    static final int COLUMN_TITLE = 1;
    static final int COLUMN_DESC = 2;
    static final int COLUMN_POSTER = 3;
    static final int COLUMN_RELEASE_DATE = 4;
    static final int COLUMN_VOTE_AVERAGE = 5;
    static final int COLUMN_TRAILER = 6;
    static final int COLUMN_REVIEWS = 7;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
