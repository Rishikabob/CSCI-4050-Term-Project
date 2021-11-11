package c3.theater.springweb.domain;

import javax.persistence.*;

@Entity
public class MovieShowing {

    public enum Period{A, B, C, D}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String showDate;
    private int timePeriod;
    private Period period;
    private String title;
    //private String showRoom;

    @ManyToOne
    private ShowRoom showRoom;

    @ManyToOne
    private MovieTitle movieTitle;


    // NEEDS A REFERENCE TO A MOVIE TITLE

    public MovieShowing() {
    }

    public MovieShowing(String showDate, int timePeriod, ShowRoom showRoom, MovieTitle movieTitle) {
        this.showDate = showDate;
        this.timePeriod = timePeriod;
        this.showRoom = showRoom;
        this.movieTitle = movieTitle;
        title = movieTitle.getTitle();
        // SET PERIOD TO TIME PERIODS CORRESPONDING VALUE
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public ShowRoom getShowRoom() {
        return showRoom;
    }

    public void setShowRoom(ShowRoom showRoom) {
        this.showRoom = showRoom;
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

    public MovieTitle getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(MovieTitle movieTitle) {
        this.movieTitle = movieTitle;
        title = movieTitle.getTitle();
    }

    @Override
    public String toString() {
        return "MovieShowing{" +
                "id=" + id +
                ", showDate='" + showDate + '\'' +
                ", timePeriod=" + timePeriod +
                ", period=" + period +
                ", showRoom=" + showRoom +
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
