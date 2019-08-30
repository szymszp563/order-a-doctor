package pl.polsl.orderadoctor.controllers;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserService userService;
    private final DoctorService doctorService;
    private static String authorizationBaseUri = "oauth2/authorization";
    private Map<String, String> oauth2Urls = new HashMap<>();

    public LoginController(ClientRegistrationRepository clientRegistrationRepository, UserService userService, DoctorService doctorService) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.userService = userService;
        this.doctorService = doctorService;
    }

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model){

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);

        if(type!=ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])){
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration->oauth2Urls.put
                (registration.getClientName(), authorizationBaseUri + "/" + registration.getRegistrationId()));

        model.addAttribute("urls", oauth2Urls);

        return "oauth_login";
    }

    @GetMapping("/register_or_logged_in")
    public String registerOrLoggedInPage(OAuth2AuthenticationToken curr){

        String externalId = curr.getName();
        String accountTypeName = curr.getAuthorizedClientRegistrationId();
        AccountType accountType = null;

        switch (accountTypeName){
            case "facebook":
                accountType = AccountType.FACEBOOK;
                break;
            case "google":
                accountType = AccountType.GOOGLE;
                break;
        }


        User ifUser = userService.findByExternalIdAndAccountType("4", accountType);
        Doctor ifDoctor = doctorService.findByExternalIdAndAccountType("1", accountType);

        if(ifUser!=null){
            return "redirect:/logged/" + ifUser.getId() + "/show";
        }

        if(ifDoctor!=null){
            return "redirect:/logged/" + ifDoctor.getId() + "/show";
        }

        return "redirect:/register";

    }

    @GetMapping("/register")
    public String register(){
        return "login/register";
    }





    @GetMapping("logged/{id}/show")
    public String loggedIn(@PathVariable Long id){
        return "login/logged_in";
    }

}
