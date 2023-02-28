package ro.fortech.betting.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.transaction.NotSupportedException;
import ro.fortech.betting.domain.Bet;
import ro.fortech.betting.domain.Match;
import ro.fortech.betting.dto.GambleDTO;
import ro.fortech.betting.repository.BetRepository;
import ro.fortech.betting.repository.MatchRepository;

@Service
public class GambleService {

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private BetRepository betRepository;
	
	@Autowired
	private OddsForecastService oddsForecastService;
	
	@Transactional(rollbackFor = NotSupportedException.class)
	public Long gamble(GambleDTO gambleDTO) throws NotSupportedException {
		

		Match match = matchRepository.findById(gambleDTO.getMatchId()).get();

		Bet bet = betRepository.findById(gambleDTO.getBetId()).get();

		Set<Bet> bets = new HashSet<>();

		if (match.getBets() == null) {
			match.setBets(bets);
		}

		match.getBets().add(bet);

		Long updateMatchId = matchRepository.save(match).getId();
		
		
		
		oddsForecastService.recalculateOdds(updateMatchId);

		throw new NotSupportedException();

	}

}
