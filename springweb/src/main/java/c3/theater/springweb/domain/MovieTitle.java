package c3.theater.springweb.domain;


import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MovieTitle {

    public enum Rating{G, PG, PG13, R}
    public enum Genre{ACTION, COMEDY, DRAMA, HORROR, ROMANCE}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //LINK TO MOVIE SHOWINGS, ONE TO MANY
    @OneToMany
    @JoinColumn(name = "movie_title_id") // this is the foreign key, movieSHowing has a FK for movieTitle id
    private Set<MovieShowing> movieShowings = new HashSet<>();

    private String title;
    private Genre genre;
    private String cast;
    private String director;
    private String producer;
    private String synopsis;
    private String[] reviews;
    private String trailerPicture;
    private String trailerVideo;
    private Rating rating;
    // CREATE A WAY TO STORE IF IT IS COMING SOON OR IN THEATERS, AUTOMATICALLY?


    public Set<MovieShowing> getMovieShowings() {
        return movieShowings;
    }

    public void setMovieShowings(Set<MovieShowing> movieShowings) {
        this.movieShowings = movieShowings;
    }

    public MovieTitle() {
    }

    public MovieTitle(String title, Genre genre, String cast, String director, String producer, String synopsis, String[] reviews, String trailerPicture, String trailerVideo, Rating rating) {
        this.title = title;
        this.genre = genre;
        this.cast = cast;
        this.director = director;
        this.producer = producer;
        this.synopsis = synopsis;
        this.reviews = reviews;
        this.trailerPicture = trailerPicture;
        this.trailerVideo = trailerVideo;
        this.rating = rating;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String[] getReviews() {
        return reviews;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    public String getTrailerPicture() {
        return trailerPicture;
    }

    public void setTrailerPicture(String trailerPicture) {
        this.trailerPicture = trailerPicture;
    }

    public String getTrailerVideo() {
        return trailerVideo;
    }

    public void setTrailerVideo(String trailerVideo) {
        this.trailerVideo = trailerVideo;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MovieTitle{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", cast='" + cast + '\'' +
                ", director='" + director + '\'' +
                ", producer='" + producer + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", reviews=" + Arrays.toString(reviews) +
                ", trailerPicture='" + trailerPicture + '\'' +
                ", trailerVideo='" + trailerVideo + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieTitle that = (MovieTitle) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
