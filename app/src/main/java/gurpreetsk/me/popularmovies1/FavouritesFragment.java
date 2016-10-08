package gurpreetsk.me.popularmovies1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gurpreetsk.me.popularmovies1.adapters.FavouritesAdapter;
import gurpreetsk.me.popularmovies1.data.Database;
import gurpreetsk.me.popularmovies1.data.FavouritesTable;

import static gurpreetsk.me.popularmovies1.data.FavouritesTable.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavouritesFragment extends Fragment {

    FavouritesAdapter favouritesAdapter;
    ArrayList<Database> favouritesMovieList = new ArrayList<>();

    public FavouritesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.favouritesRecyclerView);
        favouritesAdapter = new FavouritesAdapter(favouritesMovieList, getActivity());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favouritesAdapter);

        return v;
    }

    private void fetchFavourites() {

        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        List<Database> testRows = FavouritesTable.getRows(cursor, true);
        for (Database element : testRows)
            favouritesMovieList.add(element);

    }

    @Override
    public void onStart() {
        super.onStart();
        fetchFavourites();
    }

}
