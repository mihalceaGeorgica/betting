package ro.fortech.betting.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ro.fortech.betting.domain.Bet;
import ro.fortech.betting.dto.BetDTO;
import ro.fortech.betting.exception.NotFoundException;
import ro.fortech.betting.repository.BetRepository;
 


@Service
public class BetService {

    private final BetRepository betRepository;

    public BetService(final BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public List<BetDTO> findAll() {
        final List<Bet> bets = betRepository.findAll(Sort.by("id"));
        return bets.stream()
                .map((bet) -> mapToDTO(bet, new BetDTO()))
                .collect(Collectors.toList());
    }

    public BetDTO get(final Long id) {
        return betRepository.findById(id)
                .map(bet -> mapToDTO(bet, new BetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BetDTO betDTO) {
        final Bet bet = new Bet();
        mapToEntity(betDTO, bet);
        return betRepository.save(bet).getId();
    }

    public void update(final Long id, final BetDTO betDTO) {
        final Bet bet = betRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(betDTO, bet);
        betRepository.save(bet);
    }

    public void delete(final Long id) {
        betRepository.deleteById(id);
    }

    private BetDTO mapToDTO(final Bet bet, final BetDTO betDTO) {
        betDTO.setId(bet.getId());
        betDTO.setName(bet.getName());
        betDTO.setOdd(bet.getOdd());
        betDTO.setPlacingTime(bet.getPlacingTime());
        return betDTO;
    }

    private Bet mapToEntity(final BetDTO betDTO, final Bet bet) {
        bet.setName(betDTO.getName());
        bet.setOdd(betDTO.getOdd());
        bet.setPlacingTime(betDTO.getPlacingTime());
        return bet;
    }

}