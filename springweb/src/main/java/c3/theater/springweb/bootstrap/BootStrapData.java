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
    public final PromoRepository promoRepository;
    public final TicketRepository ticketRepository;

    public BootStrapData(UserRepository userRepository, AdminRepository adminRepository, MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository, ShowRoomRepository showRoomRepository, PromoRepository promoRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository = movieShowingRepository;
        this.showRoomRepository = showRoomRepository;
        this.promoRepository = promoRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");


        // adds two users
//        User user = new User("bob@gmail.com", "abc123", "Bob", "Smith");
//        user.setStatus(User.Status.ACTIVE);
//        user.setPromo(false);
//        userRepository.save(user);
//        User user2 = new User("bob2@gmail.com", "abc123", "Bob2", "Smith2");
//        user2.setStatus(User.Status.ACTIVE);
//        user2.setPromo(true);
//        userRepository.save(user2);
//        System.out.println("Added basic users");
//
//        // adds an admin
//        Admin admin = new Admin("testAdmin", "aaabbb");
//        adminRepository.save(admin);
//        System.out.println("Added basic admin");
//
//        // adds a movie title
//        MovieTitle movieTitle = new MovieTitle();
//        movieTitle.setTitle("Ex Machina");
//        movieTitle.setRating("R");
//        movieTitle.setGenre("Sci-Fi");
//        movieTitle.setTrailerVideo("https://www.youtube.com/embed/bggUmgeMCdc");
//        movieTitle.setTrailerPicture("https://m.media-amazon.com/images/M/MV5BMTUxNzc0OTIxMV5BMl5BanBnXkFtZTgwNDI3NzU2NDE@._V1_FMjpg_UX1000_.jpg");
//        movieTitle.setComingSoon(false);
//        movieTitle.setCast("Oscar Isac and other guy");
//        movieTitle.setDirector("Mr. Director 1");
//        movieTitle.setProducer("Production Company 300");
//        movieTitle.setSynopsis("A man must test an ai humanoid robot.");
//        movieTitleRepository.save(movieTitle);
//
//        // adds a movie title
//        MovieTitle movieTitle2 = new MovieTitle();
//        movieTitle2.setTitle("The Big Lebowski");
//        movieTitle2.setRating("R");
//        movieTitle2.setGenre("Comedy");
//        movieTitle2.setTrailerVideo("https://www.youtube.com/embed/cd-go0oBF4Y");
//        movieTitle2.setTrailerPicture("https://adventuresinevaluation.podbean.com/mf/web/m5wcvb/Big-Lebowski-movie-poster.jpg");
//        movieTitle2.setComingSoon(false);
//        movieTitle2.setCast("Jeff Bridges and John Goodman");
//        movieTitle2.setDirector("Mr. Director 2");
//        movieTitle2.setProducer("Atlanta Production Company");
//        movieTitle2.setSynopsis("A slacker gets mistaken for a millionaire of the same name and is approached by debt collectors.");
//        movieTitleRepository.save(movieTitle2);
//
//        // adds a movie title
//        MovieTitle movieTitle3 = new MovieTitle();
//        movieTitle3.setTitle("The Batman");
//        movieTitle3.setRating("R");
//        movieTitle3.setGenre("Action");
//        movieTitle3.setTrailerVideo("https://www.youtube.com/embed/mqqft2x_Aa4");
//        movieTitle3.setTrailerPicture("https://m.media-amazon.com/images/M/MV5BYTExZTdhY2ItNGQ1YS00NjJlLWIxMjYtZTI1MzNlMzY0OTk4XkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_.jpg");
//        movieTitle3.setComingSoon(true);
//        movieTitle3.setCast("Robert Pattinson");
//        movieTitle3.setDirector("Mr. Director 1");
//        movieTitle3.setProducer("Crazy Productions");
//        movieTitle3.setSynopsis("A man who loves bats.");
//        movieTitleRepository.save(movieTitle3);
//        //System.out.println(movieTitle3.getState());
//
//        // adds a movie title
//        MovieTitle movieTitle4 = new MovieTitle();
//        movieTitle4.setTitle("Inglourious Basterds");
//        movieTitle4.setRating("R");
//        movieTitle4.setGenre("Action");
//        movieTitle4.setTrailerVideo("https://www.youtube.com/embed/KnrRy6kSFF0");
//        movieTitle4.setTrailerPicture("https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_FMjpg_UX1000_.jpg");
//        movieTitle4.setComingSoon(false);
//        movieTitle4.setCast("Brad Pitt and more");
//        movieTitle4.setDirector("Tarantino");
//        movieTitle4.setProducer("Other production company");
//        movieTitle4.setSynopsis("American soldiers doa  secret plan to combat the nazis.");
//        movieTitleRepository.save(movieTitle4);
//
//        // adds two show rooms
//        ShowRoom showRoomA = new ShowRoom("A");
//        //showRoomA.set
//        ShowRoom showRoomB = new ShowRoom("B");
//        showRoomRepository.save(showRoomA);
//        showRoomRepository.save(showRoomB);
//
//        // adds a movie showing
//        MovieShowing movieShowing = new MovieShowing();
//        movieShowing.setMovieTitle(movieTitle); // connect this showing side to its title
//        movieShowing.setTimePeriod("1");
//        movieShowing.setShowDate("11/24");
//        movieShowing.setShowRoom(showRoomA);
//        movieShowing.setRoomName("A");
//        movieShowingRepository.save(movieShowing);
//
//        // adds a movie showing
//        MovieShowing movieShowing2 = new MovieShowing();
//        movieShowing2.setMovieTitle(movieTitle); // connect this showing side to its title
//        movieShowing2.setTimePeriod("3");
//        movieShowing2.setShowDate("11/24");
//        movieShowing2.setShowRoom(showRoomA);
//        movieShowing2.setRoomName("A");
//        movieShowingRepository.save(movieShowing2);
//
//        Promo promo1 = new Promo("Save 10% on Thanksgiving weekend!", "159GRS", "10", "11/26", "11/28");
//        promoRepository.save(promo1);
//
//        // DO BELOW FOR ALL MOVIE SHOWINGS, DO BELOW DYNAMICALLY IN CONTROLLER CODE
//
//        // adds movie showing to the showroom
//        showRoomA.getMovieShowings().add(movieShowing);
//        showRoomA.getMovieShowings().add(movieShowing2);
//        showRoomRepository.save(showRoomA);
//
//        // adds movie showing to the movie title
//        movieTitle.getMovieShowings().add(movieShowing); // add this showing to the title side
//        movieTitle.getMovieShowings().add(movieShowing2);
//        movieTitleRepository.save(movieTitle);
        Ticket Ticket1 = new Ticket();
        Ticket1.setSeatNum(10);
        System.out.println(Ticket1.getSeatNum());
        System.out.println("Num users: " + userRepository.count());
        System.out.println("Num movie titles: " + movieTitleRepository.count());
    }
}
