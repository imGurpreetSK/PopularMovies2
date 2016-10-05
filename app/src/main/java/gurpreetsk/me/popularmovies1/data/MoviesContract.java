package gurpreetsk.me.popularmovies1.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Gurpreet on 03/10/16.
 */

public class MoviesContract {

    public static final String CONTENT_SCHEME = "content://";
    public static final String CONTENT_AUTHORITY = "gurpreetsk.me.popularmovies1";

    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    //    public static final String PATH_RATED = "rated";
//    public static final String PATH_POPULAR = "popular";
    public static final String PATH_FAVOURITE = "favourite";

    public static final class FavouriteDatabase implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.
                buildUpon().
                appendPath(PATH_FAVOURITE).
                build();
        public static final String TABLE_NAME = "favourites.db";

        //Retrieve list of records via content provider
        public static final String LIST_OF_RECORDS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;

        //Retrieve single record for DetailFragment
        public static final String SINGLE_RECORD = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;



        public static Uri buildFavouriteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildFavouriteIDUri(String favouriteID) {
            return CONTENT_URI.buildUpon().appendPath(favouriteID).build();
        }

        public static String getFavouriteIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

//    public static final class RatedDatabase implements BaseColumns {
//
//        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath()
//
//    }
//
//    public static final class PopularDatabase implements BaseColumns {
//
//    }

}
