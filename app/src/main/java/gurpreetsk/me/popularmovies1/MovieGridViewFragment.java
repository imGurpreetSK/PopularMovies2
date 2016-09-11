package gurpreetsk.me.popularmovies1;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import gurpreetsk.me.popularmovies1.Adapters.MoviesAdapter;
import gurpreetsk.me.popularmovies1.Model.MovieData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieGridViewFragment extends Fragment {

    public static final String TAG = MovieGridViewFragment.class.getSimpleName();

    ArrayList<MovieData> MovieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

    public MovieGridViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Uri uri = Uri.parse("https://api.themoviedb.org/3/discover/movie?").buildUpon()
                .appendQueryParameter(getString(R.string.API_KEY_ATTR), getString(R.string.MOVIEDB_API_KEY))
                .build();
        String url = uri.toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < 20; i++) {
                                JSONObject obj = response.getJSONArray("results").getJSONObject(i);
                                String title = obj.getString("original_title");
                                Log.v(TAG, title);
                                String poster = obj.getString("poster_path");
                                String popularity = obj.getString("popularity");
                                String overview = obj.getString("overview");
                                String release_date = obj.getString("release_date");
                                String vote_average = obj.getString("vote_average");
                                MovieList.add(new MovieData(title, poster, popularity, overview, release_date, vote_average));
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_grid_view, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.grid_view);
        adapter = new MoviesAdapter(MovieList, getActivity());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), MovieList.get(position).getPoster_path(), Toast.LENGTH_SHORT).show();
            }
        }));

        return v;
    }



    public interface ClickListener {
        void onClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public void getData() {
        /*
        MovieList.add(new MovieData("abc","abc","abc","abc","abc","abc"));
        MovieList.add(new MovieData("abc","abc","abc","abc","abc","abc"));
        MovieList.add(new MovieData("abc","abc","abc","abc","abc","abc"));
        MovieList.add(new MovieData("abc","abc","abc","abc","abc","abc"));
        MovieList.add(new MovieData("abc","abc","abc","abc","abc","abc"));
        */


    }
}
