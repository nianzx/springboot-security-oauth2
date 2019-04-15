package cn.nianzx.common.authorization;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 具体的用户密码验证方法
 * Created by nianzx on 2019/1/29.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private MyUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        MyUserDetails user = (MyUserDetails) userDetailsService.loadUserByUsername(username);

        //验证密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (password == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        //过期状态
        if (!user.isAccountNonExpired()) {
            throw new BadCredentialsException("账户已过期");
        }

        //锁定状态
        if (!user.isAccountNonLocked()) {
            throw new BadCredentialsException("该用户已锁定");
        }

        //禁用状态
        if (!user.isEnabled()) {
            throw new BadCredentialsException("账号已禁用");
        }

        if (!user.isCredentialsNonExpired()) {
            throw new BadCredentialsException("密码已过期");
        }


        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        //这边登录后可以设置各种相关信息，比如登录日志，用户缓存等

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
