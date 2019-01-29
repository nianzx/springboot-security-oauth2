package cn.nianzx.common.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义oauth2的异常处理器
 * 捕获401异常进行自定义处理（token过期）
 * Created by nianzx on 2019/1/9.
 */
public class MyOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
    private final static Logger logger = LoggerFactory.getLogger(MyOAuth2AuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //解析异常，如果是401则处理
        try {
            ResponseEntity<?> result = exceptionTranslator.translate(authException);
            if (result.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                logger.debug("进入401异常");
                Throwable cause = authException.getCause();
                if (cause instanceof InvalidTokenException) {//无效token
                    ajaxMessage(response, "无效token");
                } else {//未登录
                    ajaxMessage(response, "当前未登录");
                }
            } else {
                //如果不是401异常，则以默认的方法继续处理其他异常
                super.commence(request, response, authException);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ajaxMessage(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write("{\"code\":401,\"msg\":\"" + msg + "\"}");
    }
}
