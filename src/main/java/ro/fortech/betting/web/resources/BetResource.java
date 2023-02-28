package ro.fortech.betting.web.resources;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import ro.fortech.betting.dto.BetDTO;
import ro.fortech.betting.service.BetService;

import java.util.List;
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


@RestController
@RequestMapping(value = "/api/bets", produces = MediaType.APPLICATION_JSON_VALUE)
public class BetResource {

    private final BetService betService;

    public BetResource(final BetService betService) {
        this.betService = betService;
    }

    @GetMapping
    public ResponseEntity<List<BetDTO>> getAllBets() {
        return ResponseEntity.ok(betService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BetDTO> getBet(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(betService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBet(@RequestBody @Valid final BetDTO betDTO) {
        return new ResponseEntity<>(betService.create(betDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBet(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final BetDTO betDTO) {
        betService.update(id, betDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBet(@PathVariable(name = "id") final Long id) {
        betService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
