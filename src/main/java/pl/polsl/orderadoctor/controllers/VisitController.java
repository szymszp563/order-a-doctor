package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.orderadoctor.dto.VisitDto;
import pl.polsl.orderadoctor.model.VisitState;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.MedicalProductService;
import pl.polsl.orderadoctor.services.SpecialityService;
import pl.polsl.orderadoctor.services.UserService;
import pl.polsl.orderadoctor.services.VisitService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class VisitController {

    private final UserService userService;
    private final DoctorService doctorService;
    private final SpecialityService specialityService;
    private final MedicalProductService medicalProductService;
    private final VisitService visitService;

    @GetMapping("/user/{userId}/show/{doctorId}/{productId}/visit")
    public String saveOrUpdatevisit(@PathVariable Long userId, @PathVariable Long doctorId, @PathVariable Long productId, Model model) {
        model.addAttribute("user", userService.findDtoById(userId));
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));
        model.addAttribute("product", medicalProductService.findDtoById(productId));

        VisitDto visitDto = new VisitDto();
        visitDto.setDoctorId(doctorId);
        visitDto.setMedicalProductIds(new Long[]{productId});
        visitDto.setVisitState(VisitState.CREATED);
        model.addAttribute("visit", visitDto);

        return "login/logged/user/find/product/visit/visitform";
    }

    @PostMapping("/user/{userId}/visit")
    public String saveVisitByUser(@ModelAttribute("visit") VisitDto dto, @PathVariable Long userId) {
        VisitDto saveVisit = visitService.saveDto(dto, userId);


        log.debug("added visit id: " + saveVisit.getId() + " to user id: " + userId + " and to doctor id: " + dto.getDoctorId());

        return "redirect:/user/" + userId + "/show/" + dto.getDoctorId();
    }
}
