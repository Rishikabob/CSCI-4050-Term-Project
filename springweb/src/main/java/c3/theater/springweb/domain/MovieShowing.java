package c3.theater.springweb.domain;

import javax.persistence.*;

@Entity
public class MovieShowing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String showDate;
    private String showTime;
    private String endTime;

    @ManyToOne
    private MovieTitle movieTitle;

    // NEEDS A REFERENCE TO A MOVIE TITLE

    public MovieShowing() {
    }

    public MovieShowing(String showDate, String showTime, String endTime, MovieTitle movieTitle) {
        this.showDate = showDate;
        this.showTime = showTime;
        this.endTime = endTime;
        this.movieTitle = movieTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public MovieTitle getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(MovieTitle movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Override
    public String toString() {
        return "MovieShowing{" +
                "id=" + id +
                ", showDate='" + showDate + '\'' +
                ", showTime='" + showTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", movieTitle=" + movieTitle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieShowing that = (MovieShowing) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
