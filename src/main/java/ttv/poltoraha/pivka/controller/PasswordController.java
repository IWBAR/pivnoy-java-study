package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ttv.poltoraha.pivka.repository.MyUserRepository;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PasswordController {

    private MyUserRepository userRepository;
    private PasswordEncoder encoder;

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String password, Principal user) {
        val trueUser = userRepository.findByUsername(user.getName());
        val encodedPass = encoder.encode(password);
        trueUser.setPassword(encodedPass);
        trueUser.setPasswordChangeRequired(false);
        userRepository.save(trueUser);
        return "redirect:/login";
    }
}
