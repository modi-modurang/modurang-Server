package modi.modurang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modi.modurang.domain.User;
import modi.modurang.dto.SignupRequestDto;
import modi.modurang.exception.CustomException;
import modi.modurang.exception.ErrorCode;
import modi.modurang.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(SignupRequestDto signupRequestDto) throws CustomException {
        if (userRepository.existsByStudentNumber(signupRequestDto.getStudentNumber())) {
            throw new CustomException(ErrorCode.HAS_STUDENTNUMBER);
        }
        User user = new User();
        user.setUsername(signupRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setStudentNumber(signupRequestDto.getStudentNumber());
        userRepository.save(user);
    }

    public User authenticateUser(String studentNumber, String password) {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
