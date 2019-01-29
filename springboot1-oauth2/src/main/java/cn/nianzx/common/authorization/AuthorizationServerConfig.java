package cn.nianzx.common.authorization;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器配置
 * Created by nianzx on 2019/1/29.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private MyUserDetailsService userService;


    //用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你可以把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置一个客户端
        clients
                .inMemory()//存储在内存中
                .withClient("clientId")//用来标识客户的Id(必须)
                .authorizedGrantTypes("password", "refresh_token")//允许授权类型，默认为空，可选：密码模式授权模式（password），授权码模式（authorization_code），简化授权模式（implicit），客户端模式（client_credentials），（刷新令牌）refresh_token
                .scopes("all")//允许授权范围，如果为空（默认）的话，那么客户端拥有全部的访问范围
                .authorities("read", "write")//此客户端可以使用的权限
                .accessTokenValiditySeconds(7200)//access_token有效期：2小时
                .refreshTokenValiditySeconds(604800)//refresh_token有效期：一周
                .secret("nianzx");


    }

    //用来配置令牌端点(Token Endpoint)的安全约束，创建ClientCredentialsTokenEndpointFilter核心过滤器
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                //开启allowFormAuthenticationForClients后，会在BasicAuthenticationFilter之前添加clientCredentialsTokenEndpointFilter,使用ClientDetailsUserDetailsService来进行client端登录的验证
                //意思即：请求中带有client_id以及client_secret时，调用ClientDetailsUserDetailsService来校验，没有就走默认的BasicAuthenticationFilter
                .allowFormAuthenticationForClients();
    }

    //配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)//token存放在redis中
                .authenticationManager(authenticationManager)//只有password模式才会有效
                .userDetailsService(userService); //用来请求token和刷新token(refresh_token)时验证账号密码

        //endpoints.pathMapping()可以自定义请求的路径，例如修改/oauth/token等

        //这边还可以选择使用endpoints.tokenGranter()来进行扩展 当使用这个时，其他所有设置都会失效 此时意味着授权将会交由你来完全掌控


        //拿到增强器链，自定义token信息
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(new RedisTokenEnhancer());
        enhancerChain.setTokenEnhancers(enhancers);
        endpoints.tokenEnhancer(enhancerChain);

    }
}