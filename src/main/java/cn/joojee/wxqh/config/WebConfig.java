package cn.joojee.wxqh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.joojee.usercerter.oauth.service.ServiceProvider;

import cn.joojee.wxqh.interceptor.CommonInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public HessianProxyFactoryBean joojeeOauthService() {
		HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
		hessianProxyFactoryBean.setServiceUrl("http://service.joojee.cn/service/oauth");
		hessianProxyFactoryBean.setServiceInterface(ServiceProvider.class);
		return hessianProxyFactoryBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE",
				"OPTIONS", "TRACE");
	}
}
