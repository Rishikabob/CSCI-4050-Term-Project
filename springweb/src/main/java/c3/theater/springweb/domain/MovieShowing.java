package c3.theater.springweb.domain;

import javax.persistence.*;

@Entity
public class MovieShowing {

    public enum Period{A, B, C, D}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String showDate;
    private String timePeriod; // DEFAULTS TO 1 ALSOOO, maybe make this a string so that i can error check in here and not get error page on web
    private Period period;
    private String title; // IF ISSUES, JUST USE MOVIETITLE.TITLE IN HTML INSTEAD OF THIS
    private String roomName; // when a new showing is added, check if roomname is valid and set to a real existing room

    @ManyToOne
    private ShowRoom showRoom;

    @ManyToOne
    private MovieTitle movieTitle;


    // NEEDS A REFERENCE TO A MOVIE TITLE

    public MovieShowing() {
    }

    public MovieShowing(String showDate, String timePeriod, ShowRoom showRoom, MovieTitle movieTitle) {
        this.showDate = showDate;
        this.timePeriod = timePeriod;
        this.showRoom = showRoom;
        this.movieTitle = movieTitle;
        title = movieTitle.getTitle();
        // SET PERIOD TO TIME PERIODS CORRESPONDING VALUE
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
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
                ", roomName=" + roomName +
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
