package models;

import java.io.Serializable;
import java.util.List;

public class ScoreResponse implements Serializable {
	
	private static final long serialVersionUID = -266706354210367639L;
	private List<ScoreEntity> scores;
	private List<TotalScoreEntity> totalScores;

	public ScoreResponse(List<ScoreEntity> scores, List<TotalScoreEntity> totalScores) {
		this.scores = scores;
		this.totalScores = totalScores;
	}

	public List<ScoreEntity> getScores() {
		return scores;
	}

	public void setScores(List<ScoreEntity> scores) {
		this.scores = scores;
	}

	public List<TotalScoreEntity> getTotalScores() {
		return totalScores;
	}

	public void setTotalScores(List<TotalScoreEntity> totalScores) {
		this.totalScores = totalScores;
	}
}
