package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.UserService;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final UserService userService;

    @GetMapping("doctor/{id}/show")
    public String doctorLoggedIn(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.findById(id);

        model.addAttribute("doctor", doctor);

        return "login/logged/doctor/show";
    }
}
