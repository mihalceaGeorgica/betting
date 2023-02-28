package ro.fortech.betting.dto;

import java.util.Set;

import jakarta.validation.constraints.Size;


public class MatchDTO {

    private Long id;

    @Size(max = 255)
    private String hometeam;

    @Size(max = 255)
    private String awayteam;
    
    private Set<BetDTO> bets;

    public Set<BetDTO> getBets() {
		return bets;
	}

	public void setBets(Set<BetDTO> bets) {
		this.bets = bets;
	}

	public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getHometeam() {
        return hometeam;
    }

    public void setHometeam(final String hometeam) {
        this.hometeam = hometeam;
    }

    public String getAwayteam() {
        return awayteam;
    }

    public void setAwayteam(final String awayteam) {
        this.awayteam = awayteam;
    }

}
