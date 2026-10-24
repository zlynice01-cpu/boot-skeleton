package com.zly.skeleton.common.security.config;

import com.zly.skeleton.common.security.filter.LoginFilter;
import com.zly.skeleton.common.security.handler.AuthFailureHandler;
import com.zly.skeleton.common.security.handler.AuthSuccessHandler;
import com.zly.skeleton.common.security.handler.AuthenticationEntryPointHandler;
import com.zly.skeleton.common.security.handler.LogoutSuccessfulHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:34
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new MyBCryptPasswordEncoder();
    }

    // 认证管理器，后面JWT过滤器会用到
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginFilter loginFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // 有状态会话：Security 上下文会写入 HttpSession，下一个请求自动还原
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/test/login/**", "/demo/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler(new AuthSuccessHandler())
                        .failureHandler(new AuthFailureHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new LogoutSuccessfulHandler())
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(new AuthenticationEntryPointHandler()))
                // 自定义登录过滤器接管 /demo/login（支持 JSON 与表单两种入参）
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("admin").implies("user")
                .build();
    }

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new AuthSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new AuthFailureHandler());
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setFilterProcessesUrl("/demo/login");
        return loginFilter;
    }


}
