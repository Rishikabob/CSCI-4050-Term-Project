package c3.theater.springweb.controllers;

import c3.theater.springweb.repositories.MovieTitleRepository;
import org.springframework.stereotype.Controller;
import c3.theater.springweb.domain.MovieShowing;
import c3.theater.springweb.repositories.MovieShowingRepository;

@Controller
public class MovieShowingController {

    private final MovieTitleRepository movieTitleRepository;
    private final MovieShowingRepository movieShowingRepository;

    public MovieShowingController(MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository) {
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository = movieShowingRepository;
    }
}
