package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.User;
import c3.theater.springweb.email.EmailSender;
import c3.theater.springweb.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final EmailSender emailSender;
    User thisUser;
    //maybe create a User item here to store which user is currently logged in and can access when edit profile is attempted

    public UserController(UserRepository userRepository, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.emailSender = emailSender; // ADDED THIS, make sure it didnt break anything
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
        //System.out.println(user.getFirstName()); // shows how i will accesss user data from attempted login
        userRepository.save(user);
        thisUser = new User();
        thisUser.setFirstName(user.getFirstName());
        thisUser.setLastName(user.getLastName());
        thisUser.setPassword(user.getPassword());
        thisUser.setStatus(user.getStatus());
        thisUser.setId(user.getId()); // not working?, probably not an issue tho
        thisUser.setEmail(user.getEmail());
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

        // make register not send to home logged in till logged in, and dont set this user since it is not active yet

        String code = "abcd1234"; // make sure to randomize this code
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
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
        emailSender.send(thisUser.getEmail(), emailWithCode);

        return "home_loggedin";
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
                    // maybe make a func to do this automatically for two user params
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
                    break; // make sure this doesnt mess with anything and only breaks out of for loop
                }
            }
        }
        if (valid) { // valid login
            returnString = "home_loggedin";
            //thisUser = userElem; // saves login as the current thisUser, DOES THIS  ACTUALLY WORK, maybe instead set all the values


            //System.out.println("This user after login: " + thisUser.toString());
            //System.out.println("Real user after login: " + user.toString());
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
    @GetMapping("/edit_profile")
    public String editProfile(Model model) {
        //System.out.println("thisUser passed to edit profile for auto fill: " + thisUser.toString());
        model.addAttribute("user", thisUser);
        return "users/edit_profile";
    }

    // Processes Edit Profile
    @PostMapping("/process_edit_profile") // what would happen if multiple ones had same mapping, how would html differentiate
    public String processEditProfile(User user) {
        thisUser.setFirstName(user.getFirstName());
        thisUser.setLastName(user.getLastName());
        thisUser.setPassword(user.getPassword());
        thisUser.setStatus(user.getStatus());
        thisUser.setId(user.getId()); // not working?, probably not an issue tho
        thisUser.setEmail(user.getEmail());
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

        for (User userElem : userRepository.findAll()) {
            if (userElem.getEmail().equals(user.getEmail())) {
                //userElem = user;
                //System.out.println("user card 1: " + user.getCardNum1());

                userElem.setFirstName(user.getFirstName());
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
                userRepository.save(userElem);
                break;
            }
        }
        return "home_loggedin";

    }

    // why does this user not have a first and last name saved?, it might not be attached to real user (auto fill in webPage)

    // HELPER METHOD TO ASSIGN ONE USER ALL THE VALUES OF SECOND USER


}
