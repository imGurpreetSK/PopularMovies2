package gurpreetsk.me.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import gurpreetsk.me.popularmovies1.models.MovieData;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent().getStringExtra("ToBeShown").equals("DetailFragment")) {
            DetailFragment detailFrag = new DetailFragment();
            MovieData data = (MovieData) getIntent().getExtras().get(Intent.EXTRA_TEXT);
            Bundle bundle = new Bundle();
            bundle.putString("title", data.getOriginal_title());
            bundle.putString("desc", data.getOverview());
            bundle.putString("vote_average", data.getVote_average());
            bundle.putString("popularity", data.getPopularity());
            bundle.putString("id", data.getId());
            bundle.putString("poster", data.getPoster_path());
            bundle.putString("release", data.getRelease_date());
            detailFrag.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, detailFrag)
                    .commit();
        } else {
            FavouriteDetailFragment favDetailFrag = new FavouriteDetailFragment();
            favDetailFrag.setArguments(getIntent().getBundleExtra("Favourites"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, favDetailFrag)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        MenuItem item = menu.findItem(R.id.share_action_provider);
        ShareActionProvider sap = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra(MovieGridViewFragment.EXTRA_TITLE) + "#P1_Udacity");
        if (sap != null)
            sap.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
