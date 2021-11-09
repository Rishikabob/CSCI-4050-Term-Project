package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.Admin;
import c3.theater.springweb.domain.User;
import c3.theater.springweb.repositories.AdminRepository;
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

    private User thisUser;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, MovieTitleRepository movieTitleRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.movieTitleRepository = movieTitleRepository;
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
        return returnString;
    }

    // Manage Movies
    @PostMapping("/manage_movies")
    public String adminManageMovies(Model model) {
        model.addAttribute("movies", movieTitleRepository.findAll());
        String returnString = "admin/manage_movies";
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
