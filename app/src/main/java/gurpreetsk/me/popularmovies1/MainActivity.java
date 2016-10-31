package gurpreetsk.me.popularmovies1;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import gurpreetsk.me.popularmovies1.sync.MovieSyncAdapter;
import gurpreetsk.me.popularmovies1.utils.NetworkConnection;

public class MainActivity extends AppCompatActivity implements MovieGridViewFragment.Callback {

    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.details_fragment_container) != null;

//        setTitle(R.string.app_name);

        MovieSyncAdapter.initializeSyncAdapter(this);

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
                Intent intent = new Intent(this, FavouritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
            DetailFragment frag = new DetailFragment();
            Bundle bun = new Bundle();
            bun.putAll(data);
            frag.setArguments(bun);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, frag)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }
}
