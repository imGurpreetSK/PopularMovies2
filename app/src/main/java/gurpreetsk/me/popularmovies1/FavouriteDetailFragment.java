package gurpreetsk.me.popularmovies1;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gurpreetsk.me.popularmovies1.data.FavouritesTable;
import gurpreetsk.me.popularmovies1.data.TableStructure;

import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteDetailFragment extends Fragment {


    ImageView imageView;
    TextView vote_average, release_date, overview;
    LikeButton likeButton;

    ArrayList<String> reviews = new ArrayList<>();
    ArrayList<String> trailers = new ArrayList<>();

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

        final Bundle data = getArguments();
//        MovieData data = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        getActivity().setTitle(data.getString("FavouritesTitle"));

        vote_average.setText(RATED + data.getString("FavouritesAvg"));
        release_date.setText(RELEASE_DATE + data.getString("FavouritesRelease"));
        overview.setText(data.getString("FavouritesDesc"));
        likeButton.setLiked(true);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                getActivity().getContentResolver().delete(FavouritesTable.CONTENT_URI, TableStructure.COLUMN_ID + " = ?", new String[]{"" + data.getString("FavouritesID")});
            }
        });
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendEncodedPath(data.getString("FavouritesPoster"))
                .build();
        Picasso.with(getActivity()).load(builder.toString()).fit().into(imageView);

        fetchReviews(data.getString("FavouritesID"));
        fetchTrailers(data.getString("FavouritesID"));

        return v;
    }

    private void fetchReviews(String id) {

        final Uri reviewUri = Uri.parse("http://api.themoviedb.org/3/movie/" + id + "/reviews?").buildUpon()
                .appendQueryParameter(getString(R.string.API_KEY_ATTR), getString(R.string.MOVIEDB_API_KEY))
                .build();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = reviewUri.toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONArray("results").getJSONObject(i);
                                reviews.add(obj.getString("content"));
                            }
//                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });

        queue.add(request);

    }

    private void fetchTrailers(String id) {

        final Uri reviewUri = Uri.parse("http://api.themoviedb.org/3/movie/" + id + "/videos?").buildUpon()
                .appendQueryParameter(getString(R.string.API_KEY_ATTR), getString(R.string.MOVIEDB_API_KEY))
                .build();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = reviewUri.toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONArray("results").getJSONObject(i);
                                trailers.add(obj.getString("key"));
                            }
//                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });

        queue.add(request);

    }

    public void getHandles(View v) {
        imageView = (ImageView) v.findViewById(R.id.detail_image_view);
        vote_average = (TextView) v.findViewById(R.id.detail_vote_average);
        release_date = (TextView) v.findViewById(R.id.detail_release_date);
        overview = (TextView) v.findViewById(R.id.detail_overview);
        likeButton = (LikeButton) v.findViewById(R.id.detail_like_btn);
    }


}
