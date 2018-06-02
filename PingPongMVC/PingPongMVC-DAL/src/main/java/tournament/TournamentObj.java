package tournament;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import java.sql.Date;

import javax.persistence.Column;

@Entity
@Table(name="tournament")
public class TournamentObj {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int ID;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Status")
	private String status;
	
	@Column(name="NumberPlayers")
	private int numberPlayers;
	
	@Column(name="Price")
	private float price;
	
	@Column(name="Start_Date")
	private Date startDate;
	
	public TournamentObj() {
		
	}

	public TournamentObj(int iD, String name, String status, int numberPlayers, float price) {
		ID = iD;
		this.name = name;
		this.status = status;
		this.numberPlayers = numberPlayers;
		this.price = price;
	}

	public TournamentObj(int iD,String name, String status, int numberPlayers, float price,Date startDate) {
		this.ID=iD;
		this.name = name;
		this.status = status;
		this.numberPlayers = numberPlayers;
		this.price = price;
		this.startDate=startDate;
	}
	
	public TournamentObj(String name, String status, int numberPlayers, float price,Date startDate) {
		this.name = name;
		this.status = status;
		this.numberPlayers = numberPlayers;
		this.price = price;
		this.startDate=startDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNumberPlayers() {
		return numberPlayers;
	}

	public void setNumberPlayers(int numberPlayers) {
		this.numberPlayers = numberPlayers;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate=startDate;
	}

	@Override
	public String toString() {
		return "TournamentObj [ID=" + ID + ", name=" + name + ", status=" + status + ", numberPlayers=" + numberPlayers
				+ ", price=" + price + "]";
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
