package com.becoder.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

//so for getloggedin user id we have AuditorAware interface and it have getCueentAuditor method so we implement this
//interface and override its method and set id there.



//AuditorAware: Spring itself asks the container
//Nobody writes @Autowired for it in your code. Spring Data’s AuditingEntityListener looks it up internally:


//listener -> container: "give me the AuditorAware"
//-> gets the AuditAwareConfig object
//-> calls getCurrentAuditor()
public class AuditAwareConfig implements AuditorAware{

	@Override
	public Optional<Integer> getCurrentAuditor() {
		return Optional.of(11);
	}

}
