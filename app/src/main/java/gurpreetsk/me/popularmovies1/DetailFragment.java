package gurpreetsk.me.popularmovies1;


import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import gurpreetsk.me.popularmovies1.adapters.ReviewsAdapter;
import gurpreetsk.me.popularmovies1.adapters.TrailersAdapter;
import gurpreetsk.me.popularmovies1.data.Database;
import gurpreetsk.me.popularmovies1.data.FavouritesTable;
import gurpreetsk.me.popularmovies1.data.TableStructure;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ImageView imageView;
    TextView vote_average, release_date, overview;
    LikeButton likeButton;
    RecyclerView reviewsRecyclerView, trailersRecyclerView;

    ReviewsAdapter reviewsAdapter;
    TrailersAdapter trailersAdapter;

    private final String RATED = "Rated: ";
    private final String RELEASE_DATE = "Released:\n  ";

    ArrayList<String> reviews = new ArrayList<>();
    ArrayList<String> reviewer = new ArrayList<>();
    ArrayList<String> trailers = new ArrayList<>();
    ArrayList<String> trailersName = new ArrayList<>();

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        getHandles(v);

        final Bundle data = getArguments();
        final String id = data.getString("id");
        final String title = data.getString("title");
        final String description = data.getString("desc");
        final String vote_avg = data.getString("vote_average");
        final String release = data.getString("release");
        final String poster = data.getString("poster");

        fetchAndSetupReviews(id);
        fetchAndSetupTrailers(id);

        getActivity().setTitle(title);

        vote_average.setText(RATED + vote_avg);
        release_date.setText(RELEASE_DATE + release);
        overview.setText(data.getString("desc"));

        ArrayList<String> idList = queryFavourites();
        if (idList.contains(id))
            likeButton.setLiked(true);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);

                Database testInstance = new Database();
                testInstance.title = title;
                testInstance.description = description;
                testInstance.poster = poster;
                testInstance.release_date = release;
                testInstance.vote_average = vote_avg;
                testInstance.ColumnID = id;

                try {
                    getActivity().getContentResolver().insert(FavouritesTable.CONTENT_URI, FavouritesTable.getContentValues(testInstance, true));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                getActivity().getContentResolver().delete(FavouritesTable.CONTENT_URI, TableStructure.COLUMN_ID + " = ?", new String[]{"" + id});
            }
        });

        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendEncodedPath(poster)
                .build();
        Picasso.with(getActivity()).load(builder.toString()).fit().error(R.mipmap.ic_launcher).into(imageView);


        reviewsAdapter = new ReviewsAdapter(getContext(), reviews, reviewer);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecyclerView.setAdapter(reviewsAdapter);

        trailersAdapter = new TrailersAdapter(getContext(), trailers, trailersName);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trailersRecyclerView.setAdapter(trailersAdapter);

        return v;
    }

    public void getHandles(View v) {
        imageView = (ImageView) v.findViewById(R.id.detail_image_view);
        vote_average = (TextView) v.findViewById(R.id.detail_vote_average);
        release_date = (TextView) v.findViewById(R.id.detail_release_date);
        overview = (TextView) v.findViewById(R.id.detail_overview);
        likeButton = (LikeButton) v.findViewById(R.id.detail_like_btn);
        trailersRecyclerView = (RecyclerView) v.findViewById(R.id.trailers_recycler_view);
        reviewsRecyclerView = (RecyclerView) v.findViewById(R.id.reviews_recycler_view);
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
                            for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                                JSONObject obj = response.getJSONArray("results").getJSONObject(i);
                                reviews.add(obj.getString("content"));
                                reviewer.add(obj.getString("author"));
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
                            for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                                JSONObject obj = response.getJSONArray("results").getJSONObject(i);
                                trailers.add(obj.getString("key"));
                                trailersName.add(obj.getString("name"));
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

        trailersAdapter = new TrailersAdapter(getContext(), trailers, trailersName);
        trailersRecyclerView.setAdapter(trailersAdapter);

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
