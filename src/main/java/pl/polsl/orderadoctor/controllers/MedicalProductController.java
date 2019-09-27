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
import pl.polsl.orderadoctor.dto.MedicalProductDto;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.MedicalProductService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MedicalProductController {

    private final DoctorService doctorService;
    private final MedicalProductService medicalProductService;

    @GetMapping("/doctor/{doctorId}/products")
    public String listProducts(@PathVariable Long doctorId, Model model) {
        log.debug("Getting speciality list from doctor id: " + doctorId);
        model.addAttribute("doctor", doctorService.findDtoById(doctorId));

        return "login/logged/doctor/product/list";
    }

    @GetMapping("doctor/{doctorId}/product/{id}/show")
    public String showDoctorMedicalProduct(@PathVariable Long doctorId,
                                           @PathVariable Long id, Model model) {
        DoctorDto doctorDto = doctorService.findDtoById(doctorId);
        model.addAttribute("doctor", doctorDto);

        MedicalProductDto medicalProductDto = medicalProductService.findDtoById(id);

        model.addAttribute("product", medicalProductDto);
        return "login/logged/doctor/product/show";
    }

    @GetMapping("doctor/{doctorId}/product/new")
    public String addDoctorSpeciality(@PathVariable Long doctorId, Model model) {

        DoctorDto doctorDto = doctorService.findDtoById(doctorId);
        model.addAttribute("doctor", doctorDto);

        MedicalProductDto medicalProductDto = new MedicalProductDto();

        model.addAttribute("product", medicalProductDto);

        return "login/logged/doctor/product/productform";
    }

    @GetMapping("doctor/{doctorId}/product/{id}/update")
    public String updateDoctorSpeciality(@PathVariable Long doctorId, @PathVariable Long id, Model model) {

        DoctorDto doctorDto = doctorService.findDtoById(doctorId);
        model.addAttribute("doctor", doctorDto);

        model.addAttribute("product", medicalProductService.findDtoById(id));

        return "login/logged/doctor/product/productform";
    }


    @PostMapping("doctor/{doctorId}/product")
    public String saveSpeciality(@Valid @ModelAttribute("product") MedicalProductDto dto, BindingResult bindingResult, Model model, @PathVariable Long doctorId) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctor", doctorService.findDtoById(doctorId));
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "login/logged/doctor/product/productform";
        }

        MedicalProductDto saveProduct = medicalProductService.saveDto(dto, doctorId);


        log.debug("added medical product id: " + saveProduct.getId() + "to doctor id: " + doctorId);

        return "redirect:/doctor/" + doctorId + "/products";
    }


    @GetMapping("doctor/{doctorId}/product/{id}/delete")
    public String deleteById(@PathVariable Long doctorId, @PathVariable Long id) {

        log.debug("Deleting speciality id: " + id);

        doctorService.deleteMedicalProductById(doctorId, id);
        return "redirect:/doctor/" + doctorId + "/products";
    }
}
