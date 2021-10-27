package c3.theater.springweb.bootstrap;

import c3.theater.springweb.repositories.UserRepository;
import c3.theater.springweb.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import c3.theater.springweb.repositories.UserRepository;

@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    public BootStrapData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        System.out.println("Num users: " + userRepository.count());

        User user = new User("bob@gmail.com", "abc123", "Bob", "Smith");

        userRepository.save(user);

        System.out.println("Num users: " + userRepository.count());
    }
}
