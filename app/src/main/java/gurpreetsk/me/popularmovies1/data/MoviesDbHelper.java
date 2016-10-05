package gurpreetsk.me.popularmovies1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gurpreet on 03/10/16.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "favourites.db";
    private static final int DB_VERSION = 1;

    private String QUERY = "CREATE TABLE " + MoviesContract.FavouriteDatabase.TABLE_NAME +
            " ( " + TableStructure.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            TableStructure.COLUMN_TITLE + " TEXT NOT NULL, " +
            TableStructure.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            TableStructure.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
            TableStructure.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            TableStructure.COLUMN_POSTER + " TEXT NOT NULL, " +
            TableStructure.COLUMN_TRAILER + " TEXT NOT NULL, " +
            TableStructure.COLUMN_REVIEWS + " TEXT NOT NULL );";

    public MoviesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY);

        ContentValues cv = new ContentValues();
        cv.put(TableStructure.COLUMN_ID, "1");
        cv.put(TableStructure.COLUMN_TITLE, "TEST");
        cv.put(TableStructure.COLUMN_DESCRIPTION, "TEST");
        cv.put(TableStructure.COLUMN_VOTE_AVERAGE, "TEST");
        cv.put(TableStructure.COLUMN_RELEASE_DATE, "TEST");
        cv.put(TableStructure.COLUMN_POSTER, "TEST");
        cv.put(TableStructure.COLUMN_TRAILER, "TEST");
        cv.put(TableStructure.COLUMN_REVIEWS, "TEST");
        sqLiteDatabase.insert(MoviesContract.FavouriteDatabase.TABLE_NAME, null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.FavouriteDatabase.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
