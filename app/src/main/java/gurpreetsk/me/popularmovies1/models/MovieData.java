package gurpreetsk.me.popularmovies1.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gurpreet on 11/09/16.
 */
public class MovieData implements Parcelable {

    private String id, original_title, poster_path, popularity, overview, release_date, vote_average;

    public MovieData(String id, String original_title, String poster_path, String popularity, String overview, String release_date, String vote_average) {
        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public MovieData(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        this.id = data[0];
        this.original_title = data[1];
        this.poster_path = data[2];
        this.popularity = data[3];
        this.overview = data[4];
        this.release_date = data[5];
        this.vote_average = data[6];
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.id
                , this.original_title
                , this.poster_path
                , this.popularity
                , this.overview
                , this.release_date
                , this.vote_average});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }
}
