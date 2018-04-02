package tournament;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class TournamentDAO implements TournamentDAOInterface {

	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `tournament`";
	String selectByIDQuery="select * from `tournament` where `ID`=?";
	String selectByNameQuery="select `ID` from `tournament` where `Name`=?";
	String selectNrQuery="select `NumberPlayers` from `tournament` where `ID`=?";
	String insertString="insert into `tournament` (`Name`,`Status`) values (?,?)";
	String updateString="update `tournament` set `Name`=?, `Status`=? where `ID`=?";
	String updateNrString="update `tournament` set `NumberPlayers`=? where `ID`=?";
	String deleteString="delete from `tournament` where `ID`=?";
	
	public TournamentDAO() {
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
			String[] auxStr = new String[2];
			String id=rs.getString("ID");
			auxStr[0]=rs.getString("Name");
			auxStr[1]=rs.getString("Status");
			map.put(id, auxStr);
		}
		rs.close();
		statement.close();
		return map;
	}
	
	public String[] selectByID(String ID) throws SQLException {
		String[] str = new String[3];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=ID;
			str[1]=rs.getString("Name");
			str[2]=rs.getString("Status");
		}
		rs.close();
		statement.close();
		return str;
		
	}
	
	public String selectNrOfPlayers(String ID) throws SQLException {
		String s="";
		statement=connection.prepareStatement(selectNrQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			s=rs.getString("NumberPlayers");
		}
		statement.close();
		rs.close();
		return s;
	}
	
	public String insert(String name,String status) throws SQLException {
		String ID="";
		statement=connection.prepareStatement(insertString);
		statement.setString(1, name);
		statement.setString(2, status);
		statement.executeUpdate();
		statement.close();
		statement=connection.prepareStatement(selectByNameQuery);
		statement.setString(1, name);
		rs=statement.executeQuery();
		if(rs.next()) {
			ID=rs.getString("ID");
		}
		statement.close();
		rs.close();
		return ID;
	}
	
	public void update(String ID,String name,String status) throws SQLException {
		String[] str = new String[2];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=rs.getString("Name");
			str[1]=rs.getString("Status");
		}
		rs.close();
		statement.close();
		
		if(name==null || name=="") {
			name=str[0];
		}
		
		if(status==null || status=="") {
			status=str[1];
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, name);
		statement.setString(2, status);
		statement.setString(3, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void updateNrOfPlayers(String ID,String number) throws SQLException {
		statement=connection.prepareStatement(updateNrString);
		statement.setString(1, number);
		statement.setString(2, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(String ID) throws SQLException {
		statement=connection.prepareStatement(deleteString);
		statement.setString(1, ID);
		statement.executeUpdate();
	}
	
}
