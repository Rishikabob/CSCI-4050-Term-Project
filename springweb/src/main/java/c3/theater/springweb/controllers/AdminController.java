package c3.theater.springweb.controllers;

import c3.theater.springweb.domain.Admin;
import c3.theater.springweb.repositories.AdminRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
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

    @PostMapping("/a_logout")
    public String adminLogout(Model model) {
        String returnString = "home";



        return returnString;
    }

}
