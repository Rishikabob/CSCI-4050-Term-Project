package c3.theater.springweb.bootstrap;

import c3.theater.springweb.domain.MovieShowing;
import c3.theater.springweb.domain.MovieTitle;
import c3.theater.springweb.repositories.*;
import c3.theater.springweb.domain.User;
import c3.theater.springweb.domain.Admin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import c3.theater.springweb.repositories.UserRepository;

@Component
public class BootStrapData implements CommandLineRunner {

    public final UserRepository userRepository;
    public final AdminRepository adminRepository;
    public final MovieTitleRepository movieTitleRepository;
    public final MovieShowingRepository movieShowingRepository;

    public BootStrapData(UserRepository userRepository, AdminRepository adminRepository, MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository = movieShowingRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        //System.out.println("Num users: " + userRepository.count());

        User user = new User("bob@gmail.com", "abc123", "Bob", "Smith");
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);
        User user2 = new User("bob2@gmail.com", "abc123", "Bob2", "Smith2");
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user2);
        System.out.println("Added basic users");

        Admin admin = new Admin("testAdmin", "aaabbb");
        adminRepository.save(admin);
        System.out.println("Added basic admin");

        MovieTitle movieTitle = new MovieTitle();
        movieTitle.setTitle("Test Movie");
        movieTitleRepository.save(movieTitle);

        MovieShowing movieShowing = new MovieShowing();
        movieShowing.setMovieTitle(movieTitle); // connect this showing side to its title
        movieShowing.setShowTime("2:00PM");
        movieShowingRepository.save(movieShowing);

        movieTitle.getMovieShowings().add(movieShowing); // add this showing to the title side
        movieTitleRepository.save(movieTitle);

        System.out.println("Num users: " + userRepository.count());
        System.out.println("Num movie titles: " + movieTitleRepository.count());
    }
}
