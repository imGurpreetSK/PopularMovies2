package gurpreetsk.me.popularmovies1.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;

import java.util.ArrayList;

import gurpreetsk.me.popularmovies1.R;

/**
 * Created by Gurpreet on 08/10/16.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    Context context;
    ArrayList<String> review = new ArrayList<>();
    ArrayList<String> reviewer = new ArrayList<>();

    public ReviewsAdapter(Context context, ArrayList<String> review, ArrayList<String> reviewer) {
        this.context = context;
        this.review = review;
        this.reviewer = reviewer;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_view_element, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {

        holder.reviewTitle.setText(reviewer.get(position));
        holder.reviewDesc.setText(review.get(position));

    }

    @Override
    public int getItemCount() {
        return review.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView reviewTitle, reviewDesc;

        public ReviewViewHolder(View view) {
            super(view);
            reviewTitle = (TextView) view.findViewById(R.id.review_title);
            reviewDesc = (TextView) view.findViewById(R.id.review_desc);
        }

    }

}
