package player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="player")
public class PlayerObj {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int ID;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Age")
	private int age;
	
	@Column(name="Available")
	private int available;
	
	@Column(name="UserName")
	private String userName;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Account")
	private float account;
	
	@Column(name="Type")
	private String type;
	
	public PlayerObj() {
		
	}
	
	public PlayerObj(String name, int age, int available, String userName, String password, float account, String type) {
		this.name = name;
		this.age = age;
		this.available = available;
		this.userName = userName;
		this.password = password;
		this.account=account;
		this.type = type;
	}

	public PlayerObj(int iD, String name, int age, int available, String userName, String password, float account, String type) {
		ID = iD;
		this.name = name;
		this.age = age;
		this.available = available;
		this.userName = userName;
		this.password = password;
		this.account=account;
		this.type = type;
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

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
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
		return "PlayerObj [ID=" + ID + ", name=" + name + ", age=" + age + ", available=" + available + ", userName="
				+ userName + ", password=" + password + ", account=" + account + ", type=" + type + "]";
	}

	public int hashCode() {
		return this.ID;
	}
	
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof PlayerObj) {
			int id=((PlayerObj) obj).getID();
			if(id==this.getID()) {
				return true;
			}
		}
		return false;
	}

}
