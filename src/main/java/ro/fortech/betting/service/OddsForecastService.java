package ro.fortech.betting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ro.fortech.betting.domain.Match;
import ro.fortech.betting.repository.MatchRepository;

@Service
public class OddsForecastService {

	@Autowired
	private MatchRepository matchRepository;
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void recalculateOdds(Long matchId) {
		
		Match match = matchRepository.findById(matchId).get();
		
		System.out.print("---------size "+ match.getBets().size() + " ------------------");
		
	}

}
