package com.myportfolio.main.configuration;


import com.myportfolio.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationFailureHandler getFailureHandler(){
        return new UserAuthenticationFailureHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 이미지 리소스 폴더 보안 활성화
        web.ignoring().antMatchers("/imgs/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/asserts/**");
        web.ignoring().antMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable();



        http.authorizeRequests()
                .antMatchers("/",
                        "/user/login",
                        "/user/register",
                        "/user/findPassword",
                        "/user/resetPassword")
                .permitAll();
        super.configure(http);
        http.formLogin()
                .loginPage("/user/login")
                .failureHandler(getFailureHandler())
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(getPasswordEncoder());

        super.configure(auth);
    }


}
