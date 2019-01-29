package cn.nianzx.common.authorization;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

/**
 * Http验证Adapter
 * Created by nianzx on 2019/1/29.
 */
@Configuration
//springboot1.5.x 的WebSecurityConfigurerAdapter优先级比ResourceServerConfigurerAdapter高，里面的HttpSecurity会覆盖掉ResourceServerConfigurerAdapter里的
//这边加上@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)把WebSecurityConfigurerAdapter优先级降低，springboot2.0不需要这个
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;


    /**
     * 验证处理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置登录user验证处理器。
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignoring是完全绕过了spring security的所有filter，相当于不走spring security
        //web.ignoring().antMatchers("/js/**", "/css/**", "/img/**");
    }
}
