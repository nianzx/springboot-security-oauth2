package cn.nianzx.common.resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来解决认证过的用户访问无权限资源时的异常(没有权限)
 * 需要抛出AccessDeniedException异常才会被该类捕获
 * Created by nianzx on 2019/1/16.
 */
public class MyOAuth2AccessDeniedHandler extends OAuth2AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpServletResponse.SC_FORBIDDEN);//403
        map.put("msg", "没有权限");
        map.put("data", authException.getMessage());
        map.put("success", false);
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
