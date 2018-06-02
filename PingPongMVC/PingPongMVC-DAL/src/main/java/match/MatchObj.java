package match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="game")
public class MatchObj {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	int ID;
	
	@Column(name="Name")
	String name;
	
	@Column(name="Score")
	String score;
	
	@Column(name="Status")
	String status;
	
	@Column(name="TournamentID")
	Integer tournamentID;
	
	@Column(name="Player1ID")
	Integer player1ID;
	
	@Column(name="Player2ID")
	Integer player2ID;
	
	@Column(name="WinnerID")
	Integer winnerID;
	
	public MatchObj() {
		
	}

	public MatchObj(int iD, String name, String score, String status, Integer tournamentID, Integer player1id, Integer player2id,Integer winnerID) {
		ID = iD;
		this.name = name;
		this.score = score;
		this.status = status;
		this.tournamentID = tournamentID;
		player1ID = player1id;
		player2ID = player2id;
		this.winnerID = winnerID;
	}

	public MatchObj(String name, String score, String status, Integer tournamentID, Integer player1id, Integer player2id) {
		this.name = name;
		this.score = score;
		this.status = status;
		this.tournamentID = tournamentID;
		player1ID = player1id;
		player2ID = player2id;
		//this.winnerID = winnerID;
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
	
	/*public Integer getTournamentID() {
		return new Integer(this.tournamentID);
	}
	
	public void setTournamentID(Integer tournamentID) {
		if(tournamentID!=null) {
			
		}
	}*/

	public Integer getPlayer1ID() {
		return player1ID;
	}

	public void setPlayer1ID(Integer player1id) {
		player1ID = player1id;
	}

	public Integer getPlayer2ID() {
		return player2ID;
	}

	public void setPlayer2ID(Integer player2id) {
		player2ID = player2id;
	}

	public Integer getWinnerID() {
		return winnerID;
	}

	public void setWinnerID(Integer winnerID) {
		this.winnerID = winnerID;
	}

	@Override
	public String toString() {
		return "MatchObj [ID=" + ID + ", name=" + name + ", score=" + score + ", status=" + status + ", tournamentID="
				+ tournamentID + ", player1ID=" + player1ID + ", player2ID=" + player2ID + ", winnerID=" + winnerID
				+ "]";
	}
	
	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof MatchObj) {
			int id=((MatchObj) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}
	
}
