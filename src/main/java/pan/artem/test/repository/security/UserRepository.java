package pan.artem.test.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.test.entity.security.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
