package gurpreetsk.me.popularmovies1;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView vote_average, release_date, overview;

    private final String RATED = "Rated: ";
    private final String RELEASE_DATE = "Released: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getHandles();

        setTitle(getIntent().getStringExtra(MovieGridViewFragment.EXTRA_TITLE));

        vote_average.setText(RATED + getIntent().getStringExtra(MovieGridViewFragment.EXTRA_VOTE_AVERAGE));
        release_date.setText(RELEASE_DATE + getIntent().getStringExtra(MovieGridViewFragment.EXTRA_RELEASE_DATE));
        overview.setText(getIntent().getStringExtra(MovieGridViewFragment.EXTRA_OVERVIEW));
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendEncodedPath(getIntent().getStringExtra(MovieGridViewFragment.EXTRA_IMAGE))
                .build();
        Picasso.with(this).load(builder.toString()).fit().into(imageView);

    }

    public void getHandles() {
        imageView = (ImageView) findViewById(R.id.detail_image_view);
        vote_average = (TextView) findViewById(R.id.detail_vote_average);
        release_date = (TextView) findViewById(R.id.detail_release_date);
        overview = (TextView) findViewById(R.id.detail_overview);
    }


}
