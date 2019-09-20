package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.MedicalProductService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MedicalProductController {

    private final DoctorService doctorService;
    private final MedicalProductService medicalProductService;

    @GetMapping("/doctor/{doctorId}/products")
    public String listProducts(@PathVariable Long doctorId, Model model){
        log.debug("Getting speciality list from doctor id: "+ doctorId);
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));

        return "login/logged/doctor/product/list";
    }
}
