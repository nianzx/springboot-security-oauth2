package cn.nianzx.common.resource;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 投票决策器
 * MyInvocationSecurityMetadataSource -> MyAccessDecisionManager
 * Created by nianzx on 2019/1/29.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 决策方法： 判定是否拥有权限的决策方法,如果方法执行完毕没有抛出异常,则说明可以放行, 否则抛出异常 AccessDeniedException
     * 这边的实现中，当需要多个权限时只要有一个符合则校验通过，即或的关系，想要并的关系只需要修改这里的逻辑即可。
     *
     * @param authentication   认证过的票据Authentication，确定了谁正在访问资源，包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities。
     * @param object           被访问的资源object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，指示了此次访问所需要的权限
     * @throws AccessDeniedException               AccessDeniedException异常
     * @throws InsufficientAuthenticationException InsufficientAuthenticationException异常
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }
        ConfigAttribute c;
        String needRole;
        for (ConfigAttribute configAttribute : configAttributes) {
            c = configAttribute;
            needRole = c.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("当前访问没有权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        //return FilterInvocation.class.isAssignableFrom(clazz);
        return true;
    }
}
