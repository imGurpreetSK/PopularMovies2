package gurpreetsk.me.popularmovies1.data;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Gurpreet on 08/10/16.
 */

@SimpleSQLTable(
        table = "Favourites",
        provider = "FavouritesProvider")

public class Database {

    @SimpleSQLColumn(value = TableStructure.COLUMN_ID, primary = true)
    public String ColumnID;

    @SimpleSQLColumn(TableStructure.COLUMN_TITLE)
    public String title;

    @SimpleSQLColumn(TableStructure.COLUMN_DESCRIPTION)
    public String description;

    @SimpleSQLColumn(TableStructure.COLUMN_VOTE_AVERAGE)
    public String vote_average;

    @SimpleSQLColumn(TableStructure.COLUMN_RELEASE_DATE)
    public String release_date;

    @SimpleSQLColumn(TableStructure.COLUMN_POSTER)
    public String poster;

//    @SimpleSQLColumn(TableStructure.COLUMN_IS_FAVOURITE)
//    public boolean isFavourite;

//    @SimpleSQLColumn(TableStructure.COLUMN_REVIEWS)
//    String reviews;

//    @SimpleSQLColumn(TableStructure.COLUMN_TRAILER)
//    String trailer;

}
