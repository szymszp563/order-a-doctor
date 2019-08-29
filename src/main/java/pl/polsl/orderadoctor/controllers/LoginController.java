package pl.polsl.orderadoctor.controllers;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public LoginController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model){

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);

        if(type!=ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])){

        }

        return "login/oauth_login";
    }

}
