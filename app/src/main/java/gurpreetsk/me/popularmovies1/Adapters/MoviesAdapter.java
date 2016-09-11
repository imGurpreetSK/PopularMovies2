package gurpreetsk.me.popularmovies1.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import gurpreetsk.me.popularmovies1.Model.MovieData;
import gurpreetsk.me.popularmovies1.R;

/**
 * Created by Gurpreet on 11/09/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private List<MovieData> MovieList;
    Context context;

    public static final String TAG = MoviesAdapter.class.getSimpleName();

    public MoviesAdapter(List<MovieData> movieList, Context context) {
        MovieList = movieList;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_element, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MyViewHolder holder, int position) {
        MovieData movie = MovieList.get(position);
        holder.textView.setText(movie.getOriginal_title());
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w500/").buildUpon()
                .appendEncodedPath(movie.getPoster_path())
                .build();
//        Log.v(TAG, builder.toString());
        Picasso.with(context).load(builder).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.thumbnail_image_view);
            textView = (TextView) view.findViewById(R.id.thumbnail_text_view);
//            textView.setBackground;
        }

    }

}
