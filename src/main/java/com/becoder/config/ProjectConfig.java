package com.becoder.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ProjectConfig {
	
	// @Bean  A bean is just an object that Spring creates and manages for you. Spring keeps all beans in a container 
	//(the ApplicationContext), and any class that needs one gets it from there. You don’t write new yourself.
	
	
	
	//A ready-made class from a library so no need to define anyware like auditoraware its just interace and we have to
	//implements it in some class
	@Bean
	public ModelMapper mapper()
	{
		return new ModelMapper();
	}
	
	
	//You never call getCurrentAuditor() yourself. Someone else calls it, and that someone is Spring’s audit listener.
	//The only way that listener can find your class is if it is registered in the Spring container, and @Bean is how 
	//you register it.
	
	@Bean
	public AuditorAware<Integer> auditAware()
	{
		return new AuditAwareConfig();
	}

}
