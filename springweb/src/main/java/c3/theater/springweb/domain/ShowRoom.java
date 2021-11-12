package c3.theater.springweb.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ShowRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn(name = "show_room_id") // this is the foreign key, movieSHowing has a FK for showroom id
    private Set<MovieShowing> movieShowings = new HashSet<>();

    private String name;

    public ShowRoom() {
    }

    public ShowRoom(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MovieShowing> getMovieShowings() {
        return movieShowings;
    }

    public void setMovieShowings(Set<MovieShowing> movieShowings) {
        this.movieShowings = movieShowings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "ShowRoom{" +
//                "id=" + id +
//                ", movieShowings=" + movieShowings +
//                ", name='" + name + '\'' +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShowRoom showRoom = (ShowRoom) o;

        return id != null ? id.equals(showRoom.id) : showRoom.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

