package ro.fortech.betting.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;


public class BetDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String odd;

    private OffsetDateTime placingTime;

    private Long matchBets;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(final String odd) {
        this.odd = odd;
    }

    public OffsetDateTime getPlacingTime() {
        return placingTime;
    }

    public void setPlacingTime(final OffsetDateTime placingTime) {
        this.placingTime = placingTime;
    }

    public Long getMatchBets() {
        return matchBets;
    }

    public void setMatchBets(final Long matchBets) {
        this.matchBets = matchBets;
    }

}