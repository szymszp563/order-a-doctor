package pl.polsl.orderadoctor.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model, HttpServletRequest request, HttpServletResponse response) {

        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);

        log.debug("In Index Controller");

        return "index";
    }
}
