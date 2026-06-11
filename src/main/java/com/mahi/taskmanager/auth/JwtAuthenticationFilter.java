package com.mahi.taskmanager.auth;
import com.mahi.taskmanager.user.AppUser;
import com.mahi.taskmanager.user.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    public JwtAuthenticationFilter(JwtService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);   // no token → let security chain decide
            return;
        }

        String token = header.substring(7);     // strip "Bearer "

        try {
            Long userId = jwtService.extractUserId(token);
            AppUser user = appUserRepository.findById(userId).orElse(null);
            if (user == null) {
                chain.doFilter(request, response);
                return;
            }

            var authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
            var auth = new UsernamePasswordAuthenticationToken(
                    user, null, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (JwtException ex) {
            // bad/expired/forged token — just don't authenticate; chain decides
        }

        chain.doFilter(request, response);
    }
}