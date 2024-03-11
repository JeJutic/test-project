package pan.artem.test.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.test.entity.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
