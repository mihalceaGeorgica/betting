package ro.fortech.betting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.fortech.betting.domain.Bet;


public interface BetRepository extends JpaRepository<Bet, Long> {
}
 