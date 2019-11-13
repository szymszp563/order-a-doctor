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
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.VisitState;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.GradeService;
import pl.polsl.orderadoctor.services.MedicalProductService;
import pl.polsl.orderadoctor.services.SpecialityService;
import pl.polsl.orderadoctor.services.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Log4j2
public class DoctorController {

    private final DoctorService doctorService;
    private final UserService userService;
    private final MedicalProductService medicalProductService;
    private final SpecialityService specialityService;
    private final GradeService gradeService;

    @GetMapping("doctor/{id}/logged")
    public String doctorLoggedIn(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.findById(id);

        doctorService.endPastVisits(id);

        doctor.getVisits().sort((v1, v2)-> {
                    if (v1.getVisitState() != v2.getVisitState()) {
                        if (v2.getVisitState() == VisitState.CREATED) {
                            return 1;
                        } else if (v1.getVisitState() == VisitState.CREATED) {
                            return -1;
                        } else if (v1.getVisitState() == VisitState.ENDED) {
                            if(v2.getVisitState()==VisitState.RATED){
                                return v1.getDateFrom().compareTo(v2.getDateFrom());
                            }
                            return 1;
                        }else if(v1.getVisitState()==VisitState.RATED){
                            if(v2.getVisitState()==VisitState.ENDED){
                                return v1.getDateFrom().compareTo(v2.getDateFrom());
                            }
                            return 1;

                        } else {
                            return -1;
                        }
                    } else {
                        return v1.getDateFrom().compareTo(v2.getDateFrom());
                    }
                }
        );

        model.addAttribute("doctor", doctor);

        return "login/logged/logged_in_doctor";
    }

    @GetMapping("doctor/{id}/show")
    public String doctorShow(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.findById(id);

        model.addAttribute("doctor", doctor);

        return "login/logged/doctor/show";
    }

    @GetMapping("doctor/{id}/update")
    public String updateDoctor(@PathVariable Long id, Model model) {

        DoctorDto doctor = doctorService.findDtoById(id);

        model.addAttribute("doctor", doctor);

        return "login/logged/doctor/doctorform";
    }

    @PostMapping("doctor/{id}/update")
    public String update(@Valid @ModelAttribute("doctor") DoctorDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/logged/doctor/doctorform";
        }

        DoctorDto saveDto = doctorService.saveDto(dto);

        return "redirect:/doctor/" + saveDto.getId() + "/show";

    }
}
