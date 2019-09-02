package pl.polsl.orderadoctor.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final UserService userService;

    public ImageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("userr/{id}/userimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
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
}
