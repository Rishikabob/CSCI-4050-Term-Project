package c3.theater.springweb.bootstrap;

import c3.theater.springweb.repositories.UserRepository;
import c3.theater.springweb.repositories.AdminRepository;
import c3.theater.springweb.domain.User;
import c3.theater.springweb.domain.Admin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import c3.theater.springweb.repositories.UserRepository;

@Component
public class BootStrapData implements CommandLineRunner {

    public final UserRepository userRepository;
    public final AdminRepository adminRepository;

    public BootStrapData(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        System.out.println("Num users: " + userRepository.count());

        User user = new User("bob@gmail.com", "abc123", "Bob", "Smith");
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);

        Admin admin = new Admin("testAdmin", "aaabbb");
        adminRepository.save(admin);

        System.out.println("Num users: " + userRepository.count());
    }
}
