package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.MovieTitle;
import c3.theater.springweb.domain.User;
import c3.theater.springweb.email.EmailSender;
import c3.theater.springweb.repositories.MovieShowingRepository;
import c3.theater.springweb.repositories.MovieTitleRepository;
import c3.theater.springweb.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final MovieTitleRepository movieTitleRepository;
    private final MovieShowingRepository movieShowingRepository;
    //private final MovieTitleRepository comingSoon;
    //private final MovieTitleRepository nowPlaying;
    List<MovieTitle> comingSoon;
    List<MovieTitle> nowPlaying;

    //private MovieTitle[] nowPlaying;

    private final EmailSender emailSender;
    User thisUser;
    private User thisRegAttemptUser;
    private String thisRegCode; // the registration code
    private String thisForgotPasswordCode;

    public UserController(UserRepository userRepository, EmailSender emailSender, MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository, MovieTitleRepository comingSoon, MovieTitleRepository nowPlaying) {
        this.userRepository = userRepository;
        this.emailSender = emailSender; // ADDED THIS, make sure it didnt break anything
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository = movieShowingRepository;
        //this.comingSoon = comingSoon; // SEE IF THIS AND LINE BELOW WORK
        //this.nowPlaying = nowPlaying;
    }

    // Homepage
    @RequestMapping("/")
    public String goHome(Model model) {
        // DONT KEEP ADDING UPON REFRESH
        comingSoon = new ArrayList<MovieTitle>();
        nowPlaying  = new ArrayList<MovieTitle>();
        for (MovieTitle movie : movieTitleRepository.findAll()) {
            if (movie.isComingSoon()) {
                //System.out.println("is coming soon : " + movie);
                comingSoon.add(movie);
                //nowPlaying.delete(movie);
            } else if (movie.isComingSoon() == false){
                //System.out.println("is now playing: " + movie);
                nowPlaying.add(movie);
                //comingSoon.delete(movie);
            }
        }

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("movies", movieTitleRepository.findAll());
        model.addAttribute("shows", movieShowingRepository.findAll()); // make sure it shows on home page and is updated when we add stuff
        model.addAttribute("movieTitle", new MovieTitle()); // THE MOVIE TITLE WE WANT INFO FOR
        model.addAttribute("comingSoons", comingSoon);
        model.addAttribute("nowPlayings", nowPlaying);
        // add two diff things, one for coming soon and one for now playing
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
        user.setStatus(User.Status.INACTIVE);
        String returnString = "";
        boolean validNewEmail = true;
        // determine if valid or not
        for (User userElem : userRepository.findAll()) {
            if (userElem.getEmail().equals(user.getEmail())) {
                validNewEmail = false;
            }
        }

        if (validNewEmail) {
            userRepository.save(user);
            thisRegAttemptUser = new User();
            thisRegAttemptUser.setFirstName(user.getFirstName());
            thisRegAttemptUser.setLastName(user.getLastName());
            thisRegAttemptUser.setPassword(user.getPassword());
            thisRegAttemptUser.setStatus(user.getStatus());
            thisRegAttemptUser.setId(user.getId()); // not working?, probably not an issue tho
            thisRegAttemptUser.setEmail(user.getEmail());
            thisRegAttemptUser.setCardNum1(user.getCardNum1());
            thisRegAttemptUser.setCardBill1(user.getCardBill1());
            thisRegAttemptUser.setCardExp1(user.getCardExp1());
            thisRegAttemptUser.setCardNum2(user.getCardNum2());
            thisRegAttemptUser.setCardBill2(user.getCardBill2());
            thisRegAttemptUser.setCardExp2(user.getCardExp2());
            thisRegAttemptUser.setCardNum3(user.getCardNum3());
            thisRegAttemptUser.setCardBill3(user.getCardBill3());
            thisRegAttemptUser.setCardExp3(user.getCardExp3());
            thisRegAttemptUser.setPromo(user.isPromo());
            thisRegAttemptUser.setPhone(user.getPhone());

            int random_intA = (int) Math.floor(Math.random() * (9 + 1) + 0); //
            int random_intB = (int) Math.floor(Math.random() * (9 + 1) + 0); //
            int random_intC = (int) Math.floor(Math.random() * (9 + 1) + 0); //
            int random_intD = (int) Math.floor(Math.random() * (9 + 1) + 0); //
            String generatedString = String.valueOf(random_intA) + String.valueOf(random_intB) + String.valueOf(random_intC) + String.valueOf(random_intD);
            thisRegCode = generatedString;
            //System.out.println("genereated code string: " + generatedString);
            String emailWithCode = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                    "\n" +
                    "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                    "\n" +
                    "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                    "    <tbody><tr>\n" +
                    "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                    "        \n" +
                    "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                    "          <tbody><tr>\n" +
                    "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                    "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                    "                  <tbody><tr>\n" +
                    "                    <td style=\"padding-left:10px\">\n" +
                    "                  \n" +
                    "                    </td>\n" +
                    "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                    "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Code: " + generatedString + "</span>\n" +
                    "                    </td>\n" +
                    "                  </tr>\n" +
                    "                </tbody></table>\n" +
                    "              </a>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </tbody></table>\n" +
                    "        \n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody></table>\n" +
                    "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                    "    <tbody><tr>\n" +
                    "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                    "      <td>\n" +
                    "        \n" +
                    "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                    "                  <tbody><tr>\n" +
                    "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                    "                  </tr>\n" +
                    "                </tbody></table>\n" +
                    "        \n" +
                    "      </td>\n" +
                    "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                    "    </tr>\n" +
                    "  </tbody></table>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                    "    <tbody><tr>\n" +
                    "      <td height=\"30\"><br></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                    "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                    "        \n" +
                    "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please enter the code, " + generatedString + " into the site. </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + "\"></a> </p></blockquote>\n  <p></p>" +
                    "        \n" +
                    "      </td>\n" +
                    "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td height=\"30\"><br></td>\n" +
                    "    </tr>\n" +
                    "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                    "\n" +
                    "</div></div>";
            emailSender.send(thisRegAttemptUser.getEmail(), emailWithCode);
            returnString = "users/register_confirm";
        } else {
            returnString = "users/signup_form";
            // TRY TO ADD APPROPRIATE MESSAGE TO USER INDICATING EMAIL IS ALREADY USED
        }
        return returnString;
    }

    //METHOD FOR WHEN THE USER REGISTRATION IS CONFIRMED
    // Confirm registration
    @PostMapping("/confirm_registration")
    public String confirmRegistration(User user) { // maybe pass String rather than user?, how to do multiple params
        //model.addAttribute("code", thisRegCode);
        //model.addAttribute("user", new User());
        System.out.println("the user code entered stored in getCardNum2 : " + user.getCardNum2());
        String returnString = "";

        if (user.getCardNum2().equals(thisRegCode)) { // valid code entered
            returnString = "home_loggedin";
            thisUser = new User();
            thisUser.setFirstName(thisRegAttemptUser.getFirstName());
            thisUser.setLastName(thisRegAttemptUser.getLastName());
            thisUser.setPassword(thisRegAttemptUser.getPassword());
            thisUser.setStatus(thisRegAttemptUser.getStatus());
            thisUser.setId(thisRegAttemptUser.getId()); // not working?, probably not an issue tho
            thisUser.setEmail(thisRegAttemptUser.getEmail());
            thisUser.setCardNum1(thisRegAttemptUser.getCardNum1());
            thisUser.setCardBill1(thisRegAttemptUser.getCardBill1());
            thisUser.setCardExp1(thisRegAttemptUser.getCardExp1());
            thisUser.setCardNum2(thisRegAttemptUser.getCardNum2());
            thisUser.setCardBill2(thisRegAttemptUser.getCardBill2());
            thisUser.setCardExp2(thisRegAttemptUser.getCardExp2());
            thisUser.setCardNum3(thisRegAttemptUser.getCardNum3());
            thisUser.setCardBill3(thisRegAttemptUser.getCardBill3());
            thisUser.setCardExp3(thisRegAttemptUser.getCardExp3());
            thisUser.setPromo(thisRegAttemptUser.isPromo());
            thisUser.setPhone(thisRegAttemptUser.getPhone());
            for (User userElem : userRepository.findAll()) { // finds user in db that
                if (userElem.getEmail().equals(thisUser.getEmail())) {
                    userElem.setStatus(User.Status.ACTIVE);
                    userRepository.save(userElem);
                }
            }
        } else { // invalid code entered
            returnString = "users/register_confirm";
        }


        return returnString;
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
        boolean userInactive = false;
        // loop through db and see if input matches any db entries
        for (User userElem : userRepository.findAll()) { // checks if valid email and password, and sets it to this user
            if (userElem.getEmail().equals(enteredEmail)) {
                if (userElem.getPassword().equals(enteredPassword)) {
                    valid = true;
                    if (userElem.getStatus().equals(User.Status.INACTIVE)) { // if user is valid but inactive
                        userInactive = true;
                    }
                    thisUser = new User();
                    thisUser.setFirstName(userElem.getFirstName());
                    thisUser.setLastName(userElem.getLastName());
                    thisUser.setPassword(userElem.getPassword());
                    thisUser.setStatus(userElem.getStatus());
                    thisUser.setId(userElem.getId());
                    thisUser.setEmail(userElem.getEmail());
                    thisUser.setCardNum1(userElem.getCardNum1());
                    thisUser.setCardBill1(userElem.getCardBill1());
                    thisUser.setCardExp1(userElem.getCardExp1());
                    thisUser.setCardNum2(userElem.getCardNum2());
                    thisUser.setCardBill2(userElem.getCardBill2());
                    thisUser.setCardExp2(userElem.getCardExp2());
                    thisUser.setCardNum3(userElem.getCardNum3());
                    thisUser.setCardBill3(userElem.getCardBill3());
                    thisUser.setCardExp3(userElem.getCardExp3());
                    thisUser.setPromo(userElem.isPromo());
                    thisUser.setPhone(userElem.getPhone());
                    break; // make sure this doesnt mess with anything and only breaks out of for loop
                }
            }
        }
        if (valid) { // valid login
            returnString = "home_loggedin";
            if (userInactive) {
                // if user is valid, but not active yet, send to confirmation page and send confirm code
                // send confirm code
                int random_intA = (int) Math.floor(Math.random() * (9 + 1) + 0); //
                int random_intB = (int) Math.floor(Math.random() * (9 + 1) + 0); //
                int random_intC = (int) Math.floor(Math.random() * (9 + 1) + 0); //
                int random_intD = (int) Math.floor(Math.random() * (9 + 1) + 0); //
                String generatedString = String.valueOf(random_intA) + String.valueOf(random_intB) + String.valueOf(random_intC) + String.valueOf(random_intD);
                thisRegCode = generatedString;
                //System.out.println("genereated code string: " + generatedString);
                String emailWithCode = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                        "\n" +
                        "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                        "\n" +
                        "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                        "    <tbody><tr>\n" +
                        "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                        "        \n" +
                        "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                        "          <tbody><tr>\n" +
                        "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                        "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                        "                  <tbody><tr>\n" +
                        "                    <td style=\"padding-left:10px\">\n" +
                        "                  \n" +
                        "                    </td>\n" +
                        "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Code: " + generatedString + "</span>\n" +
                        "                    </td>\n" +
                        "                  </tr>\n" +
                        "                </tbody></table>\n" +
                        "              </a>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "        </tbody></table>\n" +
                        "        \n" +
                        "      </td>\n" +
                        "    </tr>\n" +
                        "  </tbody></table>\n" +
                        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                        "    <tbody><tr>\n" +
                        "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                        "      <td>\n" +
                        "        \n" +
                        "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                        "                  <tbody><tr>\n" +
                        "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                        "                  </tr>\n" +
                        "                </tbody></table>\n" +
                        "        \n" +
                        "      </td>\n" +
                        "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                        "    </tr>\n" +
                        "  </tbody></table>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                        "    <tbody><tr>\n" +
                        "      <td height=\"30\"><br></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                        "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                        "        \n" +
                        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please enter the code, " + generatedString + " into the site. </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + "\"></a> </p></blockquote>\n  <p></p>" +
                        "        \n" +
                        "      </td>\n" +
                        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td height=\"30\"><br></td>\n" +
                        "    </tr>\n" +
                        "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                        "\n" +
                        "</div></div>";
                emailSender.send(thisRegAttemptUser.getEmail(), emailWithCode);
                // send to confirm page
                returnString = "users/register_confirm";
            }
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
        //System.out.println("process logout was called");
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
    @GetMapping("/info")
    public String getInfo(Model model, MovieTitle movieTitle) {
        //System.out.println("thisUser passed to edit profile for auto fill: " + thisUser.toString());
        //model.addAttribute("user", thisUser);
        //System.out.println("INFO CALLED");
        //System.out.println(movieTitle);
        //model.addAttribute("showings", movieShowingRepository.findAll());
        //System.out.println(movieShowingRepository.findAll());

        for (MovieTitle movieTest : movieTitleRepository.findAll()) {
            if (movieTest.getTitle().equals(movieTitle.getTitle())) {
                model.addAttribute("shows", movieTest.getMovieShowings());
            }
        }
        return "/info";
    }

    // Edit Profile
    @GetMapping("/edit_profile")
    public String editProfile(Model model) {
        //System.out.println("thisUser passed to edit profile for auto fill: " + thisUser.toString());
        model.addAttribute("user", thisUser);
        return "users/edit_profile";
    }

    // Processes Edit Profile
    @PostMapping("/process_edit_profile")
    // what would happen if multiple ones had same mapping, how would html differentiate
    public String processEditProfile(User user) {
        thisUser.setFirstName(user.getFirstName());
        thisUser.setLastName(user.getLastName());
        thisUser.setCardNum1(user.getCardNum1());
        thisUser.setCardBill1(user.getCardBill1());
        thisUser.setCardExp1(user.getCardExp1());
        thisUser.setCardNum2(user.getCardNum2());
        thisUser.setCardBill2(user.getCardBill2());
        thisUser.setCardExp2(user.getCardExp2());
        thisUser.setCardNum3(user.getCardNum3());
        thisUser.setCardBill3(user.getCardBill3());
        thisUser.setCardExp3(user.getCardExp3());
        thisUser.setPromo(user.isPromo());
        thisUser.setPhone(user.getPhone());

        //System.out.println("user email before loop: " + user.getEmail());  // USER EMAIL IS NEVER SET, CHECK AGAISNT REAL EMAIL SOMEHOW
        for (User userElem : userRepository.findAll()) {
//            System.out.println("looping through db");
//            System.out.println("user elem looping email to find match: " + userElem.getEmail());
            if (userElem.getEmail().equals(thisUser.getEmail())) {


                //System.out.println("user elem first name b4: " + userElem.getFirstName());
                //System.out.println("user first name before: " + user.getFirstName());
                userElem.setFirstName(user.getFirstName());
                //System.out.println("user elem first name after: " + userElem.getFirstName());
                userElem.setLastName(user.getLastName());
                userElem.setCardNum1(user.getCardNum1());
                userElem.setCardBill1(user.getCardBill1());
                userElem.setCardExp1(user.getCardExp1());
                userElem.setCardNum2(user.getCardNum2());
                userElem.setCardBill2(user.getCardBill2());
                userElem.setCardExp2(user.getCardExp2());
                userElem.setCardNum3(user.getCardNum3());
                userElem.setCardBill3(user.getCardBill3());
                userElem.setCardExp3(user.getCardExp3());
                userElem.setPromo(user.isPromo());
                userElem.setPhone(user.getPhone());
                userRepository.save(userElem);
                break;
            }
        }
        return "home_loggedin";

    }

    // Change Password
    @GetMapping("/change_password") // what would happen if multiple ones had same mapping, how would html differentiate
    public String changePassword(Model model) {
        model.addAttribute("user", thisUser);
        return "users/change_password";

    }

    // Processes Change Password
    @PostMapping("/process_change_password")
    // what would happen if multiple ones had same mapping, how would html differentiate
    public String processChangePassword(User user) {
        String returnString = "";
        System.out.println("old user pass: " + user.getPassword());
        System.out.println("new user pass: " + user.getCardBill2());
        boolean validAttempt = false;
        // check if old password matches password for thisUsers email
        for (User userElem : userRepository.findAll()) {
            if (userElem.getEmail().equals(thisUser.getEmail())) {
                if (userElem.getPassword().equals(user.getPassword())) {
                    validAttempt = true;
                    userElem.setPassword(user.getCardBill2());
                    userRepository.save(userElem);
                }
            }
        }
        // if old pass is valid
        if (validAttempt) {
            returnString = "home_loggedin";
        } else {
            returnString = "users/change_password";
        }


        return returnString;
    }

    // Forgot Password
    @GetMapping("/forgot_password") // what would happen if multiple ones had same mapping, how would html differentiate
    public String forgotPassword(User user) {
        //model.addAttribute("user", new User()); // is new user the correct thing to pass or thisUser
        // get email from user
        // send email with code
        // set thisForgotPasswordCode
        System.out.println("user forgot pass email: " + user.getEmail());
        int random_intA = (int) Math.floor(Math.random() * (9 + 1) + 0); //
        int random_intB = (int) Math.floor(Math.random() * (9 + 1) + 0); //
        int random_intC = (int) Math.floor(Math.random() * (9 + 1) + 0); //
        int random_intD = (int) Math.floor(Math.random() * (9 + 1) + 0); //
        String generatedString = String.valueOf(random_intA) + String.valueOf(random_intB) + String.valueOf(random_intC) + String.valueOf(random_intD);
        thisForgotPasswordCode = generatedString;
        //System.out.println("genereated code string: " + generatedString);
        String emailWithCode = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Code: " + generatedString + "</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please enter the code, " + generatedString + " into the site. </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + "\"></a> </p></blockquote>\n  <p></p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
        boolean emailIsValid = false;
        for (User userElem : userRepository.findAll()) {
            if (userElem.getEmail().equals(user.getEmail())) { // email is attached to account
                emailIsValid = true;
            }
        }
        if (emailIsValid) {
            emailSender.send(user.getEmail(), emailWithCode); // only send email w code if its attached to an account
            thisUser = new User();
            thisUser.setEmail(user.getEmail());
        }
        return "users/forgot_password";
    }

    // Processes forgot Password
    @PostMapping("/process_forgot_password")
    public String processForgotPassword(User user) {
        String returnString = "";
        boolean validEmail = false;
        boolean validCode = false;
        String enteredCode = user.getCardBill2();
        System.out.println("forgot pass code: " + thisForgotPasswordCode);
        System.out.println("enteredcode: " + enteredCode);
        //System.out.println("user email: " + thisUser.getEmail());
        if (thisUser == null) {
            thisUser = new User();
        }
        for (User userElem : userRepository.findAll()) {
            if (userElem.getEmail().equals(thisUser.getEmail())) { // email is attached to account
                validEmail = true;
                System.out.println("elem email equals user email");
                if (thisForgotPasswordCode.equals(enteredCode)) { // code is also valid
                    // THIS IS NOT WORKINg
                    System.out.println("entered code matches email code for forgot pass");
                    validCode = true;
                    userElem.setPassword(user.getPassword());
                    userRepository.save(userElem);
                    returnString = "home";
                }
            }
        }
        if (!validCode) {
            returnString = "users/forgot_password"; // wrong code entered
        } else if (!validEmail) {
            returnString = "users/login"; // wrong email entered
        }
        return returnString;
    }

}
