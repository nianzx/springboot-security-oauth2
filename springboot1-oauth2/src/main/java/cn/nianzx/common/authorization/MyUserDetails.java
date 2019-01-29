package cn.nianzx.common.authorization;

import cn.nianzx.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义UserDetails，需要按实际情况定制
 * Created by nianzx on 2019/1/29.
 */
public class MyUserDetails implements UserDetails {

    private final User user;

    MyUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //添加权限，需要自定义,这里为了演示方便，写死管理员和用户两个权限
        List<SimpleGrantedAuthority> authorisList = new ArrayList<>();
        if (user != null && user.getUserCode().equals("admin")) {
            authorisList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorisList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorisList;
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     *
     * @return true 未过期 false 已过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNotExpired();
    }

    /**
     * 用户是否未锁定,锁定的用户无法进行身份验证
     *
     * @return true 未锁定 false 已锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNotLocked();
    }

    /**
     * 用户凭据(密码)是否未过期,过期的凭据防止认证
     *
     * @return true 未过期 false 已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    /**
     * 是否被禁用,禁用的用户不能身份验证
     *
     * @return true 未禁用 false 已禁用
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public Integer getAge() {
        return user.getAge();
    }

    public String getSex() {
        return user.getSex();
    }
}
