package com.example.infernal.config;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/8
 * \* Time: 17:09
 * \* Description:
 * \
 */
import com.example.infernal.util.jwtUtil.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SystemInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthenticationException {
        // 从 request 的 header 中获得 token 值
        String token = request.getHeader("authorization");
        if (token == null || token.equals(""))throw new AuthenticationException();
        String sub = JWTUtils.validateToken(token);
        if (sub == null || sub.equals("")) throw new AuthenticationException();
        // 更新 token 有效时间
//        if (JWTUtils.isNeedUpdate(token)) {
//            // 过期就创建新的 token 给前端
//            String newToken = JWTUtils.getToken(sub);
//            response.setHeader(JWTUtils.USER_LOGIN_TOKEN, newToken);
//        }
        return true;
    }
    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}

