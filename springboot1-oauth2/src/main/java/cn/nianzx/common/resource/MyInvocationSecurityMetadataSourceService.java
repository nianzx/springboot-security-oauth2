package cn.nianzx.common.resource;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * MyInvocationSecurityMetadataSourceService
 * Created by nianzx on 2019/1/29.
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    private HashMap<Object, Collection<ConfigAttribute>> map = null;


    /**
     * 加载权限表中所有权限，这里可以自定义从数据库获取，这边为了演示方便，直接写死
     */
    private void loadResourceDefine() {
        map = new HashMap<>();

        ConfigAttribute cfg = new SecurityConfig("ROLE_ADMIN");
        ConfigAttribute cfg1 = new SecurityConfig("ROLE_USER");
        map.put("/home", new ArrayList<ConfigAttribute>() {{
            add(cfg);
            add(cfg1);
        }});
        map.put("/userHome", new ArrayList<ConfigAttribute>() {{
            add(cfg1);
        }});
        map.put("/adminHome", new ArrayList<ConfigAttribute>() {{
            add(cfg);
        }});
    }

    /*
    * 此方法判断用户请求的url，是否跟上述的map的key值匹配，如果匹配，则把该url所需要的权限（value值）传给MyAccessDecisionManager.decide方法进行下一步判断
    * 如果不匹配，则看是要return null 放行，还是直接抛出AccessDeniedException异常报没有权限（即默认白名单还是黑名单的问题）
    * */

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        AntPathRequestMatcher matcher;
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        //加载权限的map
        loadResourceDefine();

        String resUrl;
        for (Object s : map.keySet()) {
            resUrl = s.toString();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }

        //默认黑名单
        throw new AccessDeniedException("没有权限");
        //return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
