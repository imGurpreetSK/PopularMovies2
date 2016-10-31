package gurpreetsk.me.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import gurpreetsk.me.popularmovies1.sync.MovieSyncAdapter;

public class FavouritesActivity extends AppCompatActivity implements FavouritesFragment.Callback {

    private boolean hasFavouritesFragment = false;
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favourites);

        if (findViewById(R.id.favourites_details_fragment_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
        setTitle(R.string.action_favourites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.swapFavouritesFragment:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_sort_by:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Bundle data) {
        if (mTwoPane) {
            FavouriteDetailFragment frag = new FavouriteDetailFragment();
            Bundle bun = new Bundle();
            bun.putAll(data);
            frag.setArguments(bun);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.favourites_details_fragment_container, frag)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }
}
