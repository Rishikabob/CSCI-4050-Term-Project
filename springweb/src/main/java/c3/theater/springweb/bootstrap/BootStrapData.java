package c3.theater.springweb.bootstrap;

import c3.theater.springweb.domain.*;
import c3.theater.springweb.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import c3.theater.springweb.repositories.UserRepository;

@Component
public class BootStrapData implements CommandLineRunner {

    public final UserRepository userRepository;
    public final AdminRepository adminRepository;
    public final MovieTitleRepository movieTitleRepository;
    public final MovieShowingRepository movieShowingRepository;
    public final ShowRoomRepository showRoomRepository;

    public BootStrapData(UserRepository userRepository, AdminRepository adminRepository, MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository, ShowRoomRepository showRoomRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository = movieShowingRepository;
        this.showRoomRepository = showRoomRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        // adds two users
        User user = new User("bob@gmail.com", "abc123", "Bob", "Smith");
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);
        User user2 = new User("bob2@gmail.com", "abc123", "Bob2", "Smith2");
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user2);
        System.out.println("Added basic users");

        // adds an admin
        Admin admin = new Admin("testAdmin", "aaabbb");
        adminRepository.save(admin);
        System.out.println("Added basic admin");

        // adds a movie title
        MovieTitle movieTitle = new MovieTitle();
        movieTitle.setTitle("Ex Machina");
        movieTitle.setRating("R");
        movieTitle.setGenre("Sci-Fi");
        movieTitle.setTrailerVideo("https://www.youtube.com/embed/bggUmgeMCdc");
        movieTitle.setTrailerPicture("https://m.media-amazon.com/images/M/MV5BMTUxNzc0OTIxMV5BMl5BanBnXkFtZTgwNDI3NzU2NDE@._V1_FMjpg_UX1000_.jpg");
        movieTitle.setComingSoon(false);
        movieTitleRepository.save(movieTitle);

        // adds a movie title
        MovieTitle movieTitle2 = new MovieTitle();
        movieTitle2.setTitle("The Big Lebowski");
        movieTitle2.setRating("R");
        movieTitle2.setGenre("Comedy");
        movieTitle2.setTrailerVideo("https://www.youtube.com/embed/cd-go0oBF4Y");
        movieTitle2.setTrailerPicture("https://adventuresinevaluation.podbean.com/mf/web/m5wcvb/Big-Lebowski-movie-poster.jpg");
        movieTitle2.setComingSoon(false);
        movieTitleRepository.save(movieTitle2);

        // adds a movie title
        MovieTitle movieTitle3 = new MovieTitle();
        movieTitle3.setTitle("The Batman");
        movieTitle3.setRating("R");
        movieTitle3.setGenre("Action");
        movieTitle3.setTrailerVideo("https://www.youtube.com/embed/mqqft2x_Aa4");
        movieTitle3.setTrailerPicture("https://m.media-amazon.com/images/M/MV5BYTExZTdhY2ItNGQ1YS00NjJlLWIxMjYtZTI1MzNlMzY0OTk4XkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_.jpg");
        movieTitle3.setComingSoon(true);
        movieTitleRepository.save(movieTitle3);

        // adds two show rooms
        ShowRoom showRoomA = new ShowRoom("A");
        //showRoomA.set
        ShowRoom showRoomB = new ShowRoom("B");
        showRoomRepository.save(showRoomA);
        showRoomRepository.save(showRoomB);

        // adds a movie showing
        MovieShowing movieShowing = new MovieShowing();
        movieShowing.setMovieTitle(movieTitle); // connect this showing side to its title
        movieShowing.setTimePeriod("1");
        movieShowing.setShowDate("11/24");
        movieShowing.setShowRoom(showRoomA);
        movieShowing.setRoomName("A");
        movieShowingRepository.save(movieShowing);

        // adds a movie showing
        MovieShowing movieShowing2 = new MovieShowing();
        movieShowing2.setMovieTitle(movieTitle); // connect this showing side to its title
        movieShowing2.setTimePeriod("3");
        movieShowing2.setShowDate("11/24");
        movieShowing2.setShowRoom(showRoomA);
        movieShowing2.setRoomName("A");
        movieShowingRepository.save(movieShowing2);

        // adds movie showing to the showroom
        showRoomA.getMovieShowings().add(movieShowing);
        showRoomRepository.save(showRoomA);

        // adds movie showing to the movie title
        movieTitle.getMovieShowings().add(movieShowing); // add this showing to the title side
        movieTitleRepository.save(movieTitle);

        System.out.println("Num users: " + userRepository.count());
        System.out.println("Num movie titles: " + movieTitleRepository.count());
    }
}
