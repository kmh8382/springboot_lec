package com.min.app07.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Autowired
  private LoginFailureHandler loginFailureHandler;
  
  /*
   * SecurityFilterChain 
   * Spring Security 의 기본 애플리케이션 보안 구성을 담당합니다.
   * 사용자가 SecurityFilterChain 빈을 등록하면  Spring Security 의 보안 구성을 비활성화하고
   * 사용자가 직덥 보안 구성을 정의할 수 있습니다.
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    
    // URL에 따라 서버 리소스에 접근 가능한 권한을 부여합니다.
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/", "/user/signup", "/auth/login").permitAll();  // requestMatchers 에 등록된 모든 경로는 누구나 접근 가능합니다.
    });
    
    // 로그인 설정을 저리합니다.
    http.formLogin(login -> {
      login.loginPage("/auth/login");             // "/auth/login" 요청 시 로그인 페이지로 이동합니다.
      login.usernameParameter("userId");          // 로그인 페이지의 유저 아이디가 가진 파라미터 이름을 지정합니다.
      login.passwordParameter("userPassword");    // 로그인 페이지의 유저 비밀번호가 가진 파라미터 이름을 지정합니다.
      login.defaultSuccessUrl("/", true);         // 로그인 성공하면 언제나(true) "/"로 이동합니다.
      login.failureHandler(loginFailureHandler);  // 로그인 실패하면 LoginFailureHandler 가 동작합니다.
    });
    
    return http.build();
    
  }
  
  /*
   * PasswordEncoder
   * BCrypt 방식의 암호화 알고리즘을 지원하는 BCryptPasswordEncoder 빈능 반호나합니다.
   * BCrypt 암호화 알고리즘
   *   - 가장 많이 사용하는 비밀번호 해싱 알고리즘 중 하나
   *   - 높은 보안, 호환성(데이터베이스에 저장하기 쉬운 데이터 생성), 신뢰성을 가짐 
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  /*
   * WebSecuritycustomizer
   * static 디렉터리 하위에 있는 모든 정적 리소스들에 대한 요청은 보안에서 제외합니다.
   */
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
  
  
  
  
  
}
