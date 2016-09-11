package gurpreetsk.me.popularmovies1.Model;

/**
 * Created by Gurpreet on 11/09/16.
 */
public class MovieData {

    private String original_title, poster_path, popularity, overview, release_date, vote_average;

    public MovieData(String original_title, String poster_path, String popularity, String overview, String release_date, String vote_average) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
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
