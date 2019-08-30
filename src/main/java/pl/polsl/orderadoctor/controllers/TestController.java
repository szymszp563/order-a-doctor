package pl.polsl.orderadoctor.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/hello2")
    public String sayHello2(OAuth2AuthenticationToken curr) {

        String hello = "HELLO :)";
        Map userAttributes = curr.getPrincipal().getAttributes();
        System.out.println("==================================");
        userAttributes.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("==================================");
        curr.getPrincipal().getAuthorities().stream().forEach(System.out::println);
        System.out.println("==================================");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://graph.facebook.com/2624763627574870/picture?type=large";
        System.out.println(Arrays.toString(restTemplate.getForObject(url, byte[].class)));
        System.out.println(curr.getPrincipal().getAttributes().get("id"));

        System.out.println("curr.getAuthorizedClientRegistrationId() = " + curr.getAuthorizedClientRegistrationId());
        System.out.println("curr.getName() = " + curr.getName());

        return "Hello " + userAttributes.get("name");
    }
}
