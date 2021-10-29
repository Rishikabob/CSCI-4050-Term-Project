package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.User;
import c3.theater.springweb.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    User thisUser;
    //maybe create a User item here to store which user is currently logged in and can access when edit profile is attempted

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TEST
    // Homepage
    @RequestMapping("/")
    public String goHome(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "home"; // looks for list template (html temp) in directory users
    }

    // Displays list of users
    @RequestMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list"; // looks for list template (html temp) in directory users
    }

    // Displays this user, if one is logged in
    @RequestMapping("/thisUser")
    public String getUser(Model model) {
        model.addAttribute("users", thisUser);
        //model.addAttribute("users", userRepository.findAll());
        return "users/list"; // looks for list template (html temp) in directory users
    }

    // Allows registration
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "users/signup_form";

    }

    // Processes registration
    @PostMapping("/process_register")
    public String processRegister(User user) {
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //String encodedPassword = passwordEncoder.encode(user.getPassword());
        //user.setPassword(encodedPassword);
        user.setStatus(User.Status.INACTIVE);
        System.out.println(user.getFirstName()); // shows how i will accesss user data from attempted login
        userRepository.save(user);

        return "users/register_success";
    }

    // Allows login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "users/login";

    }

    // Processes login
    @PostMapping("/process_login")
    public String processLogin(User user) {
        String enteredEmail = user.getEmail();
        String enteredPassword = user.getPassword();
        thisUser = user; // saves login as the current thisUser
        System.out.println("email and password they tried ot login with: " + enteredEmail + " " + enteredPassword);
        return "users/login_success";
    }


}
