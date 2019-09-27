package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.services.DoctorService;
import pl.polsl.orderadoctor.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final UserService userService;
    private final DoctorService doctorService;

    @GetMapping("user/{id}/image")
    public String showUserUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findDtoById(id));

        return "login/logged/user/imageuploadform";
    }

    @PostMapping("user/{id}/image")
    public String handleUserImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {

        userService.saveImageFile(id, file);

        return "redirect:/user/" + id + "/show";
    }

    @GetMapping("doctor/{id}/image")
    public String showDoctorUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.findDtoById(id));

        return "login/logged/doctor/imageuploadform";
    }

    @PostMapping("doctor/{id}/image")
    public String handleDoctorImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {

        doctorService.saveImageFile(id, file);

        return "redirect:/doctor/" + id + "/show";
    }

    @GetMapping("user/{id}/userimage")
    public void renderUserImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        User user = userService.findById(Long.valueOf(id));

        if (user.getImage() != null) {
            byte[] byteArray = new byte[user.getImage().length];
            int i = 0;

            for (Byte wrappedByte : user.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @GetMapping("doctor/{id}/doctorimage")
    public void renderDoctorImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        Doctor doctor = doctorService.findById(Long.valueOf(id));

        if (doctor.getImage() != null) {
            byte[] byteArray = new byte[doctor.getImage().length];
            int i = 0;

            for (Byte wrappedByte : doctor.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
