package ttv.poltoraha.pivka.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ttv.poltoraha.pivka.serviceImpl.UserDetailsServiceImpl;

import java.io.IOException;

@Component
public class PasswordChangeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            if (auth.getPrincipal() instanceof UserDetailsServiceImpl) {
                UserDetailsServiceImpl userDetails = (UserDetailsServiceImpl) auth.getPrincipal();

                if (userDetails.isPasswordChangeRequired()
                        && !request.getRequestURI().equals("/change-password")
                        && !request.getRequestURI().equals("/logout")) {

                    response.sendRedirect("/change-password");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
