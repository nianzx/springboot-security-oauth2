package cn.nianzx.common.authorization;

import cn.nianzx.domain.User;
import cn.nianzx.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


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
        User user = userMapper.getUserList(username);
        return new MyUserDetails(user == null ? new User() : user);//这边不知道是配置有问题还是就是这样，1.5.4传null过去不会有事，2.1.2必须不为null
    }
}
