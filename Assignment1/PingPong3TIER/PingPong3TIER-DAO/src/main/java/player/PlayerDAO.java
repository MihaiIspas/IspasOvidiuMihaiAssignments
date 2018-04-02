package player;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PlayerDAO implements PlayerDAOInterface {
	
	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `player`";
	String selectByIDQuery="select * from `player` where `ID`=?";
	String insertString="insert into `player` (`Name`,`Age`,`Available`,`UserName`,`Password`,`Type`) values (?,?,?,?,?,?)";
	String updateString="update `player` set `Name`=?,`Age`=?,`Available`=?,`UserName`=?,`Password`=? where `ID`=?";
	String updateAvString="update `player` set `Available`=? where `ID`=?";
	String deleteString="delete from `player` where `ID`=?";
	
	public PlayerDAO() {
		try {
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pingpong?autoReconnect=true&useSSL=false", "root", "password");
		}
		catch(Exception e) {
			e.getMessage();
		}
	}
	
	public LinkedHashMap<String,String[]> selectAll() throws SQLException {
		LinkedHashMap<String,String[]> map = new LinkedHashMap<String,String[]>();
		statement=connection.prepareStatement(selectAllQuery);
		rs=statement.executeQuery();
		while(rs.next()) {
			String[] auxStr = new String[5];
			auxStr[0]=rs.getString("Name");
			auxStr[1]=rs.getString("Age");
			if(rs.getString("Available").equals("0")) {
				auxStr[2]="Available";
				//System.out.println(auxStr[2]);
			}
			if(rs.getString("Available").equals("1")) {
				auxStr[2]="Not available";
			}
			auxStr[3]=rs.getString("UserName");
			auxStr[4]=rs.getString("Password");
			map.put(rs.getString("ID"), auxStr);
		}
		rs.close();
		statement.close();
		return map;
	}
	
	public ArrayList<String[]> selectAllPrerequisites() throws SQLException {
		ArrayList<String[]> list = new ArrayList<String[]>();
		statement=connection.prepareStatement(selectAllQuery);
		rs=statement.executeQuery();
		while(rs.next()) {
			String[] auxStr = new String[4];
			auxStr[0]=rs.getString("UserName");
			auxStr[1]=rs.getString("Password");
			auxStr[2]=rs.getString("ID");
			auxStr[3]=rs.getString("Type");
			list.add(auxStr);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public String[] selectByID(String ID) throws SQLException {
		String[] str = new String[6];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=ID;
			str[1]=rs.getString("Name");
			str[2]=rs.getString("Age");
			if(rs.getString("Available").equals("0")) {
				str[3]="Available";
			}
			if(rs.getString("Available").equals("1")) {
				str[3]="Not available";
			}
			str[4]=rs.getString("UserName");
			str[5]=rs.getString("Password");
		}
		rs.close();
		statement.close();
		return str;
		
	}
	
	public void insert(String name,String age,String available,String userName,String password,String type) throws SQLException {
		statement=connection.prepareStatement(insertString);
		statement.setString(1, name);
		statement.setString(2, age);
		statement.setString(3, available);
		statement.setString(4, userName);
		statement.setString(5, password);
		statement.setString(6, type);
		statement.executeUpdate();
		statement.close();
	}
	
	public void update(String ID,String name,String age,String available,String userName,String password) throws SQLException {
		String[] str = new String[5];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=rs.getString("Name");
			str[1]=rs.getString("Age");
			str[2]=rs.getString("Available");
			str[3]=rs.getString("UserName");
			str[4]=rs.getString("Password");
		}
		rs.close();
		statement.close();
		
		if(name==null || name=="") {
			name=str[0];
		}
		if(age==null || age=="") {
			age=str[1];
		}
		if(available==null || available=="") {
			available=str[2];
		}
		if(userName==null || userName=="") {
			userName=str[3];
		}
		if(password==null || password=="") {
			password=str[4];
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, name);
		statement.setString(2, age);
		statement.setString(3, available);
		statement.setString(4, userName);
		statement.setString(5, password);
		statement.setString(6, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(String ID) throws SQLException {
		statement=connection.prepareStatement(deleteString);
		statement.setString(1, ID);
		statement.executeUpdate();
	}

}