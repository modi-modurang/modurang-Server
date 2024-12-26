package modi.modurang.global.security.resolver;

import lombok.RequiredArgsConstructor;
import modi.modurang.domain.user.entity.User;
import modi.modurang.domain.user.error.UserError;
import modi.modurang.domain.user.repository.UserRepository;
import modi.modurang.global.error.CustomException;
import modi.modurang.global.security.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    @NonNull
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new CustomException(UserError.USER_NOT_FOUND);
        }

        String email = userDetails.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));
    }
}