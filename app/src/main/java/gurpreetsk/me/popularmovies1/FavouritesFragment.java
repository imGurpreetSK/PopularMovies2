package gurpreetsk.me.popularmovies1;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import gurpreetsk.me.popularmovies1.adapters.FavouritesAdapter;
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

    public FavouritesFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);
//        TextView tv = (TextView) v.findViewById(R.id.favourite_textview);
//        tv.setText();

        GridView gridView = (GridView) v.findViewById(R.id.favourites_grid_view);

        Uri movieList = ;

//        Cursor cursor = ;
        FavouritesAdapter favouritesAdapter = new FavouritesAdapter(getContext(), cursor, 0);
        gridView.setAdapter(favouritesAdapter);


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
