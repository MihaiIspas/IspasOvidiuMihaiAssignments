package dto;

public class SetDTO {
	
	int ID;
	String name;
	String score;
	String status;
	Integer matchID;
	int scoreLimit;
	
	public SetDTO() {
		
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMatchID() {
		return matchID;
	}

	public void setMatchID(Integer matchID) {
		this.matchID = matchID;
	}

	public int getScoreLimit() {
		return scoreLimit;
	}

	public void setScoreLimit(int scoreLimit) {
		this.scoreLimit = scoreLimit;
	}

	@Override
	public String toString() {
		return "SetDTO [ID=" + ID + ", name=" + name + ", score=" + score + ", status=" + status + ", matchID="
				+ matchID + ", scoreLimit=" + scoreLimit + "]";
	}
	
	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof SetDTO) {
			int id=((SetDTO) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}

}
