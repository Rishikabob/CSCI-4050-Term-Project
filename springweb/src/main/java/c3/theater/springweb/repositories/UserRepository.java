package c3.theater.springweb.repositories;

import c3.theater.springweb.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
