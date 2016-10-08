package gurpreetsk.me.popularmovies1.data;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Gurpreet on 08/10/16.
 */

@SimpleSQLConfig(
        name = "FavouritesProvider",
        authority = "gurpreetsk.me.popularmovies1",
        database = "favourites.db",
        version = 1)

public class MoviesProvider implements ProviderConfig {

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }

}
