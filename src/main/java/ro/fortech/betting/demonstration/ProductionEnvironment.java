package ro.fortech.betting.demonstration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class ProductionEnvironment implements EnvironmentProvider {

	@Override
	public String provideEnvironmentName() {
		return "Production Environment";
	}

}
