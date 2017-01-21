package com.sck;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sck.common.json.ISO8601JsonDateDeserializer;
import com.sck.common.mybatis.DefaultParameterInjector;
import com.sck.common.mybatis.ParameterInjectInterceptor;


@EnableAutoConfiguration
@ComponentScan(basePackages={ "com.sck" })
@MapperScan("com.sck.modules")	
final public class Bootstrap 
{
	final public static void main(String[] args) 
	{
		new SpringApplication(Bootstrap.class).run(args);
	}
	
	@Primary
    @Bean
	public ObjectMapper objectMapper()
	{
    	ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Object.class, new ISO8601JsonDateDeserializer());
		mapper.registerModule(module);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
		return mapper;
	}
	
	@Bean
	public Interceptor parameterInjectInterceptor()
	{
		ParameterInjectInterceptor interceptor = new ParameterInjectInterceptor();
		interceptor.setAlternateParameterName("p");
		interceptor.setInjector(new DefaultParameterInjector());
		return interceptor;
	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher()
	{
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
}
