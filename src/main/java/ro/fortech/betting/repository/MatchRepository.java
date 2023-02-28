package ro.fortech.betting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ro.fortech.betting.domain.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
	
	@Query("SELECT m FROM Match m WHERE LOWER(m.hometeam) = LOWER(:hometeam)")
	List<Match> retrieveByName(@Param("hometeam") String hometeam);
	
	
	List<Match> findByAwayteam(String awayteam);
	
	List<Match> findByAwayteamAndHometeam(String awayteam, String hometeam);
}
