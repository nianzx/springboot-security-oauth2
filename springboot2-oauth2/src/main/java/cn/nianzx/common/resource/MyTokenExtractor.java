package cn.nianzx.common.resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 重写TokenExtractor 继承默认实现类BearerTokenExtractor
 * 实现token获取方式从cookie中获取
 * BearerTokenExtractor默认从三个地方获取
 * 1、在header获取
 * 2、在url后面获取
 * 3、在form表单里获取
 * 增加第四种从cookie中获取
 * Created by nianzx on 2019/1/12.
 */
public class MyTokenExtractor extends BearerTokenExtractor {
    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = extractToken(request);
        if (tokenValue == null) {
            //从cookie中获取
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if ("access_token".equals(cookie.getName())) {
                        tokenValue = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if (tokenValue != null) {
            return new PreAuthenticatedAuthenticationToken(tokenValue, "");
        }
        return null;
    }
}
