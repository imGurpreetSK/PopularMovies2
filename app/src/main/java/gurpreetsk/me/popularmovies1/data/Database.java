package gurpreetsk.me.popularmovies1.data;

import android.os.Parcel;
import android.os.Parcelable;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;
import gurpreetsk.me.popularmovies1.models.MovieData;

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
/*
    public Database(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);
        this.ColumnID = data[0];
        this.title = data[1];
        this.description = data[2];
        this.vote_average = data[3];
        this.release_date = data[4];
        this.poster = data[5];
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.ColumnID
                , this.title
                , this.description
                , this.vote_average
                , this.release_date
                , this.poster});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Database createFromParcel(Parcel in) {
            return new Database(in);
        }

        public Database[] newArray(int size) {
            return new Database[size];
        }
    };
    */
}
