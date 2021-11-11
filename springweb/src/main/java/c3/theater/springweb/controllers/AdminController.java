package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.Admin;
import c3.theater.springweb.domain.MovieTitle;
import c3.theater.springweb.domain.User;
import c3.theater.springweb.repositories.AdminRepository;
import c3.theater.springweb.repositories.MovieShowingRepository;
import c3.theater.springweb.repositories.MovieTitleRepository;
import c3.theater.springweb.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final MovieTitleRepository movieTitleRepository;
    private final MovieShowingRepository movieShowingRepository;

    private User thisUser;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository  = movieShowingRepository;
    }

    // Allows login
    @GetMapping("/admin_login")
    public String adminLogin(Model model) {
        model.addAttribute("admin", new Admin()); // should it pass a new user, or this user
        return "admin/a_login";
    }

    // Processes login
    @PostMapping("/home_a_loggedin") // maybe change name since it will return user to home logged in (was processlogin
    public String processLogin(Admin admin) {
        String returnString = "admin/a_login";

        // check if admin correct username a nd password were entered that match one in db
        for (Admin adminElem : adminRepository.findAll()) {
            if (adminElem.getUsername().equals(admin.getUsername())) {
                if (adminElem.getPassword().equals(admin.getPassword())) {
                    returnString = "admin/admin_logged_in";
                }
            }
        }
        return returnString;
    }

    // Logout
    @PostMapping("/a_logout")
    public String adminLogout(Model model) {
        String returnString = "home";
        return returnString;
    }

    // Manage Users
    @PostMapping("/manage_users")
    public String adminManageUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        String returnString = "admin/manage_users";
        return returnString;
    }

    @PostMapping("/manage_user")
    public String adminManageUser(User user) {
       String returnString = "admin/manage_user";
        // find corresponding user and set as this user
        for (User userElem : userRepository.findAll()) {
            if (userElem.getId().equals(user.getId())) {
                thisUser = new User();
                thisUser.setEmail(userElem.getEmail());
                thisUser.setFirstName(userElem.getFirstName());
                user.setEmail(thisUser.getEmail());
                user.setFirstName(userElem.getFirstName());
                // ASSIGN REST OF VALUES TO BOTH USER AND THIS USER
            }
        }
        return returnString;
    }

    @PostMapping("/manage_user_action")
    public String adminManageUserAction(User user) {
        String returnString = "admin/manage_users";
        // find the real correct user in teh repo
        // save values to the correct user
        // THIS REQUIREMENT IS DROPPED FROM THE PROJECT
        return returnString;
    }

    // Manage Movies
    @PostMapping("/manage_showings")
    public String adminManageShowings(Model model) {
        model.addAttribute("showings", movieShowingRepository.findAll());
        String returnString = "admin/manage_showings";
        return returnString;
    }

    // Manage Movies
    @PostMapping("/manage_movies")
    public String adminManageMovies(Model model) {
        model.addAttribute("movies", movieTitleRepository.findAll());
        String returnString = "admin/manage_movies";
        return returnString;
    }

    // Manage Movies
    @PostMapping("/admin_add_movie")
    public String adminAddMovie(Model model) {
        model.addAttribute("movieTitle", new MovieTitle());
        String returnString = "admin/add_movie";
        return returnString;
    }

    // Manage Movies
    @PostMapping("/add_movie_process")
    public String addMovieProcess(MovieTitle movieTitle) { // maybe movieTitle param rather than model?
        //model.addAttribute("movies", movieTitleRepository.findAll());
        // GET THIS MOVEITITLE FROM HTML AND STORE IT IN DATABASE
        //String returnString = "admin/add_movie";
        movieTitleRepository.save(movieTitle);
        System.out.println("Added Movie: " + movieTitle.toString());
        String returnString = "admin/admin_logged_in"; // for some reason it is not showing the added movie
        return returnString;
    }

    // Manage Promos
    @PostMapping("/manage_promos")
    public String adminManagePromos(Model model) {
        //model.addAttribute("movies", movieTitleRepository.findAll());
        String returnString = "admin/manage_promos";
        return returnString;
    }
}
