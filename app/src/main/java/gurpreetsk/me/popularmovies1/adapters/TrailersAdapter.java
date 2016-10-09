package gurpreetsk.me.popularmovies1.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gurpreetsk.me.popularmovies1.R;

/**
 * Created by Gurpreet on 09/10/16.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    Context context;
    ArrayList<String> trailers = new ArrayList<>();
    ArrayList<String> trailersName = new ArrayList<>();

    public TrailersAdapter(Context context, ArrayList<String> trailers, ArrayList<String> trailersName) {
        this.context = context;
        this.trailers = trailers;
        this.trailersName = trailersName;
    }

    @Override
    public TrailersAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_view_element, parent, false);
        return new TrailersAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, int position) {

        holder.trailerTitle.setText(trailersName.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailers.get(holder.getAdapterPosition())));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + trailers.get(holder.getAdapterPosition())));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView trailerTitle;
        CardView cardView;

        public TrailerViewHolder(View view) {
            super(view);
            trailerTitle = (TextView) view.findViewById(R.id.trailer_title);
            cardView = (CardView) view.findViewById(R.id.trailer_card_view);
        }
    }
}
