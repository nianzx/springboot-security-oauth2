package cn.nianzx.common.authorization;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 自定义UserDetails
 * Created by nianzx on 2019/1/29.
 */
public class MyUserDetails implements UserDetails {

    private final User user;

    MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 账户是否未过期,过期无法验证
     *
     * @return true 未过期 false 已过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    /**
     * 用户是否未锁定,锁定的用户无法进行身份验证
     *
     * @return true 未锁定 false 已锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    /**
     * 用户凭据(密码)是否未过期,过期的凭据防止认证
     *
     * @return true 未过期 false 已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    /**
     * 是否被禁用,禁用的用户不能身份验证
     *
     * @return true 未禁用 false 已禁用
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}
