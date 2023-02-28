package ro.fortech.betting.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Flux;
import ro.fortech.betting.domain.Bet;
import ro.fortech.betting.domain.Match;
import ro.fortech.betting.dto.BetDTO;
import ro.fortech.betting.dto.MatchDTO;
import ro.fortech.betting.dto.MatchScoreDTO;
import ro.fortech.betting.exception.NotFoundException;
import ro.fortech.betting.repository.MatchRepository;
import ro.fortech.betting.service.events.OddsChangeEvent;

@Service
public class MatchService {

	@Autowired
	private RestTemplate restTemplate;

	private final MatchRepository matchRepository;

	@Autowired
	private OddsChangeListenerService oddsChangeListenerService;

	@Autowired
	private OddsMockGeneratorService oddsGenerator;

	public MatchService(final MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	public List<MatchDTO> findAll() {

		HttpHeaders headers = new HttpHeaders();
		// headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> result = restTemplate.exchange("http://localhost:8080/betting/api/bets", HttpMethod.GET,
				entity, String.class);
		String body = result.getBody();

		System.out.println(body);
		final List<Match> matchs = matchRepository.findAll(Sort.by("id"));

		return matchs.stream().map((match) -> mapToDTO(match, new MatchDTO())).collect(Collectors.toList());
	}

	public MatchScoreDTO getLiveScoreForMatch(Long matchId) {
		MatchDTO match = get(matchId);

		MatchScoreDTO score = new MatchScoreDTO(match);

		score.setMatchScore("1x2");
		score.setOdd(oddsGenerator.generateOdd());
		return score;
	}

	public MatchDTO getByAwayteam(String team) {
		return matchRepository.findByAwayteam(team).stream().findFirst().map(match -> mapToDTO(match, new MatchDTO()))
				.orElseThrow(NotFoundException::new);
	}

	public MatchDTO getByTeams(String awayteam, String hometeam) {
		return matchRepository.findByAwayteamAndHometeam(awayteam, hometeam).stream().findFirst()
				.map(match -> mapToDTO(match, new MatchDTO())).orElseThrow(NotFoundException::new);
	}

	public MatchDTO get(final Long id) {
		return matchRepository.findById(id).map(match -> mapToDTO(match, new MatchDTO()))
				.orElseThrow(NotFoundException::new);
	}

	public Long create(final MatchDTO matchDTO) {
		final Match match = new Match();
		mapToEntity(matchDTO, match);
		return matchRepository.save(match).getId();
	}

	public void update(final Long id, final MatchDTO matchDTO) {
		final Match match = matchRepository.findById(id).orElseThrow(NotFoundException::new);
		mapToEntity(matchDTO, match);
		matchRepository.save(match);
	}

	public void delete(final Long id) {
		matchRepository.deleteById(id);
	}

	private MatchDTO mapToDTO(final Match match, final MatchDTO matchDTO) {
		matchDTO.setId(match.getId());
		matchDTO.setHometeam(match.getHometeam());
		matchDTO.setAwayteam(match.getAwayteam());
		matchDTO.setBets(match.getBets().stream().map(bet -> mapToDTO(bet, new BetDTO())).collect(Collectors.toSet()));

		return matchDTO;
	}

	private BetDTO mapToDTO(final Bet bet, final BetDTO betDTO) {
		betDTO.setId(bet.getId());
		betDTO.setName(bet.getName());
		betDTO.setOdd(bet.getOdd());
		betDTO.setPlacingTime(bet.getPlacingTime());
		return betDTO;
	}

	private Match mapToEntity(final MatchDTO matchDTO, final Match match) {
		match.setHometeam(matchDTO.getHometeam());
		match.setAwayteam(matchDTO.getAwayteam());
		return match;
	}

}
