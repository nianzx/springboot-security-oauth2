package cn.nianzx.common.authorization;

import cn.nianzx.domain.User;
import cn.nianzx.mapper.UserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 登录验证帐号密码用
 * Created by nianzx on 2019/1/29.
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户和角色
        User userInfo = userMapper.getUserList(username);
        if (userInfo == null) {
            throw new BadCredentialsException("用户名不存在");
        }
        // 构造用户权限列表，这边为了演示方便，直接写死
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userInfo.getUserCode().equals("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 返回带有用户权限信息的User（从左到右参数，用户名，密码，账户未禁用，账户未过期，密码未过期，账号未锁定，权限列表）
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userInfo.getUserCode(), userInfo.getPwd(), true, true, true, true, authorities);
        return new MyUserDetails(user);
    }
}
