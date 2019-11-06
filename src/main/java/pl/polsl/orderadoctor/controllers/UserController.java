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
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.model.VisitState;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.SpecialityService;
import pl.polsl.orderadoctor.services.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final SpecialityService specialityService;


    @GetMapping("user/{id}/logged")
    public String userLoggedIn(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        userService.endPastVisits(id);

        user.getVisits().sort((v1, v2)-> {
                    if (v1.getVisitState() != v2.getVisitState()) {
                        if (v2.getVisitState() == VisitState.CREATED) {
                            return 1;
                        } else if (v1.getVisitState() == VisitState.CREATED) {
                            return -1;
                        } else if (v1.getVisitState() == VisitState.ENDED) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        return v1.getDateFrom().compareTo(v2.getDateFrom());
                    }
                }
        );

        model.addAttribute("user", user);

        model.addAttribute("specialities", specialityService.findAllSpecialitiesDto());

        return "login/logged/logged_in_user";
    }

    @GetMapping("user/{id}/show")
    public String userShow(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "login/logged/user/show";
    }

    @GetMapping("user/{id}/update")
    public String updateUser(@PathVariable Long id, Model model) {

        UserDto user = userService.findDtoById(id);

        model.addAttribute("user", user);

        return "login/logged/user/userform";
    }

    @PostMapping("user/{id}/update")
    public String update(@Valid @ModelAttribute("user") UserDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/logged/user/userform";
        }

        UserDto saveDto = userService.saveDto(dto);

        return "redirect:/user/" + saveDto.getId() + "/show";

    }

    @GetMapping("user/{id}/find_doctor")
    public String findDoctor(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        model.addAttribute("user", user);

        model.addAttribute("specialities", specialityService.findAllSpecialitiesDto());

        return "login/logged/user/find_doctor";
    }

}
