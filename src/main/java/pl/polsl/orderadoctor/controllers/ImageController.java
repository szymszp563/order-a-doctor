package pl.polsl.orderadoctor.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("user/{id}/userimage")
    public void renderUserImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        User user = userService.findById(Long.valueOf(id));

        if (user.getImage() != null) {
            byte[] byteArray = new byte[user.getImage().length];
            int i = 0;

            for (Byte wrappedByte : user.getImage()){
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

            for (Byte wrappedByte : doctor.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
