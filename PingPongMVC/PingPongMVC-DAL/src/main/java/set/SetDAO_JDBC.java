package set;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SetDAO_JDBC implements SetDAO {
	
	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `set`";
	String selectByIDQuery="select * from `set` where `ID`=?";
	String selectByMatchIDQuery="select * from `set` where `MatchID`=?";
	String insertString="insert into `set` (`Name`,`Score`,`Status`,`MatchID`,`ScoreLimit`) values (?,?,?,?,?)";
	String updateString="update `set` set `Name`=?,`Score`=?,`Status`=?,`MatchID`=?,`ScoreLimit`=? where `ID`=?";
	String deleteString="delete from `set` where `ID`=?";
	
	public SetDAO_JDBC() {
		try {
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pingpong?autoReconnect=true&useSSL=false", "root", "password");
		}
		catch(Exception e) {
			e.getMessage();
		}
	}
	
	public ArrayList<SetObj> selectAll() throws SQLException {
		ArrayList<SetObj> list = new ArrayList<SetObj>();
		statement=connection.prepareStatement(selectAllQuery);
		rs=statement.executeQuery();
		while(rs.next()) {
			int id=rs.getInt("ID");
			String score=rs.getString("Score");
			String status=rs.getString("Status");
			Integer matchID=rs.getInt("MatchID");
			String name=rs.getString("Name");
			int scoreLimit=rs.getInt("ScoreLimit");
			SetObj set=new SetObj(id,name,score,status,matchID,scoreLimit);
			list.add(set);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public SetObj selectByID(int ID) throws SQLException {
		SetObj set = null;
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setInt(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			String score=rs.getString("Score");
			String status=rs.getString("Status");
			Integer matchID=rs.getInt("MatchID");
			String name=rs.getString("Name");
			int scoreLimit=rs.getInt("ScoreLimit");
			set=new SetObj(ID,name,score,status,matchID,scoreLimit);
		}
		rs.close();
		statement.close();
		return set;
	}
	
	public ArrayList<SetObj> selectByMatchID(Integer matchID) throws SQLException {
		ArrayList<SetObj> list = new ArrayList<SetObj>();
		statement=connection.prepareStatement(selectByMatchIDQuery);
		statement.setInt(1, matchID);
		rs=statement.executeQuery();
		while(rs.next()) {
			int id=rs.getInt("ID");
			String score=rs.getString("Score");
			String status=rs.getString("Status");
			String name=rs.getString("Name");
			int scoreLimit=rs.getInt("ScoreLimit");
			SetObj set=new SetObj(id,name,score,status,matchID,scoreLimit);
			list.add(set);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public int insert(String name,String score,String status,Integer matchID,int scoreLimit) throws SQLException {
		int ID=0;
		statement=connection.prepareStatement(insertString,Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, name);
		statement.setString(2, score);
		statement.setString(3, status);
		statement.setInt(4, matchID);
		statement.setInt(5, scoreLimit);
		statement.executeUpdate();
		ResultSet keys=statement.getGeneratedKeys();
		if(keys.next()) {
			ID=keys.getInt(1);
		}
		statement.close();
		return ID;
	}
	
	public void update(int ID,String name,String score,String status,Integer matchID,int scoreLimit) throws SQLException {
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setInt(1, ID);
		rs=statement.executeQuery();
		SetObj set=null;
		if(rs.next()) {
			String score1=rs.getString("Score");
			String status1=rs.getString("Status");
			Integer matchID1=rs.getInt("MatchID");
			int scoreLimit1=rs.getInt("ScoreLimit");
			String name1=rs.getString("Name");
			set=new SetObj(ID,name1,score1,status1,matchID1,scoreLimit1);
		}
		rs.close();
		statement.close();
		
		if(score==null || score=="") {
			score=set.getScore();
		}
		
		if(status==null || status=="") {
			status=set.getStatus();
		}
		
		if(matchID==-1) {
			matchID=set.getMatchID();
		}
		
		if(scoreLimit==-1) {
			scoreLimit=set.getScoreLimit();
		}
		
		if(name==null || name=="") {
			name=set.getName();
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, name);
		statement.setString(2, score);
		statement.setString(3, status);
		statement.setInt(4, matchID);
		statement.setInt(5, scoreLimit);
		statement.setInt(6, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(int ID) throws SQLException {
		statement=connection.prepareStatement(deleteString);
		statement.setInt(1, ID);
		statement.executeUpdate();
	}

}
