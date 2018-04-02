
public class MatchObj {

	private int ID,tournamentID,player1ID,player2ID;
	private String score,status,name,player1Name,player2Name;
	
	public MatchObj(int ID,String score,String name,String status,int tournamentID) {
		this.ID=ID;
		this.score=score;
		this.name=name;
		this.status=status;
		this.tournamentID=tournamentID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int ID) {
		this.ID=ID;
	}
	
	public String getScore() {
		return this.score;
	}
	
	public void setScore(String score) {
		this.score=score;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getPlayer1Name() {
		return this.player1Name;
	}
	
	public void setPlayer1Name(String player1Name) {
		this.player1Name=player1Name;
	}
	
	public String getPlayer2Name() {
		return this.player2Name;
	}
	
	public void setPlayer2Name(String player2Name) {
		this.player2Name=player2Name;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status=status;
	}
	
	public int getTournamentID() {
		return this.tournamentID;
	}
	
	public void setTournamentID(int tournamentID) {
		this.tournamentID=tournamentID;
	}
	
	public int getPlayer1ID() {
		return this.player1ID;
	}
	
	public void setPlayer1ID(int player1ID) {
		this.player1ID=player1ID;
	}
	
	public int getPlayer2ID() {
		return this.player2ID;
	}
	
	public void setPlayer2ID(int player2ID) {
		this.player2ID=player2ID;
	}
	
	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof TournamentObj) {
			int id=((TournamentObj) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}

}
