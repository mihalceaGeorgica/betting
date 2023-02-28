package ro.fortech.betting.web.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class MatchBetsControllerTest {
	@Autowired
	private WebTestClient webTestClient;

	//TODO is this the good way to go ?
	@Test
	public void testHelloWorld() {
		/*
		 * webTestClient.get().uri("/helloWorld") // GET method and URI
		 * .accept(MediaType.TEXT_PLAIN) // setting ACCEPT-Content .exchange() // gives
		 * access to response .expectStatus().isOk() // checking if response is OK
		 * .expectBody(String.class).isEqualTo("Hello World!"); // checking for response
		 * type and message
		 */	}

}
