package gurpreetsk.me.popularmovies1;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gurpreetsk.me.popularmovies1.R;
import gurpreetsk.me.popularmovies1.models.MovieData;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        getHandles(v);

        Bundle data = getArguments();
//        MovieData data = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        getActivity().setTitle(data.getString("title"));

        vote_average.setText(RATED + data.getString("vote_average"));
        release_date.setText(RELEASE_DATE + data.get("release"));
        overview.setText(data.getString("desc"));
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendEncodedPath(data.getString("poster"))
                .build();
        Picasso.with(getActivity()).load(builder.toString()).fit().into(imageView);

        return v;
    }

    ImageView imageView;
    TextView vote_average, release_date, overview;

    private final String RATED = "Rated: ";
    private final String RELEASE_DATE = "Released: ";


    public void getHandles(View v) {
        imageView = (ImageView) v.findViewById(R.id.detail_image_view);
        vote_average = (TextView) v.findViewById(R.id.detail_vote_average);
        release_date = (TextView) v.findViewById(R.id.detail_release_date);
        overview = (TextView) v.findViewById(R.id.detail_overview);
    }


}
