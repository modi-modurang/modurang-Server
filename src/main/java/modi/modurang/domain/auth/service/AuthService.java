package modi.modurang.domain.auth.service;

import modi.modurang.domain.auth.dto.request.LoginRequest;
import modi.modurang.domain.auth.dto.request.ReissueRequest;
import modi.modurang.domain.auth.dto.request.SignUpRequest;
import modi.modurang.domain.auth.dto.request.UpdatePasswordRequest;
import modi.modurang.domain.user.entity.User;
import modi.modurang.global.security.annotation.CurrentUser;
import modi.modurang.global.security.jwt.dto.Jwt;

public interface AuthService {
    void signup(SignUpRequest request);

    Jwt login(LoginRequest request);

    Jwt reissue(ReissueRequest request);

    void deleteAccount(@CurrentUser User user);

    void updatePassword(@CurrentUser User user, UpdatePasswordRequest request);
}