package ro.fortech.betting.web.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import ro.fortech.betting.dto.MatchDTO;
import ro.fortech.betting.service.MatchService;

@RestController
@RequestMapping(value = "/api/matchs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class MatchResource {

	private final MatchService matchService;

	public MatchResource(final MatchService matchService) {
		this.matchService = matchService;
	}

	@GetMapping
	public ResponseEntity<List<MatchDTO>> getAllMatchs() {
		return ResponseEntity.ok(matchService.findAll());
	}

	@GetMapping("/away-team/{team}")
	public ResponseEntity<MatchDTO> getMatchByAwayteam(@PathVariable(name = "team") String team) {
		return ResponseEntity.ok(matchService.getByAwayteam(team));
	}

	@GetMapping("/away/{away}/home/{home}")
	public ResponseEntity<MatchDTO> getMatchByTeams(@PathVariable("home") String home,
			@PathVariable(name = "away") String away) {
		return ResponseEntity.ok(matchService.getByTeams(away, home));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MatchDTO> getMatch(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(matchService.get(id));
	}

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<Long> createMatch(@RequestBody @Valid final MatchDTO matchDTO) {
		return new ResponseEntity<>(matchService.create(matchDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateMatch(@PathVariable(name = "id") final Long id,
			@RequestBody @Valid final MatchDTO matchDTO) {
		matchService.update(id, matchDTO);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteMatch(@PathVariable(name = "id") final Long id) {
		matchService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
