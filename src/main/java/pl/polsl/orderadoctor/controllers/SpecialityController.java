package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.orderadoctor.dto.DoctorDto;
import pl.polsl.orderadoctor.dto.SpecialityDto;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.SpecialityService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class SpecialityController {

    private final DoctorService doctorService;
    private final SpecialityService specialityService;

    @GetMapping("doctor/{doctorId}/specialities")
    public String listSpecialities(@PathVariable Long doctorId, Model model) {
        log.debug("Getting speciality list for doctor id: " + doctorId);
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));

        return "login/logged/doctor/speciality/list";
    }

    @GetMapping("doctor/{doctorId}/speciality/new")
    public String addDoctorSpeciality(@PathVariable Long doctorId, Model model) {

        DoctorDto doctorDto = doctorService.findDtoById(doctorId);
        model.addAttribute("doctor", doctorDto);

        SpecialityDto specialityDto = new SpecialityDto();

        model.addAttribute("speciality", specialityDto);

        model.addAttribute("specialities", specialityService.findAllSpecialitiesDto());

        return "login/logged/doctor/speciality/specialityform";
    }

    @GetMapping("doctor/{doctorId}/speciality/{id}/update")
    public String updateDoctorSpeciality(@PathVariable Long doctorId, @PathVariable Long id, Model model) {

        DoctorDto doctorDto = doctorService.findDtoById(doctorId);
        model.addAttribute("doctor", doctorDto);

        model.addAttribute("speciality", specialityService.findDtoById(id));

        model.addAttribute("specialities", specialityService.findAllSpecialitiesDto());

        return "login/logged/doctor/speciality/specialityform";
    }


    @PostMapping("doctor/{doctorId}/speciality")
    public String saveSpeciality(@ModelAttribute("speciality") SpecialityDto dto, @PathVariable Long doctorId) {
        SpecialityDto saveSpeciality = specialityService.saveDto(dto, doctorId);


        log.debug("added speciality id: " + saveSpeciality.getId() + "to doctor id: " + doctorId);

        return "redirect:/doctor/" + doctorId + "/specialities";
    }

    @GetMapping("doctor/{doctorId}/speciality/{id}/delete")
    public String deleteById(@PathVariable Long doctorId, @PathVariable Long id) {

        log.debug("Deleting speciality id: " + id);

        //todo something better because not working

        doctorService.deleteSpecialityById(doctorId, id);
        return "redirect:/doctor/" + doctorId + "/specialities";
    }

}
