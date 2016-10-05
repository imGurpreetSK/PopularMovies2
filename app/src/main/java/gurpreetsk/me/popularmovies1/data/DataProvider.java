package gurpreetsk.me.popularmovies1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Gurpreet on 03/10/16.
 */

public class DataProvider extends ContentProvider {

    static final int MOVIES_LIST = 100;
    static final int MOVIES_ITEM = 200;
    public static final UriMatcher matcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String AUTHORITY = MoviesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_FAVOURITE + "/#", MOVIES_ITEM);
        uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_FAVOURITE, MOVIES_LIST);

        return uriMatcher;
    }

    MoviesDbHelper helper;
    String favouriteSelection = MoviesContract.FavouriteDatabase.TABLE_NAME + "." + TableStructure.COLUMN_ID + " = ?";


    //IMPLEMENT THESE IN THE SAME ORDER

    @Override
    public boolean onCreate() {
        helper = new MoviesDbHelper(getContext());      //used in most ContentProvider methods
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = matcher.match(uri);

        switch (match) {
            case MOVIES_ITEM:
                return MoviesContract.FavouriteDatabase.SINGLE_RECORD;
            case MOVIES_LIST:
                return MoviesContract.FavouriteDatabase.LIST_OF_RECORDS;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projections, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;
        switch (matcher.match(uri)) {
            case MOVIES_ITEM:
                cursor = helper.getReadableDatabase().query(
                        MoviesContract.FavouriteDatabase.TABLE_NAME,
                        projections,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIES_LIST:
                String id = MoviesContract.FavouriteDatabase.getFavouriteIDFromUri(uri);
                cursor = helper.getReadableDatabase().query(
                        MoviesContract.FavouriteDatabase.TABLE_NAME,
                        projections,
                        favouriteSelection,
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        Uri returnUri;
        final SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long _id = sqLiteDatabase.insert(MoviesContract.FavouriteDatabase.TABLE_NAME, null, contentValues);
        if (_id > 0)
            returnUri = MoviesContract.FavouriteDatabase.buildFavouriteUri(_id);
        else
            throw new UnsupportedOperationException("Failed to insert values into " + uri);

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        int rowsDeleted;
        if (selection == null) selection = "1";
        switch (matcher.match(uri)) {
            case MOVIES_ITEM:
                rowsDeleted = sqLiteDatabase.delete(MoviesContract.FavouriteDatabase.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported Uri " + uri);
        }

        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
