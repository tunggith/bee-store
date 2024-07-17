package com.example.duanbanao.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, passwords, enabled FROM nhan_vien WHERE username = ?")
                .authoritiesByUsernameQuery("select nv.username, cv.ten_chuc_vu from nhan_vien nv join chuc_vu cv on nv.id_chuc_vu = cv.id_chuc_vu WHERE nv.username = ?");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/bee-store/trang-chu", "/css/**", "/script/**", "/img/**", "https://maxcdn.bootstrapcdn.com/**").permitAll() // Các URL này sẽ không yêu cầu xác thực
                                .anyRequest().authenticated() // Các URL khác yêu cầu xác thực
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/bee-store/login") // URL của trang đăng nhập tùy chỉnh
                                .permitAll() // Cho phép truy cập đến trang đăng nhập
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // URL để đăng xuất
                                .permitAll() // Cho phép truy cập đến trang đăng xuất
                );

        return http.build();
    }
}
