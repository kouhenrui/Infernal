package com.example.infernal.config;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/8
 * \* Time: 17:11
 * \* Description:
 * \
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 registration 拦截器
        InterceptorRegistration registration = registry.addInterceptor(new SystemInterceptor());

        // 拦截所有的路径
        registration.addPathPatterns("/**");

        // 添加不拦截路径 /api/user/login 是登录的请求, /api/user/register 注册的请求
        registration.excludePathPatterns(
                "/user/login",
                "/user/register",
                "/manager/login",
                // html 静态资源
                "/**/*.html",
                "/static/*",

                "/file/*",
                // js 静态资源
                "/**/*.js",
                // css 静态资源
                "/**/*.css",
                "/websocket"
        );
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}

