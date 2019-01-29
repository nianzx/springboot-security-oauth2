package cn.nianzx.common.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * oauth2资源服务器配置
 * Created by nianzx on 2019/1/29.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.stateless(true);
        resources.authenticationEntryPoint(new MyOAuth2AuthenticationEntryPoint());//自定义oauth2的异常处理器，捕获401异常（token过期）
        resources.accessDeniedHandler(new MyOAuth2AccessDeniedHandler());//自定义oauth2的异常处理器，捕获403异常（没有权限）
        resources.tokenExtractor(new MyTokenExtractor());//自定义带着token请求时 token的获取方式（从cookie中获取）
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
            /*
             * ObjectPostProcessor官方文档中称为后处理配置对象，可以用来修改或者替代通过Java方式配置创建的对象实例
             * 这边通过配置修改了FilterSecurityInterceptor默认的accessDecisionManager和securityMetadataSource
            */
        http.authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                fsi.setAccessDecisionManager(accessDecisionManager());
                fsi.setSecurityMetadataSource(securityMetadataSource());
                return fsi;
            }
        }).and().csrf().disable().headers().frameOptions().disable();

    }

    @Bean
    public MyAccessDecisionManager accessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new MyInvocationSecurityMetadataSourceService();
    }
}
