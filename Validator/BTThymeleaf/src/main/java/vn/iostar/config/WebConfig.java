package vn.iostar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import vn.iostar.interceptor.RoleInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
	@Autowired
	private RoleInterceptor roleInterceptor;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///D:/2025-2026/HK1/WEBDEV/LTW/springimg/");
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(roleInterceptor)
         .addPathPatterns("/admin/**", "/user/**")
         .excludePathPatterns("/login", "/logout",
                              "/css/**", "/js/**", "/uploads/**");; // áp dụng cho 2 nhóm URL
    }
}
