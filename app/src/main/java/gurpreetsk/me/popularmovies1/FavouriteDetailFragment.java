package gurpreetsk.me.popularmovies1;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gurpreetsk.me.popularmovies1.models.MovieData;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteDetailFragment extends Fragment {


    ImageView imageView;
    TextView vote_average, release_date, overview;

    private final String RATED = "Rated: ";
    private final String RELEASE_DATE = "Released: ";


    public FavouriteDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        getHandles(v);

        Bundle data = getArguments();
//        MovieData data = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        getActivity().setTitle(data.getString("FavouritesTitle"));

        vote_average.setText(RATED + data.getString("FavouritesAvg"));
        release_date.setText(RELEASE_DATE + data.getString("FavouritesRelease"));
        overview.setText(data.getString("FavouritesDesc"));
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendEncodedPath(data.getString("FavouritesPoster"))
                .build();
        Picasso.with(getActivity()).load(builder.toString()).fit().into(imageView);

        return v;
    }

    public void getHandles(View v) {
        imageView = (ImageView) v.findViewById(R.id.detail_image_view);
        vote_average = (TextView) v.findViewById(R.id.detail_vote_average);
        release_date = (TextView) v.findViewById(R.id.detail_release_date);
        overview = (TextView) v.findViewById(R.id.detail_overview);
    }

}
