package pl.polsl.orderadoctor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableWebSecurity
//@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/hello2").authenticated()
                .antMatchers("/register_or_logged_in").authenticated()
                .antMatchers("/oauth_login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/h2_console/**").permitAll()
                .and()
                .oauth2Login().loginPage("/oauth_login");

        http.csrf().disable();
        http.headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers("/user/{userId}/**")
                    .access("@userSecurity.hasUserId(authentication, #userId)")
                .antMatchers("/doctor/{userId}/**")
                    .access("@userSecurity.hasUserId(authentication, #userId)")
                .antMatchers("/register/**")
                    .access("@userSecurity.hasNoUserId(authentication)");

    }
}
