package ro.fortech.betting.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OddsMockGeneratorService {
	private final Random random = new Random();

	public double generateOdd() {
		return this.random.nextDouble(50.5);
	}

}
