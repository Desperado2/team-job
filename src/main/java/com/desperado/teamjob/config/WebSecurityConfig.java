package com.desperado.teamjob.config;

import com.desperado.teamjob.dao.UserRepository;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.security.SecurityUser;
import com.desperado.teamjob.service.LoginRecordService;
import com.desperado.teamjob.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private LoginRecordService loginRecordService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().
                antMatchers("/global/**","/druid/**").permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler())
                .and().logout().logoutSuccessUrl("/login")
                .permitAll().invalidateHttpSession(true)
        .deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler())
        .and().sessionManagement().maximumSessions(10).expiredUrl("/login")
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                try {
                    SecurityUser user = (SecurityUser) authentication.getPrincipal();
                    LOGGER.info("USER : " + user.getUsername() +"LOGOUT SUCCESS !");

                }catch (Exception e){
                    LOGGER.info("LOGOUT EXCEPTION , e : " + e.getMessage());
                }
                httpServletResponse.sendRedirect("login");
            }
        };
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler(){
        return new SavedRequestAwareAuthenticationSuccessHandler(){
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
                User userDetails = (User) authentication.getPrincipal();
                String ipAddress = IpUtils.getIpAddr(request);
                String browserType = request.getHeader("Browser-Type");
                LOGGER.info("USER : " + userDetails.getEmail() + " LOGIN SUCCESS !  ");
                LOGGER.info("USER IP : " + IpUtils.getIpAddr(request));
                LOGGER.info("USER Browser Type : " + request.getHeader("Browser-Type"));
                ExecutorService service = Executors.newFixedThreadPool(5);
                service.execute(() -> loginRecordService.addRecord(userDetails.getEmail(),ipAddress,browserType));
                response.setStatus(200);
                response.getWriter().write("success");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Autowired
            private UserRepository userRepository;
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                com.desperado.teamjob.domain.User user = userRepository.selectUserByEmail(email);
                if(user == null){
                    throw new UsernameNotFoundException("Username " + email + " not found");
                }
                return new SecurityUser(user);
            }
        };
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
