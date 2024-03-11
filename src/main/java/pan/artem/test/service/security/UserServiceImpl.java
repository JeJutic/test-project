package pan.artem.test.service.security;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pan.artem.test.entity.security.User;
import pan.artem.test.exception.security.RoleNotFoundException;
import pan.artem.test.exception.security.UserAlreadyExistsException;
import pan.artem.test.repository.security.RoleRepository;
import pan.artem.test.repository.security.UserRepository;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(String name, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));

        var roleEntity = roleRepository.findByName(role);
        if (roleEntity == null) {
            throw new RoleNotFoundException("Role named " + role + " not found");
        }
        user.setRole(roleEntity);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // log e
            throw new UserAlreadyExistsException(
                    "User with nickname " + name + " already exists"
            );
        }
    }
}
