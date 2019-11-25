package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.dto.VisitDto;
import pl.polsl.orderadoctor.model.Visit;
import pl.polsl.orderadoctor.model.VisitState;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.MedicalProductService;
import pl.polsl.orderadoctor.services.SpecialityService;
import pl.polsl.orderadoctor.services.UserService;
import pl.polsl.orderadoctor.services.VisitService;

import javax.validation.Valid;

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

        DoctorDto doctor = doctorService.findDtoById(doctorId);

        String hourFrom = doctor.getWorkingFrom();
        String hourTo = doctor.getWorkingTo();
        int hF = Integer.valueOf(hourFrom.split(":")[0]);
        int hT = Integer.valueOf(hourTo.split(":")[0]);

        model.addAttribute("from", hF);
        model.addAttribute("to", hT);

        return "login/logged/user/find/product/visit/visitform";
    }

    @GetMapping("user/{id}/visit/{visitId}/delete")
    public String deleteVisitByUser(@PathVariable Long visitId, @PathVariable Long id){
        log.debug("deleting visit id: " + visitId);

        visitService.deleteVitById(visitId);

        return "redirect:/user/" + id + "/logged";
    }

    @PostMapping("/user/{userId}/visit")
    public String saveVisitByUser(@Valid @ModelAttribute("visit") VisitDto dto, BindingResult bindingResult, @PathVariable Long userId, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.findDtoById(userId));
            model.addAttribute("doctor", doctorService.findDtoById(dto.getDoctorId()));
            model.addAttribute("product", medicalProductService.findDtoById(dto.getMedicalProductIds()[0]));
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/logged/user/find/product/visit/visitform";
        }

        VisitDto saveVisit = visitService.saveDto(dto, userId);


        log.debug("added visit id: " + saveVisit.getId() + " to user id: " + userId + " and to doctor id: " + dto.getDoctorId());

        return "redirect:/user/" + userId + "/show/" + dto.getDoctorId();
    }

    @PostMapping("/doctor/{doctorId}/visit")
    public String saveVisitByDoctor(@Valid @ModelAttribute("visit") VisitDto dto, BindingResult bindingResult, @PathVariable Long doctorId, Model model) {

        if (bindingResult.hasErrors()) {
            DoctorDto doctor = doctorService.findDtoById(doctorId);
            model.addAttribute("doctor", doctor);
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/logged/doctor/visitform";
        }

        VisitDto saveVisit = visitService.saveDto(dto, visitService.findById(dto.getId()).getUser().getId());

        log.debug("added visit id: " + saveVisit.getId() + " to doctor id: " + doctorId + " and to doctor id: " + dto.getDoctorId());

        return "redirect:/doctor/" + doctorId + "/logged";
    }

    @GetMapping("doctor/{id}/visit/{visitId}/delete")
    public String deleteVisitByDoctor(@PathVariable Long visitId, @PathVariable Long id){
        log.debug("deleting visit id: " + visitId);

        visitService.deleteVitById(visitId);

        return "redirect:/doctor/" + id + "/logged";
    }


    @GetMapping("doctor/{doctorId}/visit/{visitId}/updateVisitStatus")
    public String updateVisitStatus(@PathVariable Long doctorId, @PathVariable Long visitId){

        visitService.confirmVisit(visitId);

        return "redirect:/doctor/" + doctorId + "/logged";
    }

    @GetMapping("/doctor/{doctorId}/show/{visitId}")
    public String saveOrUpdatevisitByDoctor(@PathVariable Long visitId, @PathVariable Long doctorId, Model model) {
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));

        VisitDto visitDto = visitService.findDtoById(visitId);
        visitDto.setDoctorId(doctorId);
        visitDto.setMedicalProductIds(new Long[]{visitService.findById(visitId).getMedicalProducts().get(0).getId()});
        visitDto.setVisitState(VisitState.CONFIRMED);
        Visit visit = visitService.findById(visitId);
        visitDto.setStartingDate(visit.getDateFrom().toLocalDate().toString());
        visitDto.setHour(visit.getDateFrom().toLocalTime().toString());
        model.addAttribute("visit", visitDto);

        DoctorDto doctor = doctorService.findDtoById(doctorId);

        String hourFrom = doctor.getWorkingFrom();
        String hourTo = doctor.getWorkingTo();
        int hF = Integer.valueOf(hourFrom.split(":")[0]);
        int hT = Integer.valueOf(hourTo.split(":")[0]);

        model.addAttribute("from", hF);
        model.addAttribute("to", hT);


        return "login/logged/doctor/visitform";
    }


    @GetMapping("/user/{userId}/visit/{visitId}/updateVisit/{doctorId}")
    public String saveOrUpdatevisitByUser(@PathVariable Long visitId, @PathVariable Long userId, @PathVariable Long doctorId, Model model) {
        model.addAttribute("user", userService.findDtoById(userId));

        VisitDto visitDto = visitService.findDtoById(visitId);
        visitDto.setDoctorId(doctorId);
        visitDto.setMedicalProductIds(new Long[]{visitService.findById(visitId).getMedicalProducts().get(0).getId()});
        visitDto.setVisitState(VisitState.CREATED);
        Visit visit = visitService.findById(visitId);
        visitDto.setStartingDate(visit.getDateFrom().toLocalDate().toString());
        visitDto.setHour(visit.getDateFrom().toLocalTime().toString());
        model.addAttribute("visit", visitDto);

        DoctorDto doctor = doctorService.findDtoById(doctorId);

        String hourFrom = doctor.getWorkingFrom();
        String hourTo = doctor.getWorkingTo();
        int hF = Integer.valueOf(hourFrom.split(":")[0]);
        int hT = Integer.valueOf(hourTo.split(":")[0]);

        model.addAttribute("from", hF);
        model.addAttribute("to", hT);

        return "login/logged/user/visitform";
    }

    @PostMapping("/user/{userId}/visit/update")
    public String updateVisitByUser(@Valid @ModelAttribute("visit") VisitDto dto, BindingResult bindingResult, @PathVariable Long userId, Model model) {

        if (bindingResult.hasErrors()) {
            UserDto user = userService.findDtoById(userId);
            model.addAttribute("user", user);
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/logged/user/visitform";
        }

        VisitDto saveVisit = visitService.saveDto(dto, userId);

        log.debug("added visit id: " + saveVisit.getId() + " to user id: " + userId + " and to doctor id: " + dto.getDoctorId());

        return "redirect:/user/" + userId + "/logged";
    }
}
