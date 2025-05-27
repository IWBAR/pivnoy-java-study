package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.repository.MyUserRepository;

import java.util.Collection;

// При помощи @RequiredArgsConstructor и lombok можно не заёбываться и удобно инжектить @Autowired через конструктор,
// просто делает private final переменные

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserDetails {

    private final MyUserRepository myUserRepository;
    private final MyUser user;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val abcd = "sdasdsa";
        val user = myUserRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("user not found with username = " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isPasswordChangeRequired() {
        return user.isPasswordChangeRequired();
    }


    public void changePassword(String username, String newPassword) {
        val user = myUserRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username = " + username));

        user.setPassword(passwordEncoder.encode(newPassword));
        myUserRepository.save(user);
    }

    public void setPasswordChangeRequired(String username, boolean required) {
        val user = myUserRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username = " + username));

        user.setPasswordChangeRequired(required);
        myUserRepository.save(user);
    }

    public void markUsersAsMigrated(String username) {
        val user = myUserRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username = " + username));

        setPasswordChangeRequired(username,true);
        myUserRepository.save(user);
    }
}
