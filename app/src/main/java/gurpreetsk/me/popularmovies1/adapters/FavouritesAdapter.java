package gurpreetsk.me.popularmovies1.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.util.List;

import gurpreetsk.me.popularmovies1.DetailActivity;
import gurpreetsk.me.popularmovies1.FavouriteDetailFragment;
import gurpreetsk.me.popularmovies1.R;
import gurpreetsk.me.popularmovies1.data.Database;
import gurpreetsk.me.popularmovies1.data.FavouritesTable;
import gurpreetsk.me.popularmovies1.data.TableStructure;

/**
 * Created by Gurpreet on 08/10/16.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.MyFavouritesViewHolder> {

    private List<Database> favouriteMovieList;
    Context context;

    public static final String TAG = MoviesAdapter.class.getSimpleName();

    public FavouritesAdapter(List<Database> movieList, Context context) {
        favouriteMovieList = movieList;
        this.context = context;
    }

    @Override
    public MyFavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_element, parent, false);
        return new MyFavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyFavouritesViewHolder holder, int position) {
        final Database movie = favouriteMovieList.get(position);
        holder.likeButton.setLiked(true);
        holder.textView.setText(movie.title);
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w500/").buildUpon()
                .appendEncodedPath(movie.poster)
                .build();

        Picasso.with(context)
                .load(builder)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ToBeShown", "FavouritesDetailFragment");
                Bundle data = new Bundle();
                data.putString("FavouritesTitle", movie.title);
                data.putString("FavouritesID", movie.ColumnID);
                data.putString("FavouritesDesc", movie.description);
                data.putString("FavouritesAvg", movie.vote_average);
                data.putString("FavouritesPoster", movie.poster);
                data.putString("FavouritesRelease", movie.release_date);
                intent.putExtra("Favourites", data);
                context.startActivity(intent);
            }
        });

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                likeButton.setLiked(false);
                context.getContentResolver().delete(FavouritesTable.CONTENT_URI, TableStructure.COLUMN_ID + " = ?", new String[]{"" + movie.ColumnID});

            }
        });

    }

    @Override
    public int getItemCount() {
        return favouriteMovieList.size();
    }

    public class MyFavouritesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        CardView cardView;
        LikeButton likeButton;

        public MyFavouritesViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.thumbnail_image_view);
            textView = (TextView) view.findViewById(R.id.thumbnail_text_view);
            cardView = (CardView) view.findViewById(R.id.thumbnail_card_view);
            likeButton = (LikeButton) view.findViewById(R.id.fav_button);
        }

    }

}
