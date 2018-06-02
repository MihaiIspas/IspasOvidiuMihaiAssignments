package set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="`set`")
public class SetObj {
	
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
	
	@Column(name="MatchID")
	Integer matchID;
	
	@Column(name="ScoreLimit")
	int scoreLimit;
	
	public SetObj() {
		
	}

	public SetObj(int iD, String name, String score, String status, Integer matchID, int scoreLimit) {
		super();
		ID = iD;
		this.name = name;
		this.score = score;
		this.status = status;
		this.matchID = matchID;
		this.scoreLimit = scoreLimit;
	}

	public SetObj(String name, String score, String status, Integer matchID, int scoreLimit) {
		super();
		this.name = name;
		this.score = score;
		this.status = status;
		this.matchID = matchID;
		this.scoreLimit = scoreLimit;
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
		return "SetObj [ID=" + ID + ", name=" + name + ", score=" + score + ", status=" + status + ", matchID="
				+ matchID + ", scoreLimit=" + scoreLimit + "]";
	}
	
	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof SetObj) {
			int id=((SetObj) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}

}
