package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class LoginController {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserService userService;
    private final DoctorService doctorService;
    private static String authorizationBaseUri = "oauth2/authorization";
    private Map<String, String> oauth2Urls = new HashMap<>();


    @GetMapping("/oauth_login")
    public String getLoginPage(Model model) {

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);

        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration -> oauth2Urls.put
                (registration.getClientName(), authorizationBaseUri + "/" + registration.getRegistrationId()));

        model.addAttribute("urls", oauth2Urls);

        return "oauth_login";
    }

    @GetMapping("/register_or_logged_in")
    public String registerOrLoggedInPage(OAuth2AuthenticationToken curr, Model model) {

        String externalId = curr.getName();
        String accountTypeName = curr.getAuthorizedClientRegistrationId();
        AccountType accountType = null;

        byte[] unwrappedImg = null;

        switch (accountTypeName) {
            case "facebook":
                accountType = AccountType.FACEBOOK;
                break;
            case "google":
                accountType = AccountType.GOOGLE;
                break;
        }

        User ifUser = userService.findByExternalIdAndAccountType(externalId, accountType);
        Doctor ifDoctor = doctorService.findByExternalIdAndAccountType(externalId, accountType);

        if (ifUser != null) {
            model.addAttribute("user", ifUser);
            return "redirect:/user/" + ifUser.getId() + "/show";
        }

        if (ifDoctor != null) {
            model.addAttribute("doctor", ifDoctor);
            return "redirect:/doctor/" + ifDoctor.getId() + "/show";
        }

        return "redirect:/register";

    }

    @GetMapping("/register")
    public String register() {
        return "login/register";
    }

    @GetMapping("/register/doctor")
    public String registerDoctor(OAuth2AuthenticationToken curr, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        String externalId = curr.getName();
        String accountTypeName = curr.getAuthorizedClientRegistrationId();
        AccountType accountType = null;

        byte[] unwrappedImg = null;

        switch (accountTypeName) {
            case "facebook":
                accountType = AccountType.FACEBOOK;
                String url = "https://graph.facebook.com/" + externalId + "/picture?type=large";
                unwrappedImg = restTemplate.getForObject(url, byte[].class);
                break;
            case "google":
                String urlG = curr.getPrincipal().getAttributes().get("picture").toString();
                unwrappedImg = restTemplate.getForObject(urlG, byte[].class);
                accountType = AccountType.GOOGLE;
                break;
        }

        Byte[] img = new Byte[unwrappedImg.length];

        int i = 0;

        for (byte b : unwrappedImg) {
            img[i++] = b;
        }

        String name = curr.getPrincipal().getAttributes().get("name").toString();
        String firstName = null;
        String lastName = null;
        if (name != null) {
            String nameTab[] = name.split(" ");
            firstName = name.split(" ")[0];
            if (nameTab[1] != null) {
                lastName = name.split(" ")[1];
            }
        }

        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setAccountType(accountType);
        doctorDto.setExternalId(externalId);
        doctorDto.setFirstName(firstName);
        doctorDto.setLastName(lastName);
        doctorDto.setImage(img);
        model.addAttribute("doctor", doctorDto);

        return "login/registerDoctor";
    }

    @GetMapping("/register/user")
    public String registerUser(OAuth2AuthenticationToken curr, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String externalId = curr.getName();
        String accountTypeName = curr.getAuthorizedClientRegistrationId();
        AccountType accountType = null;

        byte[] unwrappedImg = null;

        switch (accountTypeName) {
            case "facebook":
                accountType = AccountType.FACEBOOK;
                String url = "https://graph.facebook.com/" + externalId + "/picture?type=large";
                unwrappedImg = restTemplate.getForObject(url, byte[].class);
                break;
            case "google":
                String urlG = curr.getPrincipal().getAttributes().get("picture").toString();
                unwrappedImg = restTemplate.getForObject(urlG, byte[].class);
                accountType = AccountType.GOOGLE;
                break;
        }

        Byte[] img = new Byte[unwrappedImg.length];

        int i = 0;

        for (byte b : unwrappedImg) {
            img[i++] = b;
        }

        String name = curr.getPrincipal().getAttributes().get("name").toString();
        String firstName = null;
        String lastName = null;
        if (name != null) {
            String nameTab[] = name.split(" ");
            firstName = name.split(" ")[0];
            if (nameTab[1] != null) {
                lastName = name.split(" ")[1];
            }
        }

        UserDto userDto = new UserDto();
        userDto.setAccountType(accountType);
        userDto.setExternalId(externalId);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setImage(img);
        model.addAttribute("user", userDto);

        return "login/registerUser";
    }

    @PostMapping("/register/user")
    public String saveOrUpdateUser(@Valid @ModelAttribute("user") UserDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/registerUser";
        }

        UserDto saveDto = userService.saveDto(dto);

        return "redirect:/user/" + saveDto.getId() + "/logged";

    }

    @PostMapping("/register/doctor")
    public String saveOrUpdateDoctor(@Valid @ModelAttribute("doctor") DoctorDto dto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/registerDoctor";
        }
        dto.setAverageGrade(0D);
        DoctorDto saveDto = doctorService.saveDto(dto);

        return "redirect:/doctor/" + saveDto.getId() + "/logged";

    }

}
