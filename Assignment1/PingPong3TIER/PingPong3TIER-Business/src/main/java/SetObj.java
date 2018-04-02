
public class SetObj {
	
	private int ID,matchID;
	private String score,status,name;
	
	public SetObj(int ID,String name,String score,String status,int matchID) {
		this.ID=ID;
		this.score=score;
		this.status=status;
		this.matchID=matchID;
		this.name=name;
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
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status=status;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public int getMatchID() {
		return this.matchID;
	}
	
	public void setTournamentID(int matchID) {
		this.matchID=matchID;
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
