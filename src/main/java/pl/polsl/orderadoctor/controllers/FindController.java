package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.MedicalProductService;
import pl.polsl.orderadoctor.services.SpecialityService;
import pl.polsl.orderadoctor.services.UserService;

@Controller
@RequiredArgsConstructor
public class FindController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final SpecialityService specialityService;
    private final MedicalProductService medicalProductService;

    @GetMapping("/user/{userId}/find/{id}/list")
    public String searchDoctors(@PathVariable Long userId, @PathVariable Long id, Model model){

        model.addAttribute("user", userService.findDtoById(userId));
        model.addAttribute("doctors", doctorService.findAllDtoBySpeciality(id));
        model.addAttribute("speciality", specialityService.findDtoById(id));

        return "login/logged/user/find/list";
    }

    @GetMapping("/user/{userId}/find/{id}/{city}/list")
    public String searchDoctorsWithCity(@PathVariable Long userId, @PathVariable Long id, @PathVariable String city, Model model){



        model.addAttribute("user", userService.findDtoById(userId));
        model.addAttribute("doctors", doctorService.findAllBySpecialityAndCity(id, city));
        model.addAttribute("speciality", specialityService.findDtoById(id));

        return "login/logged/user/find/list";
    }

    @GetMapping("/user/{userId}/show/{doctorId}")
    public String showFoundDoctor(@PathVariable Long userId, @PathVariable Long doctorId, Model model){
        model.addAttribute("user", userService.findDtoById(userId));
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));
        model.addAttribute("intGrade", doctorService.findById(doctorId).getIntGrade());

        return "login/logged/user/find/show";
    }

    @GetMapping("/user/{userId}/show/{doctorId}/{productId}")
    public String selectProduct(@PathVariable Long userId, @PathVariable Long doctorId, @PathVariable Long productId, Model model){
        model.addAttribute("user", userService.findDtoById(userId));
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));
        model.addAttribute("product", medicalProductService.findDtoById(productId));

        return "login/logged/user/find/product/show";
    }
}
