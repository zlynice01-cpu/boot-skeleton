package com.zly.skeleton.common.security.config;

import com.zly.skeleton.cache.util.RedisCache;
import com.zly.skeleton.common.security.handler.AuthFailureHandler;
import com.zly.skeleton.common.security.handler.AuthSuccessHandler;
import com.zly.skeleton.common.security.handler.AuthenticationEntryPointHandler;
import com.zly.skeleton.common.security.handler.LogoutSuccessfulHandler;
import com.zly.skeleton.common.security.provider.MyAuthenticationProvider;
import com.zly.skeleton.common.security.service.impl.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:34
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig
{

    private final MyUserDetailsService userDetailsService;

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
    MyAuthenticationProvider myAuthenticationProvider() {
        MyAuthenticationProvider provider = new MyAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 基于 Spring Session 的会话注册表：会话数据存于 Redis，
     * 因此 maximumSessions 在多实例下同样生效
     */
    @Bean
    public SessionRegistry sessionRegistry(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SessionRegistry sessionRegistry) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        // 同一账号只允许一个有效会话
                        .maximumSessions(1)
                        .sessionRegistry(sessionRegistry)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/doLogin", "/error", "/captcha/create").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/doLogin")
                        .successHandler(new AuthSuccessHandler())
                        .failureHandler(new AuthFailureHandler()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new LogoutSuccessfulHandler())
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(new AuthenticationEntryPointHandler()));
        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("admin").implies("user")
                .build();
    }

}
