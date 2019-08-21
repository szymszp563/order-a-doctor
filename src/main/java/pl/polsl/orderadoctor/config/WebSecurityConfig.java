package pl.polsl.orderadoctor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/hello2").authenticated()
                .antMatchers("/*").permitAll()
                .antMatchers("/h2_console/**").permitAll()
                .and()
                .oauth2Login();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
