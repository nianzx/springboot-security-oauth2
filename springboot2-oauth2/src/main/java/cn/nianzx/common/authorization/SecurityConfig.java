package cn.nianzx.common.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * Http验证Adapter
 * Created by nianzx on 2019/1/29.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;


    /**
     * 验证处理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置登录user验证处理器。springboot2.0需要配置passwordEncoder，否则报错
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignoring是完全绕过了spring security的所有filter，相当于不走spring security
        //web.ignoring().antMatchers("/js/**", "/css/**", "/img/**");
    }

    @Resource
    private MyAuthenticationProvider myAuthenticationProvider;

    /*
     * springboot2.0需要配置这个来支持password模式
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authentication -> myAuthenticationProvider.authenticate(authentication);
    }
}
