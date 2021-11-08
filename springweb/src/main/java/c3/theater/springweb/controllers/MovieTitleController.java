package c3.theater.springweb.controllers;


import c3.theater.springweb.repositories.MovieShowingRepository;
import org.springframework.stereotype.Controller;
import c3.theater.springweb.domain.MovieTitle;
import c3.theater.springweb.repositories.MovieTitleRepository;

@Controller
public class MovieTitleController {

    private final MovieTitleRepository movieTitleRepository;
    private final MovieShowingRepository movieShowingRepository;

    public MovieTitleController(MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository) {
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository = movieShowingRepository;
    }

}
