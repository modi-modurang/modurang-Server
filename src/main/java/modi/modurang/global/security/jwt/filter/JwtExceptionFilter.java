package modi.modurang.global.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modi.modurang.global.error.CustomError;
import modi.modurang.global.error.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            if (response != null) {
                sendErrorResponse(response, e);
            }
        }
    }

    private void sendErrorResponse(HttpServletResponse response, CustomException e) throws IOException {
        CustomError error = e.getError();

        response.setStatus(error.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        map.put("message", error.getMessage());
        map.put("status", error.getStatus());

        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
