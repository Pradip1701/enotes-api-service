package com.becoder.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
//This class customizes the Swagger page: it sets the title and contact info, lists the servers, 
//and adds the "Authorize" button for JWT tokens.


@Configuration
public class SwaggerConfig {
	
	    @Bean
	    public OpenAPI openAPI() {
	    	
	    	OpenAPI openApi=new OpenAPI();
	    	
	    	//now open implemetaion of OpenApi then there we have to set infos
	    	Info info=new Info();
	    	info.setTitle("Enotes api");
	    	info.setDescription("enotes api");
	    	info.setVersion("1.0.0");
	    	info.setTermsOfService("http://becoder.com");
	    	info.setContact(new Contact().email("bhosaleraje01@gmail.com")
	    			                     .name("pradip")
	    			                     .url("http://becoder.com/contact"));
	    	info.setLicense(new License().name("enotes by pradip").url("http://becoder.com"));
	    	
	        
	    	//now open implemetaion of OpenApi then there we have to set servers
	    	List<Server> serverList=List.of(new Server().description("Dev").url("http://localhost:8080"),
	    	new Server().description("Prod").url("http://localhost:8081"),
	    	new Server().description("Test").url("http://localhost:8082"));
	    	
	    	//now open implemetaion of OpenApi then there we have to set componenet like bearer xxxxx.yyy.zzz
	    	SecurityScheme securityScheme=new SecurityScheme().name("Authorization")
	    			                                                    .scheme("bearer")
	    			                                                    .type(Type.HTTP)
	    			                                                    .bearerFormat("JWT")
	    			                                                    .in(In.HEADER);
	    	
	    	Components component=new Components().addSecuritySchemes("Token", securityScheme);
	    	
	        openApi.setInfo(info);
	    	openApi.setServers(serverList);
	    	openApi.setComponents(component);
	    	openApi.setSecurity(List.of(new SecurityRequirement().addList("Token")));
	    	
	    	return openApi;
       }
}
