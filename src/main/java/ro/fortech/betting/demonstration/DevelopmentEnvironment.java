package ro.fortech.betting.demonstration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("development")
public class DevelopmentEnvironment implements EnvironmentProvider {

	@Override
	public String provideEnvironmentName() {

		return "Development Environment";
	}

}
