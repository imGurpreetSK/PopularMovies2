package gurpreetsk.me.popularmovies1;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private boolean hasFavouritesFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, new MovieGridViewFragment())
                .commit();

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
                if (!hasFavouritesFragment) {
                    FavouritesFragment favFrag = new FavouritesFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, favFrag)
                            .commit();
                    hasFavouritesFragment = true;
                } else {
                    MovieGridViewFragment movieFrag = new MovieGridViewFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, movieFrag)
                            .commit();
                    hasFavouritesFragment = false;
                }
                break;
            case R.id.menu_sort_by:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);

    }


}
