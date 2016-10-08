package gurpreetsk.me.popularmovies1;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gurpreetsk.me.popularmovies1.R;
import gurpreetsk.me.popularmovies1.data.Database;
import gurpreetsk.me.popularmovies1.data.FavouritesTable;
import gurpreetsk.me.popularmovies1.data.TableStructure;
import gurpreetsk.me.popularmovies1.models.MovieData;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ImageView imageView;
    TextView vote_average, release_date, overview;
    LikeButton likeButton;
    ExpandableHeightListView reviewsListView;
    ExpandableHeightListView trailersListView;

    ArrayAdapter<String> reviewsAdapter;
    ArrayAdapter<String> trailersAdapter;

    private final String RATED = "Rated: ";
    private final String RELEASE_DATE = "Released: ";

    ArrayList<String> reviews = new ArrayList<>();
    ArrayList<String> trailers = new ArrayList<>();

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        getHandles(v);

        final Bundle data = getArguments();

        getActivity().setTitle(data.getString("title"));

        vote_average.setText(RATED + data.getString("vote_average"));
        release_date.setText(RELEASE_DATE + data.get("release"));
        overview.setText(data.getString("desc"));
        ArrayList<String> idList = queryFavourites();
        if (idList.contains(data.getString("id")))
            likeButton.setLiked(true);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);

                Database testInstance = new Database();
                testInstance.title = data.getString("title");
                testInstance.description = data.getString("desc");
                testInstance.poster = data.getString("poster");
                testInstance.release_date = data.getString("release");
                testInstance.vote_average = data.getString("vote_average");
                testInstance.ColumnID = data.getString("id");

                try {
                    getActivity().getContentResolver().insert(FavouritesTable.CONTENT_URI, FavouritesTable.getContentValues(testInstance, true));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                getActivity().getContentResolver().delete(FavouritesTable.CONTENT_URI, TableStructure.COLUMN_ID + " = ?", new String[]{"" + data.getString("id")});
            }
        });
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendEncodedPath(data.getString("poster"))
                .build();
        Picasso.with(getActivity()).load(builder.toString()).fit().into(imageView);

        fetchAndSetupReviews(data.getString("id"));
        fetchAndSetupTrailers(data.getString("id"));

        return v;
    }

    public void getHandles(View v) {
        imageView = (ImageView) v.findViewById(R.id.detail_image_view);
        vote_average = (TextView) v.findViewById(R.id.detail_vote_average);
        release_date = (TextView) v.findViewById(R.id.detail_release_date);
        overview = (TextView) v.findViewById(R.id.detail_overview);
        likeButton = (LikeButton) v.findViewById(R.id.detail_like_btn);
        trailersListView = (ExpandableHeightListView) v.findViewById(R.id.trailers_listview);
        reviewsListView = (ExpandableHeightListView) v.findViewById(R.id.reviews_listview);
    }

    private void fetchAndSetupReviews(String id) {

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
                            reviewsAdapter.notifyDataSetChanged();
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

        reviewsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, reviews);
//        if (reviews.isEmpty()) reviews.add("No reviews available");
        reviewsListView.setAdapter(reviewsAdapter);
        reviewsListView.setExpanded(true);

    }

    private void fetchAndSetupTrailers(String id) {

        final Uri videoUri = Uri.parse("http://api.themoviedb.org/3/movie/" + id + "/videos?").buildUpon()
                .appendQueryParameter(getString(R.string.API_KEY_ATTR), getString(R.string.MOVIEDB_API_KEY))
                .build();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = videoUri.toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONArray("results").getJSONObject(i);
                                trailers.add("youtu.be/" + obj.getString("key"));
                            }
                            trailersAdapter.notifyDataSetChanged();
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

        trailersAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, trailers);
//        if (trailers.isEmpty()) trailers.add("No trailers available");
        trailersListView.setAdapter(trailersAdapter);
        trailersListView.setExpanded(true);

    }

    private ArrayList<String> queryFavourites() {

        Cursor c = getActivity().getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<Database> list = FavouritesTable.getRows(c, true);
        ArrayList<String> idList = new ArrayList<>();
        for (Database element : list) {
            idList.add(element.ColumnID);
        }
        return idList;

    }


}
