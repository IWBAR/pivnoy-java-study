package ttv.poltoraha.pivka.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.repository.MyUserRepository;
import ttv.poltoraha.pivka.serviceImpl.UserDetailsServiceImpl;

// Рест контроллер для авторизации пользователей

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private MyUserRepository myUserRepository;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User " + username + " logged in successfully!";
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String username) {
        MyUser user = myUserRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("user not found with username = " + username));

        if (user.isEnabled()) {
            return ResponseEntity.ok("Email уже подтверждён.");
        }

        user.setEnabled(true);
        myUserRepository.save(user);

        return ResponseEntity.ok("Email успешно подтверждён!");
    }
}
