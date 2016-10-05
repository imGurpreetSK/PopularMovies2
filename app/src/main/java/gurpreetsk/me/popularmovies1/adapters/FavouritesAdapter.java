package gurpreetsk.me.popularmovies1.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import gurpreetsk.me.popularmovies1.R;

/**
 * Created by Gurpreet on 05/10/16.
 */

public class FavouritesAdapter extends CursorAdapter {

    Context context;

    public FavouritesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.grid_layout_element, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CardView cardView = (CardView) view;
        TextView tv = (TextView) cardView.findViewById(R.id.thumbnail_text_view);
        tv.setText(cursor.getColumnName(1));
    }
}
