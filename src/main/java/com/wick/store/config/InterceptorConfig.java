package com.wick.store.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mclt2017
 * @date 2021年06月24日 17:45
 * 拦截器配置文件
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${file-save-path}")
    private String fileSavePath;
    /**
     * 注册拦截器
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry
                .addInterceptor(new JwtInterceptor())
                .addPathPatterns("/api/**") //拦截的地址
                .excludePathPatterns("/user/**"); //不需要拦截的地址，如登录接口
    }

    /**
     * 图片做虚拟映射处理
     * @param registry
     */
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       //重写方法
       //修改tomcat 虚拟映射
       registry.addResourceHandler("/images/**")
               .addResourceLocations("file:" + fileSavePath);//定义图片存放路径
   }
    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                //如果有多个路径需要跨域，只需要将跨域路径放入数组中
                //String []  allowDomain={"http://**","http://*","http://*"};
                //.allowedOrigins(allowDomain)//多url跨域
                .allowedOrigins("*")//设置允许跨域请求的域名
                .allowCredentials(false)//是否允许证书 不写默认开启
                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE","PATCH")
                .allowedHeaders("*")//设置允许的方法
                .maxAge(3600);//跨域允许时间
    }

}
