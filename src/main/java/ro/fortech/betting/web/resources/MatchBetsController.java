package ro.fortech.betting.web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.NotSupportedException;
import ro.fortech.betting.dto.GambleDTO;
import ro.fortech.betting.service.GambleService;

@RestController
@RequestMapping(value = "/api/gamble", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchBetsController {

	@Autowired
	private GambleService gambleService;

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<Long> gamble(@RequestBody GambleDTO body) throws NotSupportedException {
		return new ResponseEntity<>(gambleService.gamble(body), HttpStatus.CREATED);
	}

}
