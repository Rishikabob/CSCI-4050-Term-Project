package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.User;
import c3.theater.springweb.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserRepository userRepository;
    User thisUser;
    //maybe create a User item here to store which user is currently logged in and can access when edit profile is attempted

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        model.addAttribute("user", new User()); // should it pass a new user, or this user
        return "users/login";
    }

    // Processes login
    @PostMapping("/home_loggedin") // maybe change name since it will return user to home logged in (was processlogin
    public String processLogin(User user) {
        String returnString = "";
        String enteredEmail = user.getEmail();
        String enteredPassword = user.getPassword();
        boolean valid = false;
        // loop through db and see if input matches any db entries
        for (User userElem : userRepository.findAll()) {
            if (userElem.getEmail().equals(enteredEmail)) {
                if (userElem.getPassword().equals(enteredPassword)) {
                    valid = true;
                    break; // make sure this doesnt mess with anything and only breaks out of for loop
                }
            }
        }

        if (valid) { // valid login
            returnString = "home_loggedin";
            thisUser = user; // saves login as the current thisUser, DOES THIS  ACTUALLY WORK, maybe instead set all the values
            System.out.println("This user after login: " + thisUser.toString());
            System.out.println("Real user after login: " + user.toString());
            // IS ABOVE CORRECT WAY TO SET EQUALS, maybe need to just set all values same/have set equals func
        } else { // invalid login
            returnString = "users/login"; // a failed login should return user to login attempt
        }

        //System.out.println("email and password they tried ot login with: " + enteredEmail + " " + enteredPassword);
        return returnString; // maybe dont return home, since hte url will be /rpocesslogin
    }

    // maybe this should be a post mapping and is rather  a function tht will call go home
    // Process logout
    @PostMapping("/home") // maybe change name somehow since it takes u to regular home (was process logout)
    public String processLogout(Model model) { // should is be user or model
        //TEST ALL OF THIS
        System.out.println("process logout was called");
        thisUser = null;
        //Model model = null;
        return goHome(model);
        //String enteredEmail = user.getEmail();
        //String enteredPassword = user.getPassword();
        //thisUser = user; // saves login as the current thisUser
        //System.out.println("email and password they tried ot login with: " + enteredEmail + " " + enteredPassword);
        //return "home";
    }

    // Edit Profile
    @GetMapping("/edit_profile")
    public String editProfile(Model model) {
        //pass the current user info somehow so that it can be displayed
        model.addAttribute("user", thisUser);
        return "users/edit_profile";
    }

    // Processes Edit Profile
    @PostMapping("/process_edit_profile") // what would happen if multiple ones had same mapping, how would html differentiate
    public String processEditProfile(User user) {
        thisUser = user; // MIGHT NOT BE RIGHT WAY TO SET THISUSER AS EQUAL
        //loop through and find the original user db by email (dont let them edit email)
        // change the values of tht user to match values of this new user
        for (User userElem : userRepository.findAll()) {
            if (userElem.getEmail().equals(user.getEmail())) {
                //update all other fields
                System.out.println("user elem first name before edit: " + userElem.getFirstName());
                userElem.setFirstName(user.getFirstName());
                System.out.println("User first name: " + user.getFirstName());
                System.out.println("User elem first name after edit: " + userElem.getFirstName());

                userElem.setLastName(user.getLastName());
                userElem.setCardNum1(user.getCardNum1());
                userElem.setCardBill1(user.getCardBill1());
                userElem.setCardExp1(user.getCardExp1());
                userElem.setPromo(user.isPromo());
                // make sure to add option to change password somewhere
                // make sure to add option to add or edit other payment methods
                //System.out.println("Found the user edited in db");
                System.out.println("User elem entirety after editing: " + userElem.toString());
                //userRepository.save(thisUser); // TRY saving user or thisuser or someway to edit rather than create new
                userRepository.save(user);
                userRepository.delete(userElem); // TEST THIS

                break; // make sure this doesnt mess with anything and only breaks out of for loop

            }
        }
        //userRepository.
        //userRepository.save(user);
        //Above is adding a new user, rather than editing, FIX, also original stays same, but this user is updated
        return "users/edit_success";
    }

    // why does this user not have a first and last name saved?, it might not be attached to real user
    // it did not update the real user is user list,


}
