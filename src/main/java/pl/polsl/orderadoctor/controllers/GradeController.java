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
import pl.polsl.orderadoctor.dto.GradeDto;
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.GradeService;
import pl.polsl.orderadoctor.services.UserService;
import pl.polsl.orderadoctor.services.VisitService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GradeController {

    private final GradeService gradeService;
    private final UserService userService;
    private final DoctorService doctorService;
    private final VisitService visitService;

    @GetMapping("/user/{userId}/grades")
    public String listGrades(@PathVariable Long userId, Model model) {
        log.debug("Getting grade list from user id: " + userId);
        model.addAttribute("user", userService.findDtoById(userId));

        return "login/logged/user/grade/list";
    }

    @GetMapping("user/{userId}/grade/{id}/show")
    public String showUserGrade(@PathVariable Long userId,
                                           @PathVariable Long id, Model model) {
        UserDto userDto = userService.findDtoById(userId);
        model.addAttribute("user", userDto);

        GradeDto gradeDto = gradeService.findDtoById(id);

        model.addAttribute("grade", gradeDto);
        return "login/logged/user/grade/show";
    }

    @GetMapping("user/{userId}/grade/{visitId}/new")
    public String addNewGrade(@PathVariable Long userId, @PathVariable Long visitId, Model model) {

        UserDto userDto = userService.findDtoById(userId);
        model.addAttribute("user", userDto);

        GradeDto gradeDto = new GradeDto();

        gradeDto.setVisitId(visitId);

        gradeDto.setDoctorId(visitService.findById(visitId).getDoctor().getId());

        model.addAttribute("grade", gradeDto);

        //FOR TESTING!!
//        model.addAttribute("doctors", doctorService.findAllDoctorsDto());

        return "login/logged/user/grade/gradeform";
    }

    @GetMapping("user/{userId}/grade/{id}/update")
    public String updateGrade(@PathVariable Long userId, @PathVariable Long id, Model model) {

        UserDto userDto = userService.findDtoById(userId);
        model.addAttribute("user", userDto);

        model.addAttribute("grade", gradeService.findDtoById(id));

        //FOR TESTING!!
//        model.addAttribute("doctors", doctorService.findAllDoctorsDto());

        return "login/logged/user/grade/gradeform";
    }

    @PostMapping("user/{userId}/grade")
    public String saveGrade(@Valid @ModelAttribute("grade") GradeDto dto, BindingResult bindingResult,
                           Model model, @PathVariable Long userId) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.findAllDoctorsDto());//TESTING
            UserDto userDto = userService.findDtoById(userId);
            model.addAttribute("user", userDto);
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/logged/user/grade/gradeform";
        }

        GradeDto saveGrade = gradeService.saveDto(dto, userId);

        log.debug("posted grade id: " + saveGrade.getId() + "to user id: " + userId);

        return "redirect:/user/" + userId + "/grades";
    }


    @GetMapping("user/{userId}/grade/{id}/delete")
    public String deleteById(@PathVariable Long userId, @PathVariable Long id) {

        log.debug("Deleting speciality id: " + id);

        userService.deleteGrade(id);
        return "redirect:/user/" + userId + "/grades";
    }

}
