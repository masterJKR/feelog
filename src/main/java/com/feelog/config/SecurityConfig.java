package com.feelog.config;

import com.feelog.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfig {

    @Autowired
    private MemberService memberService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache =
                new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);
        // 페이지 이동 하는데  로그인해야 이용할수 있는 경우  로그인페이지로이동한다,
        //  로그인 성공하면 이전에 방문 했던 페이지로 이동할수 있는 기능을 제공한다.


        http.requestCache( rq -> rq.requestCache(requestCache))
                .authorizeHttpRequests(
                        ar -> ar
                                .requestMatchers("/","/image/**","/css/**","/javascript/**")
                                .permitAll()// 매처에 작성된 주소에 대해 모두 허용
                                .requestMatchers("/error","/signUp","/favicon.ico","/post","/post/detail").permitAll()//  /error주소 허용
                                .requestMatchers("/admin/**").hasRole("ADMIN")// 관리자페이지는 권한이 ADMIN만허용
                                .anyRequest().authenticated() // 나머지 모든 주소요청은 로그인해야한다.
                )
                .formLogin(
                        form -> form
                                .loginPage("/signIn") // 커스텀 로그인 페이지 주소
                                .defaultSuccessUrl("/") // 로그인 성공하면 갈곳
                                .usernameParameter("email")// 로그인할때 계정명 input태그의 name
                                .failureUrl("/signIn/error")// 로그인 실패시 어디로?
                                .permitAll()//로그인과 관련된 주소들을 허용
                )
                .logout(
                        out -> out
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                                .logoutSuccessUrl("/") // 로그아웃 성공 하면 어디로?
                                .invalidateHttpSession(true) // 로그아웃되면 모든 세션 제거
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
