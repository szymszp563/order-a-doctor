package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.services.DoctorService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class SpecialityController {

    private final DoctorService doctorService;

    @GetMapping("doctor/{doctorId}/specialities")
    public String listSpecialities(@PathVariable Long doctorId, Model model){
        log.debug("Getting speciality list for doctor id: " + doctorId);
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));

        return "login/logged/doctor/speciality/list";
    }
}
