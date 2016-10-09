package gurpreetsk.me.popularmovies1.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gurpreetsk.me.popularmovies1.DetailActivity;
import gurpreetsk.me.popularmovies1.MainActivity;
import gurpreetsk.me.popularmovies1.MovieGridViewFragment;
import gurpreetsk.me.popularmovies1.R;
import gurpreetsk.me.popularmovies1.data.Database;
import gurpreetsk.me.popularmovies1.data.FavouritesTable;
import gurpreetsk.me.popularmovies1.data.TableStructure;
import gurpreetsk.me.popularmovies1.models.MovieData;

/**
 * Created by Gurpreet on 11/09/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<MovieData> MovieList;
    Context context;
//    boolean mTwoPane;

    public static final String TAG = MoviesAdapter.class.getSimpleName();

    public MoviesAdapter(List<MovieData> movieList, Context context) {//, boolean mTwoPane) {
        MovieList = movieList;
        this.context = context;
//        this.mTwoPane = mTwoPane;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_element, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder holder, int position) {
        final MovieData movie = MovieList.get(position);

        ArrayList<String> idList = queryFavourites();
        if (idList.contains(movie.getId()))
            holder.likeButton.setLiked(true);


        holder.textView.setText(movie.getOriginal_title());
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w500/").buildUpon()
                .appendEncodedPath(movie.getPoster_path())
                .build();
        Picasso.with(context).load(builder).error(R.drawable.ic_error).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mTwoPane) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("DETAIL", MovieList.get(holder.getAdapterPosition()));
                    ((MovieGridViewFragment.Callback) context).onItemSelected(bundle);
                } else {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("ToBeShown", "DetailFragment");
                    intent.putExtra(Intent.EXTRA_TEXT, MovieList.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            }
        });

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);
                Database testInstance = new Database();
                testInstance.title = movie.getOriginal_title();
                testInstance.description = movie.getOverview();
                testInstance.poster = movie.getPoster_path();
                testInstance.release_date = movie.getRelease_date();
                testInstance.vote_average = movie.getVote_average();
                testInstance.ColumnID = movie.getId();
                try {
                    context.getContentResolver().insert(FavouritesTable.CONTENT_URI, FavouritesTable.getContentValues(testInstance, true));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                context.getContentResolver().delete(FavouritesTable.CONTENT_URI, TableStructure.COLUMN_ID + " = ?", new String[]{"" + movie.getId()});
            }
        });
    }

    private ArrayList<String> queryFavourites() {

        Cursor c = context.getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<Database> list = FavouritesTable.getRows(c, true);
        ArrayList<String> idList = new ArrayList<>();
        for (Database element : list) {
            idList.add(element.ColumnID);
        }

        return idList;

    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        CardView cardView;
        LikeButton likeButton;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.thumbnail_image_view);
            textView = (TextView) view.findViewById(R.id.thumbnail_text_view);
            cardView = (CardView) view.findViewById(R.id.thumbnail_card_view);
            likeButton = (LikeButton) view.findViewById(R.id.fav_button);
        }

    }

}