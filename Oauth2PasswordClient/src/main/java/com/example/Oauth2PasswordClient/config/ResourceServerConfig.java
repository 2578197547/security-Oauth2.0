package com.example.Oauth2PasswordClient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer //这个类表明了此应用是OAuth2 的资源服务器，此处主要指定了受资源服务器保护的资源链接
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
 
/*	//密码登陆模式
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//禁用了 csrf 功能
                .authorizeRequests()//限定签名成功的请求
                .antMatchers("/decision/**","/govern/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/test/**").authenticated()//必须认证过后才可以访问
                .anyRequest().permitAll()//其他没有限定的请求，允许随意访问
                .and().anonymous();//对于没有配置权限的其他请求允许匿名访问
    }*/
	
	//客户端模式
	@Override
	public void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()//禁用了 csrf 功能
	            .authorizeRequests()//限定签名成功的请求
	            .antMatchers("/test/**","/admin/**").authenticated()//签名成功后可访问
	            //.antMatchers("/admin/login","/oauth/**").permitAll()
	            .anyRequest().permitAll()//其他没有限定的请求，允许访问
	            .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
	            .and().formLogin()//使用 spring security 默认登录页面
	            .and().httpBasic();//启用http 基础验证
	 }
 
}


