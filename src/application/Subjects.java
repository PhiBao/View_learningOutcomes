package application;

public class Subjects {

	private String subjectName;
	private float score;
	
	public Subjects() {};
	
	public Subjects(String subjectName, float score) {
		this.subjectName = subjectName;
		this.score = score;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
