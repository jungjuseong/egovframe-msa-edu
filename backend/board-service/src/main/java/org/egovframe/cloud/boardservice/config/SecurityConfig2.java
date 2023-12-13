package org.egovframe.cloud.boardservice.config;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.servlet.config.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * org.egovframe.cloud.boardservice.config.SecurityConfig
 * <p>
 * Spring Security Config 클래스
 * AuthenticationFilter 를 추가하고 토큰으로 setAuthentication 인증처리를 한다
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig2 {

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    /**
     * 스프링 시큐리티 설정
     *
     * @param http
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 사용하기 때문에 세션은 비활성화
                .and()
                .addFilter(getAuthenticationFilter(http));
        
        return http.build();
    }
    /**
     * 토큰에 담긴 정보로 Authentication 정보를 설정하여 jpa audit 처리에 사용된다.
     * 이 처리를 하지 않으면 AnonymousAuthenticationToken 으로 처리된다.
     *
     * @return
     * @throws Exception
     */
    @Bean
    AuthenticationFilter getAuthenticationFilter(HttpSecurity http) throws Exception {
    	AuthenticationManager m = http.getSharedObject(AuthenticationManagerBuilder.class).build();
        return new AuthenticationFilter(m, TOKEN_SECRET);
    }
   
}



