package com.service.auth.serviceauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
@Order(2)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;//注入自定义userdetailservice(com.service.auth.serviceauth.service.impl.UserDetailServiceImpl)
	
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();//兼容多种密码的加密方式
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers().anyRequest()//所有请求都加入HttpSecurity(多个HttpSecurity过滤)
//                .and().authorizeRequests().antMatchers("/oauth/**").permitAll();//开放/oauth/开头的所有请求
    	//不拦截/oauth/**，/login/**，/logout/**(requestMatchers用于但需要过滤多个HttpSecurity的情况)
        http.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")//使HttpSecurity接收以"/login/","/oauth/","/logout/"开头请求。
                .and().authorizeRequests().antMatchers("/oauth/**").authenticated()
                .and().formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());//注入自定义的UserDetailsService，采用BCrypt加密
    }
    
    //通过重载该方法，可配置Spring Security的Filter链（HTTP请求安全处理）
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
