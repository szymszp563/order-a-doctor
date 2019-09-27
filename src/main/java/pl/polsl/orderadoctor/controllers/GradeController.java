package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.dto.GradeDto;
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.GradeService;
import pl.polsl.orderadoctor.services.UserService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GradeController {

    private final GradeService gradeService;
    private final UserService userService;
    private final DoctorService doctorService;

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

    @GetMapping("user/{userId}/grade/new")
    public String addNewGrade(@PathVariable Long userId, Model model) {

        UserDto userDto = userService.findDtoById(userId);
        model.addAttribute("user", userDto);

        GradeDto gradeDto = new GradeDto();

        model.addAttribute("grade", gradeDto);

        return "login/logged/user/grade/gradeform";
    }

    @GetMapping("user/{userId}/grade/{id}/update")
    public String updateGrade(@PathVariable Long userId, @PathVariable Long id, Model model) {

        UserDto userDto = userService.findDtoById(userId);
        model.addAttribute("user", userDto);

        model.addAttribute("grade", gradeService.findDtoById(id));

        return "login/logged/user/grade/gradeform";
    }

    /**
     * POST NOT HERE
     */
//    @PostMapping("user/{userId}/grade")
//    public String saveSpeciality(@Valid @ModelAttribute("grade") GradeDto dto, BindingResult bindingResult, Model model, @PathVariable Long userId) {
//
//        if (bindingResult.hasErrors()) {
//            UserDto userDto = userService.findDtoById(userId);
//            model.addAttribute("user", userDto);
//            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
//            return "login/logged/doctor/product/productform";
//        }
//
//        GradeDto saveGrade = gradeService.saveDto(dto, userId);
//
//
//        log.debug("added medical product id: " + saveGrade.getId() + "to doctor id: " + doctorId);
//
//        return "redirect:/doctor/" + doctorId + "/products";
//    }


    @GetMapping("user/{userId}/grade/{id}/delete")
    public String deleteById(@PathVariable Long userId, @PathVariable Long id) {

        log.debug("Deleting speciality id: " + id);

        doctorService.deleteGrade(id);
        return "redirect:/user/" + userId + "/grades";
    }

}
