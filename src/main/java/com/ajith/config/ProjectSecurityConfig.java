package com.ajith.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {

//    @Bean
//    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//
//                .authorizeHttpRequests()
//                .antMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated()
//
//                .antMatchers("/contact","/notices").permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin();
//
//        return http.build();
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http.csrf(csrf->csrf.disable())

         .authorizeHttpRequests((request)-> request
//                request.requestMatchers("/myAccount","/myBalance","/myLoans","/myCards","/user")
//                        .authenticated()
//                 request.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                         .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
//                         .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                         .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                         .requestMatchers("/myAccount").hasRole("USER")
                         .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                         .requestMatchers("/myLoans").hasRole("USER")
                         .requestMatchers("/myCards").hasRole("USER")
                         .requestMatchers("/user").authenticated()

                        .requestMatchers("/notices","/contact","/register").permitAll()
        )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("Ajith")
//                .password("1234")
//                .authorities("admin")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("akp")
//                .password("test")
//                .authorities("read")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //authentication using Jdbc
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource)
//    {
//        /**
//         * implementing our own custom implementation so , commenting it out , to remove the ambiguity
//         */
//        return new JdbcUserDetailsManager(dataSource);
//    }

}

