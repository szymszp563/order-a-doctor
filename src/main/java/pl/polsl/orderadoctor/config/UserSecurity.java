package pl.polsl.orderadoctor.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.UserService;

@Component
@Log4j2
public class UserSecurity {

    private final UserService userService;
    private final DoctorService doctorService;

    public UserSecurity(UserService userService, DoctorService doctorService) {
        this.userService = userService;
        this.doctorService = doctorService;
    }

    public boolean hasUserId(OAuth2AuthenticationToken authentication, Long userId) {
        User u = userService.findByExternalId(authentication.getName());
        log.debug("In hasUserId");
        if (u != null) {
            return u.getId().equals(userId);
        }
        return false;
    }

    public boolean hasDoctorId(OAuth2AuthenticationToken authentication, Long userId) {//Authentication authentication1
        Doctor d = doctorService.findByExternalId(authentication.getName());
        log.debug("In hasUserId");

        if (d != null) {
            return d.getId().equals(userId);
        }

        return false;
    }

    public boolean hasUserId(AnonymousAuthenticationToken authentication, Long userId) {
        return false;
    }

    public boolean hasDoctorId(AnonymousAuthenticationToken authentication, Long userId) {
        return false;
    }

    public boolean hasNoUserId(OAuth2AuthenticationToken authentication) {
        User u = userService.findByExternalId(authentication.getName());
        Doctor d = doctorService.findByExternalId(authentication.getName());
        log.debug("In hasNoUserId");
        if (u != null || d != null) {
            return false;
        }

        return true;

    }

    public boolean hasNoUserId(AnonymousAuthenticationToken authentication) {
        return true;
    }
}
