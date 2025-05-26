package ttv.poltoraha.pivka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ttv.poltoraha.pivka.serviceImpl.UserDetailsServiceImpl;

@Controller
public class PasswordChangeController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String processPasswordChange(@RequestParam String newPassword,
                                        HttpExchange.Principal principal,
                                        RedirectAttributes redirectAttributes) {
        String username = principal.getName();

        userService.changePassword(username, newPassword);
        userService.setPasswordChangeRequired(username, false);

        redirectAttributes.addFlashAttribute("message", "Password changed successfully");
        return "redirect:/home";
    }
}
