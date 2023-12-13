package org.egovframe.cloud.userservice.config;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.userservice.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.egovframe.cloud.common.config.GlobalConstant.SECURITY_PERMITALL_ANTPATTERNS;

/**
 * org.egovframe.cloud.userservice.SecurityConfig
 * <p>
 * Spring Security Config 클래스
 * AuthenticationFilter를 추가하고 로그인 인증처리를 한다
 *
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig2 {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private AuthenticationManager authenticationManager = null;
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        
        if (authenticationManager == null) {
        	authenticationManager = builder.build();
        }
        
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager , tokenProvider, userService);
    	    	
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 사용하기 때문에 세션은 비활성화
            .and()
                .authorizeRequests()
                .antMatchers(SECURITY_PERMITALL_ANTPATTERNS).permitAll()
                .anyRequest().access("@authorizationService.isAuthorization(request, authentication)") // 호출 시 권한 인가 데이터 확인
            .and()
            	.authenticationManager(authenticationManager)
            	.addFilter(authenticationFilter)
                .logout()
                .logoutSuccessUrl("/");
                
        return http.build();
    }
}



