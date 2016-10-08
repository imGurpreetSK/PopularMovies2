package gurpreetsk.me.popularmovies1.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import gurpreetsk.me.popularmovies1.R;

/**
 * Created by Gurpreet on 08/10/16.
 */

public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> trailers = new ArrayList<>();
    ArrayList<String> trailersName = new ArrayList<>();

    public ListAdapter(Context context, ArrayList<String> trailers, ArrayList<String> trailersName) {
        this.context = context;
        this.trailers = trailers;
        this.trailersName = trailersName;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
//        View v = View.inflate(context, R.layout.list_view_element, viewGroup);
        TextView tv = (TextView) v.findViewById(R.id.listview_textview);
        tv.setText(trailersName.get(i));
        return v;
    }
}
