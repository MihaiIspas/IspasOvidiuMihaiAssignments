package entities;

import java.util.UUID;

public class WriterObj {
	
	String ID;
	String name;
	Integer age;
	String userName;
	String password;
	
	public WriterObj() {
		
	}

	public WriterObj(String name, Integer age, String userName, String password) {
		this.name = name;
		this.age = age;
		this.userName = userName;
		this.password = password;
		this.ID=UUID.randomUUID().toString();
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID=ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	@Override
	public String toString() {
		return "WriterObj [name=" + name + ", age=" + age + ", userName=" + userName + ", password=" + password + "]";
	}

}
