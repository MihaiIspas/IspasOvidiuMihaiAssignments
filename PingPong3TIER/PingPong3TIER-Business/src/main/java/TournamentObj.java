
public class TournamentObj {

	private int ID;
	private String name,status;
	
	public TournamentObj(int ID,String name,String status) {
		this.ID=ID;
		this.name=name;
		this.status=status;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int ID) {
		this.ID=ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status=status;
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
