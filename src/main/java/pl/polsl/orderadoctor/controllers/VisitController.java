package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.dto.VisitDto;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.MedicalProductService;
import pl.polsl.orderadoctor.services.SpecialityService;
import pl.polsl.orderadoctor.services.UserService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class VisitController {

    private final UserService userService;
    private final DoctorService doctorService;
    private final SpecialityService specialityService;
    private final MedicalProductService medicalProductService;

    @GetMapping("/user/{userId}/find/{id}/show/{doctorId}/{productId}/visit")
    public String saveOrUpdatevisit(@PathVariable Long userId, @PathVariable Long doctorId, @PathVariable Long id, @PathVariable Long productId, Model model){
        model.addAttribute("user", userService.findDtoById(userId));
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));
        model.addAttribute("speciality", specialityService.findDtoById(id));
        model.addAttribute("product", medicalProductService.findDtoById(productId));

        VisitDto visitDto = new VisitDto();
        model.addAttribute("visit", visitDto);

        return "login/logged/user/find/product/visit/visitform";
    }
}
