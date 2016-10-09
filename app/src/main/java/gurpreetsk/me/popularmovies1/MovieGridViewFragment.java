package gurpreetsk.me.popularmovies1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gurpreetsk.me.popularmovies1.adapters.MoviesAdapter;
import gurpreetsk.me.popularmovies1.models.MovieData;
import gurpreetsk.me.popularmovies1.utils.NetworkConnection;


public class MovieGridViewFragment extends Fragment{

    public static final String TAG = MovieGridViewFragment.class.getSimpleName();

    ArrayList<MovieData> MovieList = new ArrayList<>();
    private MoviesAdapter adapter;

    public static final String EXTRA_TITLE = "title";
    String sortBy;

    public interface Callback {
        void onItemSelected(Bundle data);
    }

    public MovieGridViewFragment() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_grid_view, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.grid_view);
        adapter = new MoviesAdapter(MovieList, getActivity());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }

    private void fetchJSON() {

        Uri uri;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        sortBy = prefs.getString(getString(R.string.sort), getString(R.string.popularity));

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        if (sortBy.equals(getString(R.string.popularity))) {

            uri = Uri.parse("http://api.themoviedb.org/3/movie/popular?")
                    .buildUpon()
                    .appendQueryParameter(getString(R.string.API_KEY_ATTR), getString(R.string.MOVIEDB_API_KEY))
                    .build();

        } else{
            uri = Uri.parse("http://api.themoviedb.org/3/movie/top_rated?")
                    .buildUpon()
                    .appendQueryParameter(getString(R.string.API_KEY_ATTR), getString(R.string.MOVIEDB_API_KEY))
                    .build();
        }

        String url = uri.toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < 20; i++) {
                                JSONObject obj = response.getJSONArray("results").getJSONObject(i);
                                String id = obj.getString("id");
                                String title = obj.getString("original_title");
                                String poster = obj.getString("poster_path");
                                String popularity = obj.getString("popularity");
                                String overview = obj.getString("overview");
                                String release_date = obj.getString("release_date");
                                String vote_average = obj.getString("vote_average");
                                MovieList.add(new MovieData(id, title, poster, popularity, overview, release_date, vote_average));
                            }
                            adapter.notifyDataSetChanged();
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

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkConnection.isNetworkConnected(getActivity()))
            fetchJSON();
        else
            Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!MovieList.isEmpty() && NetworkConnection.isNetworkConnected(getActivity()))
            MovieList.clear();
    }

}