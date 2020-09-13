package application;

public class Subjects {

	private String subjectName;
	private String score;

	public Subjects(String subjectName, String score) {
		this.subjectName = subjectName;
		this.score = score;
	}
	
	public Subjects() {
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
