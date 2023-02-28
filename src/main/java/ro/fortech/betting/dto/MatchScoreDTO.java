package ro.fortech.betting.dto;

public class MatchScoreDTO {

	private String matchScore; // 1x2

	private MatchDTO match; // Real Madrid - Barcelona

	private double odd; // 2.34

	public MatchScoreDTO(MatchDTO match) {
		super();
		this.match = match;
	}

	public String getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(String matchScore) {
		this.matchScore = matchScore;
	}

	public MatchDTO getMatch() {
		return match;
	}

	public void setMatch(MatchDTO match) {
		this.match = match;
	}

	public double getOdd() {
		return odd;
	}

	public void setOdd(double odd) {
		this.odd = odd;
	}

	@Override
	public String toString() {
		return "MatchScoreDTO [matchScore=" + matchScore + ", match=" + match + ", odd=" + odd + "]";
	}

}
