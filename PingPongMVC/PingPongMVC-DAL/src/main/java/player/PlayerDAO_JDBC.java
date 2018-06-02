package player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerDAO_JDBC implements PlayerDAO {
	
	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `player`";
	String selectByIDQuery="select * from `player` where `ID`=?";
	String insertString="insert into `player` (`Name`,`Age`,`Available`,`UserName`,`Password`,`Account`,`Type`) values (?,?,?,?,?,?,?)";
	String updateString="update `player` set `Name`=?,`Age`=?,`Available`=?,`UserName`=?,`Password`=?,`Account`=? where `ID`=?";
	String deleteString="delete from `player` where `ID`=?";
	
	public PlayerDAO_JDBC() {
		try {
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pingpong?autoReconnect=true&useSSL=false", "root", "password");
		}
		catch(Exception e) {
			e.getMessage();
		}
	}
	
	public ArrayList<PlayerObj> selectAll() throws SQLException {
		ArrayList<PlayerObj> list=new ArrayList<PlayerObj>();
		statement=connection.prepareStatement(selectAllQuery);
		rs=statement.executeQuery();
		while(rs.next()) {
			//String[] auxStr = new String[5];
			String name=rs.getString("Name");
			int age=rs.getInt("Age");
			int available=rs.getInt("Available");
			String userName=rs.getString("UserName");
			String password=rs.getString("Password");
			float account=rs.getFloat("Account");
			String type=rs.getString("Type");
			int ID=rs.getInt("ID");
			PlayerObj player=new PlayerObj(ID,name,age,available,userName,password,account,type);
			list.add(player);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public PlayerObj selectByID(int ID) throws SQLException {
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, Integer.toString(ID));
		rs=statement.executeQuery();
		PlayerObj player=null;
		if(rs.next()) {
			String name=rs.getString("Name");
			int age=rs.getInt("Age");
			int available=rs.getInt("Available");
			String userName=rs.getString("UserName");
			String password=rs.getString("Password");
			float account=rs.getFloat("Account");
			String type=rs.getString("Type");
			player=new PlayerObj(ID,name,age,available,userName,password,account,type);
		}
		rs.close();
		statement.close();
		return player;
	}
	
	public int insert(String name,int age,int available,String userName,String password,float account,String type) throws SQLException {
		int ID=0;
		statement=connection.prepareStatement(insertString,Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, name);
		statement.setInt(2, age);
		statement.setInt(3, available);
		statement.setString(4, userName);
		statement.setString(5, password);
		statement.setFloat(6, account);
		statement.setString(7, type);
		statement.executeUpdate();
		ResultSet keys=statement.getGeneratedKeys();
		if(keys.next()) {
			ID=keys.getInt(1);
		}
		statement.close();
		return ID;
	}
	
	public void update(int ID,String name,int age,int available,String userName,String password,float account) throws SQLException {
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, Integer.toString(ID));
		rs=statement.executeQuery();
		PlayerObj player=null;
		if(rs.next()) {
			String name1=rs.getString("Name");
			int age1=rs.getInt("Age");
			int available1=rs.getInt("Available");
			String userName1=rs.getString("UserName");
			String password1=rs.getString("Password");
			int account1=rs.getInt("Account");
			player=new PlayerObj(ID,name1,age1,available1,userName1,password1,account1,null);
		}
		rs.close();
		statement.close();
		
		if(name==null || name=="") {
			name=player.getName();
		}
		if(age<0) {
			age=player.getAge();
		}
		if(available!=0 && available!=1) {
			available=player.getAvailable();
		}
		if(userName==null || userName=="") {
			userName=player.getUserName();
		}
		if(password==null || password=="") {
			password=player.getPassword();
		}
		if(account<0) {
			account=player.getAccount();
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, name);
		statement.setInt(2, age);
		statement.setInt(3, available);
		statement.setString(4, userName);
		statement.setString(5, password);
		statement.setFloat(6, account);
		statement.setInt(7, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(int ID) throws SQLException {
		statement=connection.prepareStatement(deleteString);
		statement.setInt(1, ID);
		statement.executeUpdate();
	}

}
