package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.*;
import c3.theater.springweb.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import c3.theater.springweb.email.EmailSender;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final MovieTitleRepository movieTitleRepository;
    private final MovieShowingRepository movieShowingRepository;
    private final ShowRoomRepository showRoomRepository;
    private final PromoRepository promoRepository;
    private final EmailSender emailSender;

    private User thisUser;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, MovieTitleRepository movieTitleRepository, MovieShowingRepository movieShowingRepository, ShowRoomRepository showRoomRepository, PromoRepository promoRepository, EmailSender emailSender) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.movieTitleRepository = movieTitleRepository;
        this.movieShowingRepository  = movieShowingRepository;
        this.showRoomRepository = showRoomRepository;
        this.promoRepository = promoRepository;
        this.emailSender = emailSender;
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
        MovieTitle thisMovieTitle = new MovieTitle();

        // DO THE BELOW
        // 1. CHECK IF MOVIE TITLE IS REAL IN DB AND THEN SET MOVIETITLE TO THAT
        for (MovieTitle movieElem : movieTitleRepository.findAll()) {
            if (movieElem.getTitle().equals(movieShowing.getTitle())) {
                // movie title is valid
                thisMovieTitle = movieElem;
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
            System.out.println("show room var value: " + movieShowing.getShowRoom());

            movieShowing.getShowRoom().getMovieShowings().add(movieShowing);
            showRoomRepository.save(movieShowing.getShowRoom());
            movieShowing.getMovieTitle().getMovieShowings().add(movieShowing);
            movieTitleRepository.save(movieShowing.getMovieTitle()); // check if it actually saves real movie Title. CHECK ALL LINKS
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
        model.addAttribute("promos", promoRepository.findAll());
        model.addAttribute("promo", new Promo());
        String returnString = "admin/manage_promos";
        return returnString;
    }

    // Add Promo
    @PostMapping("/add_promo")
    public String addPromo(Model model, Promo promo) {
        promoRepository.save(promo);

        // SEND TO CORRECT USER EMAILS
        for (User user: userRepository.findAll()) {
            if (user.isPromo()) {
                String promoEmail = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
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
                        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Code: " + promo.getCode() + "</span>\n" +
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
                        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> New Promo! Use code, " + promo.getCode() + " . " + promo.getMessage() + " </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + "\"></a> </p></blockquote>\n  <p></p>" +
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
                emailSender.send(user.getEmail(), promoEmail);
            }
        }

        String returnString = "admin/admin_logged_in";
        return returnString;
    }
}
