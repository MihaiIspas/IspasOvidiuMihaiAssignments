package dto;

import java.time.LocalDate;

public class TournamentDTO {
	
	private int ID;
	private String name;
	private String status;
	private int numberPlayers;
	private float price;
	private LocalDate date;
	
	public TournamentDTO() {
		
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
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "TournamentDTO [ID=" + ID + ", name=" + name + ", status=" + status + ", numberPlayers=" + numberPlayers
				+ ", price=" + price +", date=" + date + "]";
	}
	
	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof TournamentDTO) {
			int id=((TournamentDTO) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}

}
