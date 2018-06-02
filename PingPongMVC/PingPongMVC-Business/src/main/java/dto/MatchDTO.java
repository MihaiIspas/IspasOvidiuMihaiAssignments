package dto;

public class MatchDTO {
	
	int ID;
	String name;
	String score;
	String status;
	Integer tournamentID;
	String player1ID;
	String player2ID;
	String winnerID;
	
	public MatchDTO() {
		
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
	public Integer getTournamentID() {
		return tournamentID;
	}
	public void setTournamentID(Integer tournamentID) {
		this.tournamentID = tournamentID;
	}
	public String getPlayer1ID() {
		return player1ID;
	}
	public void setPlayer1ID(String player1id) {
		player1ID = player1id;
	}
	public String getPlayer2ID() {
		return player2ID;
	}
	public void setPlayer2ID(String player2id) {
		player2ID = player2id;
	}
	public String getWinnerID() {
		return winnerID;
	}
	public void setWinnerID(String winnerID) {
		this.winnerID = winnerID;
	}
	@Override
	public String toString() {
		return "MatchDTO [ID=" + ID + ", name=" + name + ", score=" + score + ", status=" + status + ", tournamentID="
				+ tournamentID + ", player1ID=" + player1ID + ", player2ID=" + player2ID + ", winnerID=" + winnerID
				+ "]";
	}
	
	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof MatchDTO) {
			int id=((MatchDTO) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}

}
