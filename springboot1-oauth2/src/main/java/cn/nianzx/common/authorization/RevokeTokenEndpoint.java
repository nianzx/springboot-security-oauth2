package cn.nianzx.common.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 清除token，实现注销
 * Created by nianzx on 2019/1/29.
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Resource
    private ConsumerTokenServices consumerTokenServices;

    private final static Logger logger = LoggerFactory.getLogger(RevokeTokenEndpoint.class);

    //调用时必须有access_token，client_id，client_secret三个参数，否则会报401，原因在AuthorizationServerConfig配置的allowFormAuthenticationForClients()
    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public String revokeToken(HttpServletRequest request) {
        String access_token = extractToken(request);
        if (consumerTokenServices.revokeToken(access_token)) {
            return "{\"code\":200,\"msg\":\"操作成功\",\"data\":\"注销成功，已成功清除token\"}";
        } else {
            return "{\"code\":400,\"msg\":\"操作失败\",\"data\":\"注销失败\"}";
        }
    }

    //从请求中获取token（表单和url）
    private String extractToken(HttpServletRequest request) {
        // first check the header...
        String token = extractHeaderToken(request);
        // bearer type allows a request parameter as well
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
            if (token == null) {
                logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
            } else {
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, OAuth2AccessToken.BEARER_TYPE);
            }
        }

        return token;
    }


    /**
     * Extract the OAuth bearer token from a header.从Header中获取token
     *
     * @param request The request.
     * @return The token, or null if no OAuth authorization header was supplied.
     */
    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
                // Add this here for the auth details later. Would be better to change the signature of this method.
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
                        value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }
}
