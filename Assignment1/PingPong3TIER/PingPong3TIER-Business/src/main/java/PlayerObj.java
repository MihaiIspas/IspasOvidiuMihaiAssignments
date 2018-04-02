
public class PlayerObj {
	
	private String name,userName,password,available;
	private int age,ID;
	
	public PlayerObj(int ID,String name,int age,String available,String userName,String password) {
		this.ID=ID;
		this.name=name;
		this.age=age;
		this.available=available;
		this.userName=userName;
		this.password=password;
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
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age=age;
	}
	
	public String getAvailable() {
		return this.available;
	}
	
	public void setAvailable(String available) {
		this.available=available;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password=password;
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
