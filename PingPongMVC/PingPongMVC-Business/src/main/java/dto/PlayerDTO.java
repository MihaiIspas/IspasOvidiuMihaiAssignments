package dto;

public class PlayerDTO {
	
	

	private int ID;
	private String name;
	private int age;
	private String available;
	private String userName;
	private String password;
	private float account;
	private String type;
	
	public PlayerDTO() {
		
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public float getAccount() {
		return account;
	}
	public void setAccount(float account) {
		this.account = account;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "PlayerDTO [ID=" + ID + ", name=" + name + ", age=" + age + ", available=" + available + ", userName="
				+ userName + ", password=" + password + ", account=" + account + ", type=" + type + "]";
	}
	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof PlayerDTO) {
			int id=((PlayerDTO) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}


}
