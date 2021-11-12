package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.*;
import c3.theater.springweb.repositories.*;
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
    private final ShowRoomRepository showRoomRepository;

    private User thisUser;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository, ShowRoomRepository showRoomRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository  = movieShowingRepository;
        this.showRoomRepository = showRoomRepository;
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
    @PostMapping("/admin_add_showing")
    public String adminAddShowing(Model model) {
        model.addAttribute("movieShowing", new MovieShowing());
        String returnString = "admin/add_showing";
        return returnString;
    }

    // Add Showing
    @PostMapping("/add_showing_process")
    public String addShowingProcess(MovieShowing movieShowing) {
        String returnString = "admin/admin_logged_in";
        boolean titleValid = false;
        boolean roomValid = false;
        boolean timeValid = true;

        // DO THE BELOW
        // 1. CHECK IF MOVIE TITLE IS REAL IN DB AND THEN SET MOVIETITLE TO THAT
        for (MovieTitle movieElem : movieTitleRepository.findAll()) {
            if (movieElem.getTitle().equals(movieShowing.getTitle())) {
                // movie title is valid
                titleValid = true;
                movieShowing.setMovieTitle(movieElem); // check if this works correctly, or if a new movie elem needs be made and assigned with same values
                System.out.println("movie title valid");
            }
        }
        // 2. CHECK IF SHOW ROOM IS REAL IN DB AND THEN SET SHOWROOM TO THAT
        for (ShowRoom roomElem : showRoomRepository.findAll()) {
            //System.out.println("web room name: " + roomElem.getName());
            if (roomElem.getName().equals(movieShowing.getRoomName())) {
                System.out.println("room elem name: " + roomElem.getName());
                // movie title is valid
                //System.out.println("1");
                roomValid = true;
                System.out.println("2");
                movieShowing.setShowRoom(roomElem); // check if this works correctly, or if a new movie elem needs be made and assigned with same values
                System.out.println("room valid");
            }
        }
        // 3. CHECK IF DATE AND TIME OVERLAP OR NOT for the specific room ONLY
        for (MovieShowing showingElem : movieShowingRepository.findAll()) {
            if (showingElem.getRoomName().equals(movieShowing.getRoomName())) {
                if (showingElem.getTimePeriod().equals(movieShowing.getTimePeriod())) {
                    if (showingElem.getShowDate().equals(movieShowing.getShowDate())) {
                        timeValid = false;
                        System.out.println("time invalid");
                        // MAYBE SET PERIOD VAR
                    }
                }
            }
        }
        // 4. IF EVERYTHING WORKED, ADD TO DB ADN SEND TO CORRECT WEB PAGE
        if (timeValid && roomValid && titleValid) {
            movieShowingRepository.save(movieShowing);
            System.out.println("Added Showing: " + movieShowing.toString());
        }
        // 5. IF FAILURE TRY AGAIN (MAYBE GIVE ERROR MESSAGE)

        //movieShowingRepository.save(movieShowing);


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
