package modi.modurang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modi.modurang.domain.User;
import modi.modurang.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void UserSave(String username, String password, String studentNumber) throws DuplicateKeyException {
        if (userRepository.existsByStudentNumber(studentNumber)) {
            throw new DuplicateKeyException("StudentNumber already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setStudentNumber(studentNumber);
        userRepository.save(user);
    }

    public User authenticateUser(String studentNumber, String password) {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElse(null);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
