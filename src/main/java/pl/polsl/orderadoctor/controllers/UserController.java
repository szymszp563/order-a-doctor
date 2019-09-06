package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DoctorService doctorService;


    @GetMapping("user/{id}/logged")
    public String userLoggedIn(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "login/logged/logged_in_user";
    }

    @GetMapping("user/{id}/show")
    public String userShow(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "login/logged/user/show";
    }

    @GetMapping("user/{id}/find_doctor")
    public String findDoctor(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "login/logged/user/find_doctor";
    }

}
