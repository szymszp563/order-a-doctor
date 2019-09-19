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

import java.util.List;

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

        List<SpecialityDto> dtoList = specialityService.findAllSpecialitiesDto();

        model.addAttribute("specialities", dtoList);

        return "login/logged/doctor/speciality/specialityform";
    }

    @PostMapping("doctor/{doctorId}/speciality")
    public String saveSpeciality(@ModelAttribute("doctor") DoctorDto dto) {
        DoctorDto savedDoctor = doctorService.saveDto(dto);

        log.debug("saved doctor id:" + savedDoctor.getId());

        return "redirect:/doctor/" + savedDoctor.getId() + "/specialities";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable Long doctorId, @PathVariable Long id) {

        log.debug("Deleting speciality id: " + id);


        doctorService.deleteSpecialityById(doctorId, id);
        return "redirect:/doctor/" + doctorId + "/specialities";
    }

}
