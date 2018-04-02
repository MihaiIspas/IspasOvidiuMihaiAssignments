package set;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class SetDAO implements SetDAOInterface {
	
	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `set`";
	String selectByIDQuery="select * from `set` where `ID`=?";
	String selectByMatchIDQuery="select * from `set` where `MatchID`=?";
	String selectScoreLimitQuery="select `ScoreLimit` from `set` where `ID`=?";
	String insertString="insert into `set` (`Score`,`Status`,`MatchID`,`ScoreLimit`,`Name`) values (?,?,?,?,?)";
	String updateString="update `set` set `Score`=?,`Status`=?,`MatchID`=?,`ScoreLimit`=?,`Name`=? where `ID`=?";
	String deleteString="delete from `set` where `ID`=?";
	
	public SetDAO() {
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
			String[] auxStr = new String[4];
			String id=rs.getString("ID");
			auxStr[0]=rs.getString("Score");
			auxStr[1]=rs.getString("Status");
			auxStr[2]=rs.getString("MatchID");
			auxStr[3]=rs.getString("Name");
			map.put(id, auxStr);
		}
		rs.close();
		statement.close();
		return map;
	}
	
	public String selectScoreLimit(String ID) throws SQLException{
		String str=null;
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str=rs.getString("ScoreLimit");
		}
		return str;
	}
	
	public String[] selectByID(String ID) throws SQLException {
		String[] str = new String[5];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=ID;
			str[1]=rs.getString("Score");
			str[2]=rs.getString("Status");
			str[3]=rs.getString("MatchID");
			str[4]=rs.getString("Score");
		}
		rs.close();
		statement.close();
		return str;
	}
	
	public LinkedHashMap<String,String[]> selectByMatchID(String matchID) throws SQLException {
		LinkedHashMap<String,String[]> map = new LinkedHashMap<String,String[]>();
		statement=connection.prepareStatement(selectByMatchIDQuery);
		statement.setString(1, matchID);
		rs=statement.executeQuery();
		while(rs.next()) {
			String[] str = new String[4];
			String id=rs.getString("ID");
			str[0]=rs.getString("Score");
			str[1]=rs.getString("Status");
			str[2]=matchID;
			str[3]=rs.getString("Name");
			map.put(id, str);
		}
		rs.close();
		statement.close();
		return map;
	}
	
	public void insert(String score,String status,String matchID,String scoreLimit,String name) throws SQLException {
		statement=connection.prepareStatement(insertString);
		statement.setString(1, score);
		statement.setString(2, status);
		statement.setString(3, matchID);
		statement.setString(4, scoreLimit);
		statement.setString(5, name);
		statement.executeUpdate();
		statement.close();
	}
	
	public void update(String ID,String score,String status,String matchID,String scoreLimit,String name) throws SQLException {
		String[] str = new String[5];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=rs.getString("Score");
			str[1]=rs.getString("Status");
			str[2]=rs.getString("MatchID");
			str[3]=rs.getString("ScoreLimit");
			str[4]=rs.getString("Name");
		}
		rs.close();
		statement.close();
		
		if(score==null || score=="") {
			score=str[0];
		}
		
		if(status==null || status=="") {
			status=str[1];
		}
		
		if(matchID==null || matchID=="") {
			matchID=str[2];
		}
		
		if(scoreLimit==null || scoreLimit=="") {
			scoreLimit=str[3];
		}
		
		if(name==null || name=="") {
			name=str[4];
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, score);
		statement.setString(2, status);
		statement.setString(3, matchID);
		statement.setString(4, scoreLimit);
		statement.setString(5, name);
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
